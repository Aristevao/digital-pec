package com.fho.digitalpec.api.address.entity;

import com.fho.digitalpec.utils.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Data
@Entity
@Table(name = "address", schema = "digital_pec")
public class Address implements BaseEntity<Long> {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String street;

    @Column
    private String number;

    @Column
    private String district;

    @Column
    private String complement;

    @Column
    private String zipcode;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;
}
