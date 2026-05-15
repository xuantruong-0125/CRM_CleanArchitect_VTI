package org.example.crm_project.modules.customers.application.service;

import org.example.crm_project.modules.customers.application.dto.request.CreateCustomerDTO;
import org.example.crm_project.modules.customers.application.dto.response.CustomerResponseDTO;
import org.example.crm_project.modules.customers.application.dto.request.UpdateCustomerDTO;
import org.example.crm_project.modules.customers.application.mapper.CustomerMapper;
import org.example.crm_project.modules.customers.domain.constant.CustomerStatus;
import org.example.crm_project.modules.customers.domain.constant.CustomerTier;
import org.example.crm_project.modules.customers.domain.entity.Customer;
import org.example.crm_project.modules.customers.domain.exception.CustomerNotFoundException;
import org.example.crm_project.modules.customers.domain.exception.InvalidCustomerException;
import org.example.crm_project.modules.customers.domain.repository.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

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
        // Validate input
        if (createDTO == null || createDTO.getName() == null) {
            throw new InvalidCustomerException("Tên khách hàng là bắt buộc");
        }

        // Kiểm tra email đã tồn tại
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

        // Generate customer code
        customer.setCustomerCode("CUS-" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        customer.setCreatedAt(LocalDateTime.now());

        // Save to repository
        Customer savedCustomer = customerRepository.save(customer);

        // Return response DTO
        return customerMapper.toResponseDTO(savedCustomer);
    }

    @Override
    public CustomerResponseDTO updateCustomer(Long customerId, UpdateCustomerDTO updateDTO) {
        // Lấy khách hàng
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));

        // Cập nhật từ DTO
        customer = customerMapper.updateEntityFromDTO(updateDTO, customer);
        customer.setUpdatedAt(LocalDateTime.now());

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
    public Page<CustomerResponseDTO> getB2BCustomers(Pageable pageable) {
        Page<Customer> customers = customerRepository.findByType("B2B", pageable);
        return customers.map(customerMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CustomerResponseDTO> getB2CCustomers(Pageable pageable) {
        Page<Customer> customers = customerRepository.findByType("B2C", pageable);
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
    @Transactional(readOnly = true)
    public Page<CustomerResponseDTO> getCustomersByAssignedUser(Long userId, Pageable pageable) {
        Page<Customer> customers = customerRepository.findByAssignedTo(userId, pageable);
        return customers.map(customerMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CustomerResponseDTO> getCustomersByStatus(Long statusId, Pageable pageable) {
        Page<Customer> customers = customerRepository.findByStatusId(statusId, pageable);
        return customers.map(customerMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CustomerResponseDTO> getCustomersByTier(Long tierId, Pageable pageable) {
        Page<Customer> customers = customerRepository.findByTierId(tierId, pageable);
        return customers.map(customerMapper::toResponseDTO);
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

        customer.setStatus(CustomerStatus.fromId(statusId));
        customer.setUpdatedAt(LocalDateTime.now());
        customerRepository.save(customer);
    }

    @Override
    public void updateCustomerTier(Long customerId, Long tierId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));

        customer.setTier(CustomerTier.fromId(tierId));
        customer.setUpdatedAt(LocalDateTime.now());
        customerRepository.save(customer);
    }

    @Override
    public void assignCustomerToUser(Long customerId, Long userId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));

        customer.setAssignedTo(userId);
        customer.setUpdatedAt(LocalDateTime.now());
        customerRepository.save(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public long getTotalCustomersCount() {
        return customerRepository.count();
    }
}
