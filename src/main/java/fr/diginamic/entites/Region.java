package fr.diginamic.entites;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Region {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "CODE_REGION", unique = true, nullable = false)
    private String codeRegion;

    @Column(name = "NOM")
    private String nom;

    @OneToMany(mappedBy = "region")
    private Set<Departement> departements = new HashSet<>();

    public Region() {}

    public Region(String codeRegion, String nom) {

        this.codeRegion = codeRegion;
        this.nom = nom;

    }

    public int getId() { return id; }
    public String getCodeRegion() { return codeRegion; }
    public String getNom() { return nom; }
    public Set<Departement> getDepartements() { return departements; }

    public void setCodeRegion(String codeRegion) { this.codeRegion = codeRegion; }
    public void setNom(String nom) { this.nom = nom; }
    public void setDepartements(Set<Departement> departements) { this.departements = departements; }

}
