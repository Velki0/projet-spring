package fr.diginamic.hello.entites;

public class Ville {

    private static int numId = 1;

    private final int id;
    private String nom;
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
