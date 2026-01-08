package fr.diginamic.entites;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Objects;

@Entity
public class Ville {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "CODE_VILLE")
    private String codeVille;

    @Column(name = "NOM")
    private String nom;

    @Column(name = "POPULATION")
    private int population;

    @ManyToOne
    @JoinColumn(name = "DEPARTEMENT_ID")
    private Departement departement;

    public Ville() {}

    public Ville(String codeVille, String nom, Integer population, Departement departement) {

        this.codeVille = codeVille;
        this.nom = nom;
        this.population = Objects.requireNonNullElse(population, 0);
        this.departement = departement;

    }

    public int getId() { return id; }
    public String getCodeVille() { return codeVille; }
    public String getNom() { return nom; }
    public int getPopulation() { return population; }
    public Departement getDepartement() { return departement; }

    public void setCodeVille(String codeVille) { this.codeVille = codeVille; }
    public void setNom(String nom) { this.nom = nom; }
    public void setPopulation(int population) { this.population = population; }
    public void setDepartement(Departement departement) { this.departement = departement; }

}
