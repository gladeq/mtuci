package ru.mtuci.rbpomtuci2024.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

@Data
public class UserDetailsImpl implements UserDetails {
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private boolean isActive;

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

    public static UserDetailsImpl fromApplicationUser(ApplicationUser user) {

        Collection<GrantedAuthority> authorities =  Arrays.asList(new SimpleGrantedAuthority("ROLE_" + user.getRole()));

        return new UserDetailsImpl(
                user.getLogin(),
                user.getPassword_hash(),
                authorities,
                true
        );
    }

    // Конструктор для UserDetailsImpl
    public UserDetailsImpl(String username, String password, Collection<? extends GrantedAuthority> authorities, boolean isActive) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.isActive = isActive;
    }
}

