package com.yayaveli.inventorymanagement.models;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Article
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class Item extends AbstractEntity {

    private String itemCode;
    private String designation;
    private BigDecimal unitPriceExclT;
    private BigDecimal vat;
    private BigDecimal unitPriceInclT;
    private String picture;
    @ManyToOne
    private Category category;
    private Integer companyId;
}