package fr.diginamic.entites;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Ville {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "NOM", unique = true, nullable = false)
    @NotNull
    @Size(min = 2, message = "de la ville doit avoir un nom contenant au moins 2 lettres.")
    private String nom;

    @Column(name = "POPULATION")
    @Min(value = 10, message = "de la ville doit avoir au moins 10 habitants.")
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

        return "{" +id + ", " + nom + ", " + population + "}";

    }

}
