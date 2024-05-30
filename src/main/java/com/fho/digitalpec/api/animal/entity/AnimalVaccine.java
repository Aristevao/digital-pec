package com.fho.digitalpec.api.animal.entity;

import java.io.Serializable;
import java.time.LocalDate;

import com.fho.digitalpec.api.vaccine.entity.Vaccine;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity
@Table(name = "animal_vaccine", schema = "digital_pec")
@Data
public class AnimalVaccine {

    @Embeddable
    public static class AnimalVaccineId implements Serializable {
        @ManyToOne
        @JoinColumn(name = "animal_id", nullable = false)
        private Animal animal;

        @ManyToOne
        @JoinColumn(name = "vaccine_id", nullable = false)
        private Vaccine vaccine;
    }

    @EmbeddedId
    private AnimalVaccineId id;

    @Column(name = "next_application_date")
    private LocalDate nextApplicationDate;

    @Column(name = "completed")
    private Boolean completed;

    @Column(name = "application_date", nullable = false)
    private LocalDate applicationDate;
}
