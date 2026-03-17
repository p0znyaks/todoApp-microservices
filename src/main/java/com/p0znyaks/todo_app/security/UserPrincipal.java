package com.p0znyaks.todo_app.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.List;

@Getter
public class UserPrincipal implements UserDetails {

    private final String username;
    private final String password;
    private final String email;
    private final List<? extends GrantedAuthority> authorities;

    public UserPrincipal(String username, String password, String email, List<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.authorities = Collections.unmodifiableList(authorities);
    }
}
