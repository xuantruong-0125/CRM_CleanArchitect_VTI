package org.example.crm_project.modules.customers_managerment.application.service;

import org.example.crm_project.modules.customers_managerment.application.dto.request.CreateCustomerAddressDTO;
import org.example.crm_project.modules.customers_managerment.application.dto.response.CustomerAddressResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CustomerAddressService {

    CustomerAddressResponseDTO createCustomerAddress(CreateCustomerAddressDTO createDTO);

    CustomerAddressResponseDTO getCustomerAddressById(Long id);

    List<CustomerAddressResponseDTO> getAddressesByCustomer(Long customerId);

    Page<CustomerAddressResponseDTO> getAddressesByCustomerPaginated(Long customerId, Pageable pageable);

    Optional<CustomerAddressResponseDTO> getPrimaryAddress(Long customerId);

    CustomerAddressResponseDTO updateCustomerAddress(Long id, CreateCustomerAddressDTO createDTO);

    void deleteCustomerAddress(Long id);

    void deleteAddressesByCustomer(Long customerId);

    long countAddresses();
}
