package fr.diginamic.hello.entites;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Ville {

    private static int numId = 1;

    @Min(value = 1, message = "de la ville doit être supérieur à 1.")
    private final int id;
    @NotNull
    @Size(min = 2, message = "de la ville doit avoir un nom contenant au moins 2 lettres.")
    private String nom;
    @Min(value = 10, message = "de la ville doit avoir au moins 10 habitants.")
    private int population;

    public Ville(String nom, int population) {

        this.id = numId++;
        this.nom = nom;
        this.population = population;

    }

    public int getId() { return id; }
    public String getNom() { return nom; }
    public int getPopulation() { return population; }

    public void setNom(String nom) { this.nom = nom; }
    public void setPopulation(int population) { this.population = population; }

    @Override
    public String toString() {

        return "{" +id + ", " + nom + ", " + population + "}";

    }

}
