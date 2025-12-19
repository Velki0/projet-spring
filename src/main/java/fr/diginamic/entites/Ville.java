package fr.diginamic.entites;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Ville {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "NOM", unique = true, nullable = false)
    private String nom;

    @Column(name = "POPULATION")
    private int population;

    @ManyToOne
    @JoinColumn(name = "DEPARTEMENT_ID")
    private Departement departement;

    public Ville() {}

    public Ville(String nom, int population, Departement departement) {

        this.nom = nom;
        this.population = population;
        this.departement = departement;

    }

    public int getId() { return id; }
    public String getNom() { return nom; }
    public int getPopulation() { return population; }
    public Departement getDepartement() { return departement; }

    public void setNom(String nom) { this.nom = nom; }
    public void setPopulation(int population) { this.population = population; }
    public void setDepartement(Departement departement) { this.departement = departement; }

    @Override
    public String toString() {

        return "{" +id + ", " + nom + ", " + population + ", " + departement.getCodeDepartement() + ", " + departement.getNom() + "}";

    }

}
