package com.yayaveli.inventorymanagement.models.auth;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;

public class ExtendedUser extends User {

    @Getter
    @Setter
    private Integer companyId;

    public ExtendedUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public ExtendedUser(String username, String password, Collection<? extends GrantedAuthority> authorities,
            Integer companyId) {
        super(username, password, authorities);
        this.companyId = companyId;
    }
}
