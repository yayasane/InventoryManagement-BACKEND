package com.yayaveli.inventorymanagement.models;

import java.util.List;

import javax.persistence.Embedded;
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
public class Provider extends AbstractEntity {
    private String lastName;
    private String firstName;
    private String email;
    @Embedded
    private Address address;
    private String picture;
    private String phoneNumber;
    @OneToMany(mappedBy = "provider")
    private List<ProviderOrder> providerOrders;
}
