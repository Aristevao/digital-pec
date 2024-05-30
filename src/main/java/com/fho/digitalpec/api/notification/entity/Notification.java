package com.fho.digitalpec.api.notification.entity;

import com.fho.digitalpec.api.user.entity.User;
import com.fho.digitalpec.utils.entity.AuditableEntity;
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

@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Data
@Entity
@Table(name = "notification", schema = "digital_pec")
public class Notification extends AuditableEntity<Long> {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 100)
    private String title;

    @Column(name = "message", length = 500)
    private String message;

    @Column(name = "is_read")
    private Boolean isRead;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
