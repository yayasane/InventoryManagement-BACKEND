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
public class Company extends AbstractEntity {
    private String name;
    private String description;
    @Embedded
    private Address address;
    private String taxCode;
    private String picture;
    private String email;
    private String phoneNumber;
    private String webSite;
    @OneToMany(mappedBy = "company")
    private List<User> users;
}
