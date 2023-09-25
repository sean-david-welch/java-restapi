package com.example.demo.customer;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> GetCustomers() {
        return customerRepository.findAll();
    }

    public void CreateCustomer(Customer customer) {
        Optional<Customer> customerOptional = customerRepository.findCustomerById(customer.getId());

        if (customerOptional.isPresent()) {
            throw new IllegalStateException("Csutomer already exits");
        }

        customer.setId(UUID.randomUUID().toString());

        customerRepository.save(customer);
    }

    public void RemoveCustomer(String customerId) {
        boolean exists = customerRepository.existsById(customerId);

        if (!exists) {
            throw new IllegalStateException("Customer does not exist" + customerId);
        }

        customerRepository.deleteById(customerId);
    }

    @Transactional
    public void UpdateCustomer(String customerId, Customer customer) {
        Optional<Customer> customerOptional = customerRepository.findCustomerById(customer.getId());

        if (customerOptional.isPresent()) {
            throw new IllegalStateException("Csutomer already exits");
        }

        Customer existingCustomer = customerOptional.get();

        existingCustomer.setName(customer.getName());
    }
}
