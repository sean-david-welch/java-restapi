package com.example.demo.address;

// import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.example.demo.customer.Customer;
import com.example.demo.customer.CustomerRepository;

import jakarta.transaction.Transactional;

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

    public List<Address> FetchAllAddress() {
        return addressRepository.findAll();
    }

    public Optional<Address> FetchAddressDetail(String addressId) {
        return addressRepository.findAddressById(addressId);
    }

    @Transactional
    public void CreateAddress(Address address) {
        Optional<Address> addressOptional = addressRepository.findAddressById(address.getId());
        Optional<Customer> customerOptional = customerRepository.findCustomerById(address.getCustomer().getId());

        if (addressOptional.isPresent() || !customerOptional.isPresent()) {
            throw new IllegalStateException("Address already exists or customer does not exist");
        }

        address.setId(UUID.randomUUID().toString());

        addressRepository.save(address);
    }

    @Transactional
    public void UpdateAddress(String addressId, Address address) {
        Optional<Address> addressOptional = addressRepository.findAddressById(address.getId());
        Optional<Customer> customerOptional = customerRepository.findCustomerById(address.getCustomer().getId());

        if (addressOptional.isPresent() || !customerOptional.isPresent()) {
            throw new IllegalStateException("Address already exists or customer does not exist");
        }

        Address existingAddress = addressOptional.get();

        existingAddress.setLine1(address.getLine1());
        existingAddress.setLine2(address.getLine2());
        existingAddress.setCustomer(address.getCustomer());
        existingAddress.setStreet(address.getStreet());
        existingAddress.setCity(address.getCity());
        existingAddress.setState(address.getState());
        existingAddress.setCountry(address.getCountry());
        existingAddress.setPostalCode(address.getPostalCode());

        addressRepository.save(existingAddress);
    }

    public void RemoveAddress(String addressId) {
        boolean exists = addressRepository.existsById(addressId);

        if (!exists) {
            throw new IllegalStateException("Address does not exists" + addressId);
        }

        addressRepository.deleteById(addressId);
    }
}