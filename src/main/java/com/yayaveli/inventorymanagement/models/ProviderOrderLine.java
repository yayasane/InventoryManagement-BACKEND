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
public class ProviderOrderLine extends AbstractEntity {
    @ManyToOne
    private Item item;
    @ManyToOne
    private ProviderOrder providerOrder;
    private BigDecimal quantity;
    private BigDecimal unitPrice;
    private Integer companyId;
}
