package fr.diginamic.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class VilleDto {

    @Min(value = 0, message = "de la ville doit être positifs.")
    private Integer id;

    @NotNull
    @Size(min = 2, message = "de la ville doit avoir un nom contenant au moins 2 lettres.")
    private final String nom;

    @Min(value = 10, message = "de la ville doit avoir au moins 10 habitants.")
    private final int population;

    @NotNull
    @Size(min = 2, message = "de la ville doit contenir au moins 2 caractères.")
    private final String codeDepartement;

    @NotNull
    @Size(min = 2, message = "de la ville doit contenir au moins 2 lettres.")
    private final String nomDepartement;

    public VilleDto(Integer id, String nom, int population, String codeDepartement, String nomDepartement) {

        this.id = id;
        this.nom = nom;
        this.population = population;
        this.codeDepartement = codeDepartement;
        this.nomDepartement = nomDepartement;

    }

    public Integer getId() { return id; }
    public String getNom() { return nom; }
    public int getPopulation() { return population; }
    public String getCodeDepartement() { return codeDepartement; }
    public String getNomDepartement() { return nomDepartement; }

}
