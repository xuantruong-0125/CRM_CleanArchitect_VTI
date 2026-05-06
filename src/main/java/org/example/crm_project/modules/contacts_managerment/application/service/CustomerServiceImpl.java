package org.example.crm_project.modules.contacts_managerment.application.service;

import org.example.crm_project.modules.contacts_managerment.application.dto.request.CreateCustomerRequest;
import org.example.crm_project.modules.contacts_managerment.application.dto.request.UpdateCustomerRequest;
import org.example.crm_project.modules.contacts_managerment.application.dto.response.CustomerResponse;
import org.example.crm_project.modules.contacts_managerment.application.dto.response.PageResponse;
import org.example.crm_project.modules.contacts_managerment.application.interfaces.CustomerService;
import org.example.crm_project.modules.contacts_managerment.application.mapper.CustomerMapper;
import org.example.crm_project.modules.contacts_managerment.domain.entity.Customer;
import org.example.crm_project.modules.contacts_managerment.domain.entity.CustomerType;
import org.example.crm_project.modules.contacts_managerment.domain.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    @Transactional
    public CustomerResponse create(CreateCustomerRequest req) {
        Customer customer = customerMapper.toDomain(req);
        Customer saved = customerRepository.save(customer);
        return customerMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public CustomerResponse update(Long id, UpdateCustomerRequest req) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        customerMapper.updateDomain(customer, req);
        Customer updated = customerRepository.save(customer);
        return customerMapper.toResponse(updated);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByIds(List<Long> ids) {
        ids.forEach(customerRepository::deleteById);
    }

    @Override
    public CustomerResponse getById(Long id) {
        return customerRepository.findById(id)
                .map(customerMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    @Override
    public List<CustomerResponse> getAll() {
        return customerRepository.findAll().stream()
                .map(customerMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PageResponse<CustomerResponse> search(String keyword, CustomerType type, int page, int size) {
        List<CustomerResponse> items = customerRepository.search(keyword, type, page, size).stream()
                .map(customerMapper::toResponse)
                .collect(Collectors.toList());
        long totalItems = customerRepository.countSearch(keyword, type);
        int totalPages = (int) Math.ceil((double) totalItems / size);

        return PageResponse.<CustomerResponse>builder()
                .items(items)
                .totalItems(totalItems)
                .totalPages(totalPages)
                .page(page)
                .size(size)
                .build();
    }
}
