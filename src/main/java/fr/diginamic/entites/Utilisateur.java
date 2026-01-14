package fr.diginamic.entites;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Utilisateur implements UserDetails {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @ManyToMany
    @JoinTable(name = "UTILISATEURS_ROLES",
               joinColumns = @JoinColumn(name = "UTILISATEUR_ID"),
               inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    private final Set<Role> roles = new HashSet<>();

    public Utilisateur() {}

    public Utilisateur(String username, String password, Set<Role> roles) {

        this.username = username;
        this.password = password;
        this.roles.addAll(roles);

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
