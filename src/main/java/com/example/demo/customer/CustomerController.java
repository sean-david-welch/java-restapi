package com.example.demo.customer;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.data.CustomerDTO;

@RestController
@RequestMapping(path = "/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<CustomerDTO> GetCustomers() {
        return customerService.GetCustomers();
    }

    @GetMapping(path = "{customerId}")
    public Optional<CustomerDTO> GetCustomerById(@PathVariable("customerId") String customerId) {
        return customerService.GetCustomerDetail(customerId);
    }

    @PostMapping
    public CustomerDTO PostCustomer(@RequestBody CustomerDTO customer) {
        return customerService.CreateCustomer(customer);
    }

    @PutMapping(path = "{customerId}")
    public CustomerDTO PutCustomer(@PathVariable("customerId") String customerId, CustomerDTO customer) {
        return customerService.UpdateCustomer(customerId, customer);
    }

    @DeleteMapping(path = "{customerId}")
    public ResponseEntity<String> DeleteCustomer(@PathVariable("customerId") String customerId) {
        customerService.RemoveCustomer(customerId);

        return ResponseEntity.ok("Customer with ID " + customerId + " has been deleted.");
    }
}
