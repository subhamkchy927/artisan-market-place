package com.artisan_market_place.Security;

import com.artisan_market_place.entity.UsersLoginInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class PrincipleUsers extends UsersLoginInfo implements UserDetails {
    private String username;
    private String password;

    public PrincipleUsers(UsersLoginInfo loginUser) {
        this.username = loginUser.getLoginId();
        this.password = loginUser.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}