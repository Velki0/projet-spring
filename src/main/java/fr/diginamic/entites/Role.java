package fr.diginamic.entites;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Role implements GrantedAuthority {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "NOM")
    private String nom;

    @ManyToMany(mappedBy = "roles")
    private final Set<Utilisateur> utilisateurs = new HashSet<>();

    public Role() {}

    public Role(String nom) {

        this.nom = nom;

    }

    @Override
    public @Nullable String getAuthority() { return nom; }

}
