package fr.diginamic.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Departement {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "CODE_DEPARTEMENT", unique = true, nullable = false)
    private String codeDepartement;

    @Column(name = "NOM", unique = true, nullable = false)
    private String nom;

    @JsonIgnore
    @OneToMany(mappedBy = "departement")
    private List<Ville> villes = new ArrayList<>();

    public Departement() {}

    public Departement(String codeDepartement, String nom) {

        this.codeDepartement = codeDepartement;
        this.nom = nom;

    }

    public int getId() { return id; }
    public String getCodeDepartement() { return codeDepartement; }
    public String getNom() { return nom; }
    public List<Ville> getVilles() { return villes; }

}
