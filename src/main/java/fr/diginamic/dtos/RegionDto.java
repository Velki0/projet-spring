package fr.diginamic.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

public class RegionDto {

    @Min(value = 0, message = "de la région doit être positifs.")
    private Integer id;

    @JsonProperty("codeRegion")
    @JsonAlias({"code"})
    @NotNull
    @Size(min = 2, message = "doit contenir au moins 2 caractères.")
    private final String codeRegion;

    @JsonProperty("nomRegion")
    @JsonAlias({"nom"})
    @NotNull
    @Size(min = 2, message = "de la région doit avoir un nom contenant au moins 2 lettres.")
    private final String nomRegion;

    private int nombreDepartements;

    private final Set<String> nomsDepartements = new HashSet<>();

    public RegionDto(Integer id, String codeRegion, String nomRegion, Set<DepartementDto> departementsDto) {

        this.id = id;
        this.codeRegion = codeRegion;
        this.nomRegion = nomRegion;
        if (departementsDto != null) {
            this.nombreDepartements = departementsDto.size();
            departementsDto.forEach(departementDto -> nomsDepartements.add(departementDto.getNomDepartement()));
        }

    }

    public int getId() { return id; }
    public String getCodeRegion() { return codeRegion; }
    public String getNomRegion() { return nomRegion; }
    public int getNombreDepartements() { return nombreDepartements; }
    public Set<String> getNomsDepartements() { return nomsDepartements; }

}
