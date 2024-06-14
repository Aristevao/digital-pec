package com.fho.digitalpec.api.specie.entity;

import com.fho.digitalpec.api.user.entity.User;
import com.fho.digitalpec.utils.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Data
@Entity
@Table(name = "specie", schema = "digital_pec")
public class Specie implements BaseEntity<Long> {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 80, unique = true)
    private String name;

    public Specie(String name) {
        this.name = name;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}