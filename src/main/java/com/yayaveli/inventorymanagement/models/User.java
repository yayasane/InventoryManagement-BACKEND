package com.yayaveli.inventorymanagement.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class User extends AbstractEntity {
    private String lastName;
    private String firstName;
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;
    private String email;
    private String password;
    @Embedded
    private Address address;
    private String picture;
    private String phoneNumber;
    @ManyToOne
    private Company company;
    @OneToMany(mappedBy = "user")
    private List<UserRole> userRoles;
}
