package com.yayaveli.inventorymanagement.models;

import java.time.Instant;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class ProviderOrder extends AbstractEntity {
    private String orderCode;
    private Instant orderDate;
    private Provider provider;
    @OneToMany(mappedBy = "providerOrder")
    private List<ProviderOrderLine> providerOrderLines;
}
