package fr.diginamic.entites;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;

public class Role implements GrantedAuthority {

    private final String nom;

    public Role(String nom) {

        this.nom = nom;

    }

    @Override
    public @Nullable String getAuthority() { return nom; }

}
