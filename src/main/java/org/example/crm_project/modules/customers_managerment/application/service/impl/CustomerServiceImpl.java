package org.example.crm_project.modules.customers_managerment.application.service.impl;

import org.example.crm_project.modules.customers_managerment.application.dto.request.CreateCustomerDTO;
import org.example.crm_project.modules.customers_managerment.application.dto.response.CustomerResponseDTO;
import org.example.crm_project.modules.customers_managerment.application.dto.request.UpdateCustomerDTO;
import org.example.crm_project.modules.customers_managerment.application.mapper.CustomerMapper;
import org.example.crm_project.modules.customers_managerment.application.service.CustomerService;
import org.example.crm_project.modules.customers_managerment.domain.constant.CustomerStatus;
import org.example.crm_project.modules.customers_managerment.domain.constant.CustomerTier;
import org.example.crm_project.modules.customers_managerment.domain.entity.Customer;
import org.example.crm_project.modules.customers_managerment.domain.exception.CustomerNotFoundException;
import org.example.crm_project.modules.customers_managerment.domain.exception.InvalidCustomerException;
import org.example.crm_project.modules.customers_managerment.domain.repository.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.example.crm_project.modules.auth.domain.entity.AuthUser;
import org.example.crm_project.modules.system_managerment.application.service.UserService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation: CustomerServiceImpl
 * Xử lý logic nghiệp vụ cho Customer
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final UserService userService;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper, UserService userService) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.userService = userService;
    }

    @Override
    public CustomerResponseDTO createCustomer(CreateCustomerDTO createDTO) {
        if (createDTO == null) {
            throw new InvalidCustomerException("Dữ liệu khách hàng không được để trống");
        }

        // Kiểm tra email đã tồn tại (uniqueness constraint across aggregate roots)
        if (createDTO.getEmail() != null && !createDTO.getEmail().isEmpty()) {
            customerRepository.findByEmail(createDTO.getEmail()).ifPresent(c -> {
                throw new InvalidCustomerException("Email " + createDTO.getEmail() + " đã tồn tại");
            });
        }

        // Kiểm tra mã thuế đã tồn tại
        if (createDTO.getTaxCode() != null && !createDTO.getTaxCode().isEmpty()) {
            customerRepository.findByTaxCode(createDTO.getTaxCode()).ifPresent(c -> {
                throw new InvalidCustomerException("Mã thuế " + createDTO.getTaxCode() + " đã tồn tại");
            });
        }

        // Convert DTO to Entity
        Customer customer = customerMapper.toEntity(createDTO);

        AuthUser currentUser = getCurrentAuthenticatedUser();
        customer.setCreatedBy(currentUser.getId());

        // Initialize domain invariants and creation state
        customer.initializeCreation();

        // Save to repository
        Customer savedCustomer = customerRepository.save(customer);

        // Return response DTO
        return customerMapper.toResponseDTO(savedCustomer);
    }

    @Override
    public CustomerResponseDTO updateCustomer(Long customerId, UpdateCustomerDTO updateDTO) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
        validateScopeAccess(customer);

        // Map updates into domain model
        customer = customerMapper.updateEntityFromDTO(updateDTO, customer);
        
        // Validate invariants and update timestamps
        customer.validate();
        customer.setUpdatedAt(java.time.LocalDateTime.now());
        AuthUser currentUser = getCurrentAuthenticatedUser();
        customer.setUpdatedBy(currentUser.getId());

        // Save
        Customer updatedCustomer = customerRepository.save(customer);

        return customerMapper.toResponseDTO(updatedCustomer);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponseDTO getCustomerById(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
        validateScopeAccess(customer);
        return customerMapper.toResponseDTO(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CustomerResponseDTO> getAllCustomers(Pageable pageable) {
        AuthUser currentUser = getCurrentAuthenticatedUser();
        Long orgId = getUserOrganizationId(currentUser.getId());
        Page<Customer> customers = customerRepository.findAll(
                currentUser.getId(),
                orgId,
                currentUser.getScope(),
                pageable
        );
        return customers.map(customerMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponseDTO findByEmail(String email) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new CustomerNotFoundException("Không tìm thấy khách hàng với email: " + email));
        validateScopeAccess(customer);
        return customerMapper.toResponseDTO(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponseDTO findByCustomerCode(String customerCode) {
        Customer customer = customerRepository.findByCustomerCode(customerCode)
                .orElseThrow(() -> new CustomerNotFoundException("Không tìm thấy khách hàng với mã: " + customerCode));
        validateScopeAccess(customer);
        return customerMapper.toResponseDTO(customer);
    }

    @Override
    public void deleteCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
        validateScopeAccess(customer);
        customerRepository.delete(customerId);
    }

    @Override
    public void updateCustomerStatus(Long customerId, Long statusId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
        validateScopeAccess(customer);

        customer.changeStatus(CustomerStatus.fromId(statusId));
        customerRepository.save(customer);
    }

    @Override
    public void updateCustomerTier(Long customerId, Long tierId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
        validateScopeAccess(customer);

        customer.changeTier(CustomerTier.fromId(tierId));
        customerRepository.save(customer);
    }

    @Override
    public void assignCustomerToUser(Long customerId, Long userId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
        validateScopeAccess(customer);

        customer.assignToUser(userId);
        customerRepository.save(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public long getTotalCustomersCount() {
        return customerRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CustomerResponseDTO> searchCustomers(String keyword, String type, Long statusId, Long tierId, String email, String phone, Long assignedTo, Pageable pageable) {
        AuthUser currentUser = getCurrentAuthenticatedUser();
        Long orgId = getUserOrganizationId(currentUser.getId());
        Page<Customer> customers = customerRepository.search(
                keyword, type, statusId, tierId, email, phone, assignedTo,
                currentUser.getId(), orgId, currentUser.getScope(), pageable
        );
        return customers.map(customerMapper::toResponseDTO);
    }

    private AuthUser getCurrentAuthenticatedUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof AuthUser) {
            return (AuthUser) auth.getPrincipal();
        }
        throw new AccessDeniedException("Phiên đăng nhập không hợp lệ hoặc đã hết hạn!");
    }

    private Long getUserOrganizationId(Long userId) {
        try {
            var userDto = userService.getById(userId);
            return userDto.getOrganizationId();
        } catch (Exception e) {
            return null;
        }
    }

    private void validateScopeAccess(Customer customer) {
        AuthUser currentUser = getCurrentAuthenticatedUser();
        String scope = currentUser.getScope();
        if ("OWN".equalsIgnoreCase(scope)) {
            if (!currentUser.getId().equals(customer.getAssignedTo()) &&
                    !currentUser.getId().equals(customer.getCreatedBy())) {
                throw new AccessDeniedException("Bạn không có quyền truy cập Khách hàng này!");
            }
        } else if ("BRANCH".equalsIgnoreCase(scope) || "DEPARTMENT".equalsIgnoreCase(scope) || "TEAM".equalsIgnoreCase(scope)) {
            Long userOrgId = getUserOrganizationId(currentUser.getId());
            // Since we need to check if the assignedTo user or createdBy user belongs to this organization,
            // we can retrieve the organizationId of the user assigned to/created this customer:
            Long assignedToUserOrgId = customer.getAssignedTo() != null ? getUserOrganizationId(customer.getAssignedTo()) : null;
            Long createdByUserOrgId = customer.getCreatedBy() != null ? getUserOrganizationId(customer.getCreatedBy()) : null;
            
            boolean assignedMatches = assignedToUserOrgId != null && assignedToUserOrgId.equals(userOrgId);
            boolean createdMatches = (customer.getAssignedTo() == null) && createdByUserOrgId != null && createdByUserOrgId.equals(userOrgId);
            
            if (!assignedMatches && !createdMatches) {
                throw new AccessDeniedException("Bạn không có quyền truy cập Khách hàng thuộc phòng ban khác!");
            }
        }
    }
}
