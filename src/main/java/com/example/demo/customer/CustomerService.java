package com.example.demo.customer;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.example.demo.user.User;
import com.example.demo.user.UserRepository;

import java.util.UUID;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    public CustomerService(CustomerRepository customerRepository, UserRepository userRepository) {
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;

    }

    public List<Customer> GetCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> GetCustomerDetail(String customerId) {
        return customerRepository.findCustomerById(customerId);
    }

    @Transactional
    public void CreateCustomer(Customer customer) {
        Optional<Customer> customerOptional = customerRepository.findCustomerById(customer.getId());
        Optional<User> userOptional = userRepository.findById(customer.getUser().getId());

        if (customerOptional.isPresent() || !userOptional.isPresent()) {
            throw new IllegalStateException("Csutomer already exits or user does not exist");
        }

        customer.setId(UUID.randomUUID().toString());

        customerRepository.save(customer);
    }

    @Transactional
    public void UpdateCustomer(String customerId, Customer customer) {
        Optional<Customer> customerOptional = customerRepository.findCustomerById(customer.getId());
        Optional<User> userOptional = userRepository.findById(customer.getUser().getId());

        if (customerOptional.isPresent() || !userOptional.isPresent()) {
            throw new IllegalStateException("Csutomer already exits or user does not exist");
        }

        Customer existingCustomer = customerOptional.get();

        existingCustomer.setName(customer.getName());

        customerRepository.save(customer);
    }

    public void RemoveCustomer(String customerId) {
        boolean exists = customerRepository.existsById(customerId);

        if (!exists) {
            throw new IllegalStateException("Customer does not exist" + customerId);
        }

        customerRepository.deleteById(customerId);
    }
}
