package org.example.crm_project.modules.customers_managerment.application.service.impl;

import org.example.crm_project.modules.customers_managerment.application.dto.request.CreateCustomerAddressDTO;
import org.example.crm_project.modules.customers_managerment.application.dto.response.CustomerAddressResponseDTO;
import org.example.crm_project.modules.customers_managerment.application.mapper.CustomerAddressMapper;
import org.example.crm_project.modules.customers_managerment.application.service.CustomerAddressService;
import org.example.crm_project.modules.customers_managerment.domain.entity.CustomerAddress;
import org.example.crm_project.modules.customers_managerment.domain.repository.CustomerAddressRepository;
import org.example.crm_project.modules.customers_managerment.domain.exception.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation: CustomerAddressServiceImpl
 * Business logic for CustomerAddress management
 */
@Service
@Transactional
public class CustomerAddressServiceImpl implements CustomerAddressService {

    private final CustomerAddressRepository customerAddressRepository;
    private final CustomerAddressMapper customerAddressMapper;

    public CustomerAddressServiceImpl(CustomerAddressRepository customerAddressRepository, CustomerAddressMapper customerAddressMapper) {
        this.customerAddressRepository = customerAddressRepository;
        this.customerAddressMapper = customerAddressMapper;
    }

    @Override
    public CustomerAddressResponseDTO createCustomerAddress(CreateCustomerAddressDTO createDTO) {
        CustomerAddress customerAddress = customerAddressMapper.toEntity(createDTO);
        customerAddress.setCreatedAt(LocalDateTime.now());

        // Enforce business rule: only one address can be primary
        if (Boolean.TRUE.equals(customerAddress.getIsPrimary())) {
            demoteExistingPrimaryAddress(customerAddress.getCustomerId());
        }

        CustomerAddress saved = customerAddressRepository.save(customerAddress);
        return customerAddressMapper.toResponseDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerAddressResponseDTO getCustomerAddressById(Long id) {
        CustomerAddress customerAddress = customerAddressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Địa chỉ khách hàng không tìm thấy: " + id));
        return customerAddressMapper.toResponseDTO(customerAddress);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerAddressResponseDTO> getAddressesByCustomer(Long customerId) {
        List<CustomerAddress> addresses = customerAddressRepository.findByCustomerId(customerId);
        return addresses.stream()
                .map(customerAddressMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CustomerAddressResponseDTO> getAddressesByCustomerPaginated(Long customerId, Pageable pageable) {
        Page<CustomerAddress> addresses = customerAddressRepository.findByCustomerId(customerId, pageable);
        return addresses.map(customerAddressMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustomerAddressResponseDTO> getPrimaryAddress(Long customerId) {
        return customerAddressRepository.findPrimaryAddress(customerId)
                .map(customerAddressMapper::toResponseDTO);
    }

    @Override
    public CustomerAddressResponseDTO updateCustomerAddress(Long id, CreateCustomerAddressDTO createDTO) {
        CustomerAddress customerAddress = customerAddressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Địa chỉ khách hàng không tìm thấy: " + id));
        
        boolean wasPrimary = Boolean.TRUE.equals(customerAddress.getIsPrimary());

        customerAddressMapper.updateEntityFromDTO(customerAddress, createDTO);
        customerAddress.setUpdatedAt(LocalDateTime.now());

        // Enforce business rule: only one address can be primary
        if (Boolean.TRUE.equals(customerAddress.getIsPrimary()) && !wasPrimary) {
            demoteExistingPrimaryAddress(customerAddress.getCustomerId());
        }

        CustomerAddress updated = customerAddressRepository.save(customerAddress);
        return customerAddressMapper.toResponseDTO(updated);
    }

    @Override
    public void deleteCustomerAddress(Long id) {
        if (!customerAddressRepository.existsById(id)) {
            throw new EntityNotFoundException("Địa chỉ khách hàng không tìm thấy: " + id);
        }
        customerAddressRepository.delete(id);
    }

    @Override
    public void deleteAddressesByCustomer(Long customerId) {
        customerAddressRepository.deleteByCustomerId(customerId);
    }

    @Override
    @Transactional(readOnly = true)
    public long countAddresses() {
        return customerAddressRepository.count();
    }

    private void demoteExistingPrimaryAddress(Long customerId) {
        customerAddressRepository.findPrimaryAddress(customerId).ifPresent(existingPrimary -> {
            existingPrimary.demoteFromPrimary();
            customerAddressRepository.save(existingPrimary);
        });
    }
}
