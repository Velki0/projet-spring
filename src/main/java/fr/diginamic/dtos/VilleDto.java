package fr.diginamic.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VilleDto {

    @Min(value = 0, message = "de la ville doit être positifs.")
    private Integer id;

    @JsonProperty("codeVille")
    @JsonAlias("code")
    @NotNull
    @Size(min = 2, message = "de la ville doit contenir au moins 2 caractères.")
    private final String codeVille;

    @NotNull
    @Size(min = 2, message = "de la ville doit avoir un nom contenant au moins 2 lettres.")
    private final String nom;

    @Min(value = 10, message = "de la ville doit avoir au moins 10 habitants.")
    private final Integer population;

    @NotNull
    @Size(min = 2, message = "de la ville doit contenir au moins 2 caractères.")
    private final String codeDepartement;

    @Size(min = 2, message = "de la ville doit contenir au moins 2 lettres.")
    private final String nomDepartement;

    public VilleDto(Integer id, String codeVille, String nom, Integer population, String codeDepartement, String nomDepartement) {

        this.id = id;
        this.codeVille = codeVille;
        this.nom = nom;
        this.population = population;
        this.codeDepartement = codeDepartement;
        this.nomDepartement = nomDepartement;

    }

    public Integer getId() { return id; }
    public String getCodeVille() { return codeVille; }
    public String getNom() { return nom; }
    public Integer getPopulation() { return population; }
    public String getCodeDepartement() { return codeDepartement; }
    public String getNomDepartement() { return nomDepartement; }

}
