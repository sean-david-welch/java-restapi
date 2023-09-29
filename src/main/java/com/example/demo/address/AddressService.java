package com.example.demo.address;

// import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.example.demo.customer.Customer;
import com.example.demo.customer.CustomerRepository;
import com.example.demo.data.AddressDTO;

import jakarta.transaction.Transactional;

import java.util.UUID;
import java.util.stream.Collectors;
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

    public List<AddressDTO> FetchAllAddress() {
        List<Address> addresses = addressRepository.findAll();

        return addresses.stream()
                .map(AddressDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<AddressDTO> FetchAddressDetail(String addressId) {
        Optional<Address> address = addressRepository.findAddressById(addressId);

        return address.map(AddressDTO::new);
    }

    @Transactional
    public AddressDTO CreateAddress(AddressDTO addressDTO) {
        Optional<Address> addressOptional = addressRepository.findAddressById(addressDTO.getId());
        Optional<Customer> customerOptional = customerRepository.findCustomerById(addressDTO.getCustomerId());

        if (addressOptional.isPresent() || !customerOptional.isPresent()) {
            throw new IllegalStateException("Address already exists or customer does not exist");
        }

        Address address = new Address();

        address.setId(UUID.randomUUID().toString());
        address.setLine1(addressDTO.getLine1());
        address.setLine2(addressDTO.getLine2());
        address.setStreet(addressDTO.getStreet());
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setCountry(addressDTO.getCountry());
        address.setPostalCode(addressDTO.getPostalCode());
        address.setCustomer(customerOptional.get());

        Address savedAddress = addressRepository.save(address);

        return new AddressDTO(savedAddress);
    }

    @Transactional
    public AddressDTO UpdateAddress(String addressId, AddressDTO addressDTO) {
        Optional<Address> addressOptional = addressRepository.findAddressById(addressId);
        Optional<Customer> customerOptional = customerRepository.findCustomerById(addressDTO.getCustomerId());

        if (!addressOptional.isPresent() || !customerOptional.isPresent()) {
            throw new IllegalStateException("Address does not exist or customer does not exist");
        }

        Address existingAddress = addressOptional.get();

        existingAddress.setLine1(addressDTO.getLine1());
        existingAddress.setLine2(addressDTO.getLine2());
        existingAddress.setStreet(addressDTO.getStreet());
        existingAddress.setCity(addressDTO.getCity());
        existingAddress.setState(addressDTO.getState());
        existingAddress.setCountry(addressDTO.getCountry());
        existingAddress.setPostalCode(addressDTO.getPostalCode());
        existingAddress.setCustomer(customerOptional.get());

        Address updatedAddress = addressRepository.save(existingAddress);

        return new AddressDTO(updatedAddress);
    }

    public void RemoveAddress(String addressId) {
        boolean exists = addressRepository.existsById(addressId);

        if (!exists) {
            throw new IllegalStateException("Address does not exists" + addressId);
        }

        addressRepository.deleteById(addressId);
    }
}