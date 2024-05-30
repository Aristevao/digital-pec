package com.fho.digitalpec.api.address.repository;

import com.fho.digitalpec.api.address.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
