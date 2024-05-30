package com.fho.digitalpec.api.address.service;

import com.fho.digitalpec.api.address.entity.Address;
import com.fho.digitalpec.api.address.repository.AddressRepository;
import com.fho.digitalpec.exception.ResourceNotFoundException;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressService {

    private final MessageSource messageSource;
    private final AddressRepository repository;

    public void create(Address entity) {
        Address unit = repository.save(entity);
        log.info("Address '{}' was successfully created.", unit.getId());
    }

    public Address findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(messageSource, Address.class, id));
    }

    public void update(Long id, Address entity) {
        findById(id);
        entity.setId(id);
        repository.save(entity);
    }
}
