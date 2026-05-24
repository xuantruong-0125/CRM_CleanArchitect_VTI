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

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
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

        // Map updates into domain model
        customer = customerMapper.updateEntityFromDTO(updateDTO, customer);
        
        // Validate invariants and update timestamps
        customer.validate();
        customer.setUpdatedAt(java.time.LocalDateTime.now());

        // Save
        Customer updatedCustomer = customerRepository.save(customer);

        return customerMapper.toResponseDTO(updatedCustomer);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponseDTO getCustomerById(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
        return customerMapper.toResponseDTO(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CustomerResponseDTO> getAllCustomers(Pageable pageable) {
        Page<Customer> customers = customerRepository.findAll(pageable);
        return customers.map(customerMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponseDTO findByEmail(String email) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new CustomerNotFoundException("Không tìm thấy khách hàng với email: " + email));
        return customerMapper.toResponseDTO(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponseDTO findByCustomerCode(String customerCode) {
        Customer customer = customerRepository.findByCustomerCode(customerCode)
                .orElseThrow(() -> new CustomerNotFoundException("Không tìm thấy khách hàng với mã: " + customerCode));
        return customerMapper.toResponseDTO(customer);
    }

    @Override
    public void deleteCustomer(Long customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new CustomerNotFoundException(customerId);
        }
        customerRepository.delete(customerId);
    }

    @Override
    public void updateCustomerStatus(Long customerId, Long statusId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));

        customer.changeStatus(CustomerStatus.fromId(statusId));
        customerRepository.save(customer);
    }

    @Override
    public void updateCustomerTier(Long customerId, Long tierId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));

        customer.changeTier(CustomerTier.fromId(tierId));
        customerRepository.save(customer);
    }

    @Override
    public void assignCustomerToUser(Long customerId, Long userId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));

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
        Page<Customer> customers = customerRepository.search(keyword, type, statusId, tierId, email, phone, assignedTo, pageable);
        return customers.map(customerMapper::toResponseDTO);
    }
}
