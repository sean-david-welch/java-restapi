package com.example.demo.customer;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.example.demo.data.CustomerDTO;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;

import java.util.UUID;
import java.util.stream.Collectors;
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

    public List<CustomerDTO> GetCustomers() {
        List<Customer> customers = customerRepository.findAll();

        return customers.stream()
                .map(CustomerDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<CustomerDTO> GetCustomerDetail(String customerId) {
        Optional<Customer> customer = customerRepository.findCustomerById(customerId);

        return customer.map(CustomerDTO::new);
    }

    @Transactional
    public CustomerDTO CreateCustomer(CustomerDTO customerDTO) {
        Optional<Customer> customerOptional = customerRepository.findCustomerById(customerDTO.getId());
        Optional<User> userOptional = userRepository.findById(customerDTO.getUserId());

        if (customerOptional.isPresent() || !userOptional.isPresent()) {
            throw new IllegalStateException("Csutomer already exits or user does not exist");
        }

        Customer customer = new Customer();
        customer.setId(UUID.randomUUID().toString());
        customer.setName(customerDTO.getName());
        customer.setUser(userOptional.get());

        Customer savedCustomer = customerRepository.save(customer);

        return new CustomerDTO(savedCustomer);
    }

    @Transactional
    public CustomerDTO UpdateCustomer(String customerId, CustomerDTO customerDTO) {
        Optional<Customer> customerOptional = customerRepository.findCustomerById(customerDTO.getId());
        Optional<User> userOptional = userRepository.findById(customerDTO.getUserId());

        if (customerOptional.isPresent() || !userOptional.isPresent()) {
            throw new IllegalStateException("Csutomer already exits or user does not exist");
        }

        Customer existingCustomer = customerOptional.get();

        existingCustomer.setName(customerDTO.getName());
        existingCustomer.setUser(userOptional.get());

        Customer updatedCustomer = customerRepository.save(existingCustomer);

        return new CustomerDTO(updatedCustomer);
    }

    public void RemoveCustomer(String customerId) {
        boolean exists = customerRepository.existsById(customerId);

        if (!exists) {
            throw new IllegalStateException("Customer does not exist" + customerId);
        }

        customerRepository.deleteById(customerId);
    }
}
