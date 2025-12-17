package fr.diginamic.hello.entites;

public class Ville {

    private String nom;
    private int population;

    public Ville(String nom, int population) {

        this.nom = nom;
        this.population = population;

    }

    public String getNom() { return nom; }
    public int getPopulation() { return population; }

    public void setNom(String nom) { this.nom = nom; }
    public void setPopulation(int population) { this.population = population; }

}
