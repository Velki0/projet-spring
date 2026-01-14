package fr.diginamic.entites;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Utilisateur implements UserDetails {

    private final String username;
    private final String password;
    private final Set<Role> roles = new HashSet<>();

    public Utilisateur(String username, String password, Role role) {

        this.username = username;
        this.password = password;
        roles.add(role);

    }

    @Override
    public @NullMarked Collection<? extends GrantedAuthority> getAuthorities() { return roles; }

    @Override
    public @Nullable String getPassword() { return password; }

    @Override
    public @NullMarked String getUsername() { return username; }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }

}
