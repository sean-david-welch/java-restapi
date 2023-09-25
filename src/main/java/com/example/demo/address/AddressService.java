package com.example.demo.address;

// import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.example.demo.customer.Customer;
import com.example.demo.customer.CustomerRepository;

import java.util.UUID;
import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;

    public AddressService(AddressRepository addressRepository, CustomerRepository customerRepository) {
        this.addressRepository = addressRepository;
        this.customerRepository = customerRepository;
    }

    public List<Address> GetEveryAddress() {
        return addressRepository.findAll();
    }

    public void CreateAddress(Address address) {
        Optional<Address> addressOptional = addressRepository.findAddressById(address.getId());

        if (addressOptional.isPresent()) {
            throw new IllegalStateException("Address already exists");
        }

        Optional<Customer> customerOptional = customerRepository.findById(address.getCustomer().getId());
        if (!customerOptional.isPresent()) {
            throw new IllegalStateException("Associated customer not found");
        }

        address.setId(UUID.randomUUID().toString());

        addressRepository.save(address);
    }
}