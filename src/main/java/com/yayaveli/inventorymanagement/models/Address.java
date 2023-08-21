package com.yayaveli.inventorymanagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String address1;
    private String address2;
    private String city;
    private String zipCode;
    private String country;
}
