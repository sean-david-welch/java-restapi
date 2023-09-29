package com.example.demo.address;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.data.AddressDTO;

@RestController
@RequestMapping(path = "/api/address")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public List<AddressDTO> GetAddress() {
        return addressService.FetchAllAddress();
    }

    @GetMapping(path = "{addressId}")
    public Optional<AddressDTO> GetAddressById(@PathVariable("addressId") String addressId) {
        return addressService.FetchAddressDetail(addressId);
    }

    @PostMapping
    public AddressDTO PostAddress(@RequestBody AddressDTO address) {
        return addressService.CreateAddress(address);
    }

    @PutMapping(path = "{addressId}")
    public AddressDTO PutAddresss(@PathVariable("addressId") String addressId, @RequestBody AddressDTO address) {
        return addressService.UpdateAddress(addressId, address);
    }

    @DeleteMapping(path = "{addressId}")
    public ResponseEntity<String> DeleteAddress(@PathVariable("addressid") String addressId) {
        addressService.RemoveAddress(addressId);

        return ResponseEntity.ok("Address with ID " + addressId + " has been deleted.");
    }
}
