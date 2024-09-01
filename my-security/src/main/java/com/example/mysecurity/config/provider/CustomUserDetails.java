package com.example.mysecurity.config.provider;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

@Getter
@Setter
@Builder
public class CustomUserDetails implements UserDetails, Serializable {

    private String username;
    private String password;

    private Collection<GrantedAuthority> authorities;

}
