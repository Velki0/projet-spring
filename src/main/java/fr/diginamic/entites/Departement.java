package fr.diginamic.entites;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Departement {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "CODE_DEPARTEMENT", unique = true, nullable = false)
    private String codeDepartement;

    @Column(name = "NOM")
    private String nom;

    @OneToMany(mappedBy = "departement")
    private Set<Ville> villes = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "REGION_ID")
    private Region region;

    public Departement() {}

    public Departement(String codeDepartement, String nom) {

        this.codeDepartement = codeDepartement;
        this.nom = nom;

    }

    public int getId() { return id; }
    public String getCodeDepartement() { return codeDepartement; }
    public String getNom() { return nom; }
    public Set<Ville> getVilles() { return villes; }
    public Region getRegion() { return region; }

    public void setCodeDepartement(String codeDepartement) { this.codeDepartement = codeDepartement; }
    public void setNom(String nom) { this.nom = nom; }
    public void setVilles(Set<Ville> villes) { this.villes = villes; }
    public void setRegion(Region region) { this.region = region; }

}
