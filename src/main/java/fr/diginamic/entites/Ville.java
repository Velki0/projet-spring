package fr.diginamic.entites;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

@Entity
public class Ville {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "CODE_VILLE", unique = true, nullable = false)
    private String codeVille;

    @Column(name = "NOM")
    private String nom;

    @Column(name = "POPULATION")
    private Integer population;

    @ManyToOne
    @JoinColumn(name = "DEPARTEMENT_ID")
    private Departement departement;

    @Column(name = "USER_MAJ")
    private String userMaj;

    @Column(name = "DATE_MAJ")
    private LocalDateTime dateMaj;

    public Ville() {}

    public Ville(String codeVille, String nom, Integer population, Departement departement) {

        this.codeVille = codeVille;
        this.nom = nom;
        this.population = population;
        this.departement = departement;

    }

    public int getId() { return id; }
    public String getCodeVille() { return codeVille; }
    public String getNom() { return nom; }
    public Integer getPopulation() { return population; }
    public Departement getDepartement() { return departement; }
    public String getUserMaj() { return userMaj; }
    public LocalDateTime getDateMaj() { return dateMaj; }

    public void setCodeVille(String codeVille) { this.codeVille = codeVille; }
    public void setNom(String nom) { this.nom = nom; }
    public void setPopulation(Integer population) { this.population = population; }
    public void setDepartement(Departement departement) { this.departement = departement; }
    public void setUserMaj(String userMaj) { this.userMaj = userMaj; }
    public void setDateMaj(LocalDateTime dateMaj) { this.dateMaj = dateMaj; }

}
