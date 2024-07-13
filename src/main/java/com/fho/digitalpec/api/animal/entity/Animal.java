package com.fho.digitalpec.api.animal.entity;

import java.time.LocalDate;

import com.fho.digitalpec.api.unit.entity.Unit;
import com.fho.digitalpec.api.user.entity.User;
import com.fho.digitalpec.utils.entity.AuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Data
@Entity
@Table(name = "animal", schema = "digital_pec")
public class Animal extends AuditableEntity<Long> {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 80)
    private String name;

    @Column(name = "identification", length = 50, unique = true)
    private String identification;

    @Column(name = "specie", length = 80)
    private String specie;

    @Column(name = "breed", length = 80)
    private String breed;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex", length = 6)
    private Sex sex;

    @Column(name = "birthdate")
    private LocalDate birthdate;

    @Column(name = "registration_date")
    private LocalDate registrationDate;

    @Column(name = "picture", length = 1024)
    private String picture;

    @Column(name = "description", length = 1000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
