package com.fho.digitalpec.api.vaccine.entity;

import java.util.List;

import com.fho.digitalpec.api.specie.entity.Specie;
import com.fho.digitalpec.utils.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Data
@Entity
@Table(name = "vaccine", schema = "digital_pec")
public class Vaccine implements BaseEntity<Long> {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String name;

    @Column(name = "description", length = 1000)
    private String description;

    @ManyToMany
    @JoinTable(
            name = "vaccine_specie",
            joinColumns = @JoinColumn(name = "vaccine_id"),
            inverseJoinColumns = @JoinColumn(name = "specie_id")
    )
    private List<Specie> species;
}
