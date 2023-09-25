package com.example.demo.address;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, String> {

    Optional<Address> findAddressById(String Id);
}
