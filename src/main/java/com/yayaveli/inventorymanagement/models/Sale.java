package com.yayaveli.inventorymanagement.models;

import java.time.Instant;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class Sale extends AbstractEntity {
    private String saleCode;
    private Instant saleDate;
    private String commentary;

}
