package com.yayaveli.inventorymanagement.models;

import java.time.Instant;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
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
public class ClientOrder extends AbstractEntity {
    private String orderCode;
    private Instant orderDate;
    @ManyToOne
    private Client client;
    @OneToMany(mappedBy = "clientOrder")
    private List<ClientOrderLine> clientOrderLines;
}
