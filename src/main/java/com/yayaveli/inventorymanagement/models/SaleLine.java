package com.yayaveli.inventorymanagement.models;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class SaleLine extends AbstractEntity {
    @ManyToOne
    private Sale sale;
    private BigDecimal quantity;
    private BigDecimal unitPrice;
    private Integer companyId;
}
