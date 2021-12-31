package com.yayaveli.inventorymanagement.models;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AbstractEntity implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @CreatedDate

    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = false, updatable = false)
    private Instant updatedAt;

    /*
     * @PreUpdate
     * void preUpdate() {
     * updatedAt = Instant.now();
     * }
     */

    /*
     * @PrePersist
     * void prePersist() {
     * createdAt = Instant.now();
     * }
     */

}
