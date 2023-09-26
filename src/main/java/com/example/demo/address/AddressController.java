package com.example.demo.address;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/address")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public List<Address> GetAddress() {
        return addressService.FetchAllAddress();
    }

    @GetMapping(path = "{addressId}")
    public Optional<Address> GetAddressById(@PathVariable("addressId") String addressId) {
        return addressService.FetchAddressDetail(addressId);
    }

    @PostMapping
    public void PostAddress(@RequestBody Address address) {
        addressService.CreateAddress(address);
    }

    @PutMapping(path = "{addressId}")
    public void PutAddresss(@PathVariable("addressId") String addressId, @RequestBody Address address) {
        addressService.UpdateAddress(addressId, address);
    }

    @DeleteMapping(path = "{addressId}")
    public void DeleteCustomer(@PathVariable("addressid") String addressId) {
        addressService.RemoveAddress(addressId);
    }
}
