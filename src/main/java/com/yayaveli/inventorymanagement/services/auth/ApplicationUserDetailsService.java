package com.yayaveli.inventorymanagement.services.auth;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import com.yayaveli.inventorymanagement.dto.UserDto;
import com.yayaveli.inventorymanagement.models.auth.ExtendedUser;
import com.yayaveli.inventorymanagement.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDto user = userService.findByEmail(email);
        System.out.println("after req " + user);
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getUserRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getRoleName())));
        System.out.println("roles -----" + authorities);
        return new ExtendedUser(user.getEmail(), user.getPassword(), authorities, user.getCompany().getId());
    }

}
