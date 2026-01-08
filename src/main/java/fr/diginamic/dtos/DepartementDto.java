package fr.diginamic.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

public class DepartementDto {

    @Min(value = 0, message = "du département doit être positifs.")
    private Integer id;

    @JsonProperty("codeDepartement")
    @JsonAlias({"code"})
    @NotNull
    @Size(min = 2, message = "doit contenir au moins 2 caractères.")
    private final String codeDepartement;

    @JsonProperty("nomDepartement")
    @JsonAlias({"nom"})
    @NotNull
    @Size(min = 2, message = "du département doit avoir un nom contenant au moins 2 lettres.")
    private final String nomDepartement;

    private int nombreVilles;

    private final Set<String> nomsVilles = new HashSet<>();

    private final String codeRegion;

    private final String nomRegion;

    public DepartementDto(Integer id, String codeDepartement, String nomDepartement, Set<VilleDto> villesDto, String codeRegion, String nomRegion) {

        this.id = id;
        this.codeDepartement = codeDepartement;
        this.nomDepartement = nomDepartement;
        if (villesDto != null) {
            this.nombreVilles = villesDto.size();
            villesDto.forEach(villeDto -> nomsVilles.add(villeDto.getNom()));
        }
        this.codeRegion = codeRegion;
        this.nomRegion = nomRegion;

    }

    public Integer getId() { return id; }
    public String getCodeDepartement() { return codeDepartement; }
    public String getNomDepartement() { return nomDepartement; }
    public int getNombreVilles() { return nombreVilles; }
    public Set<String> getNomsVilles() { return nomsVilles; }
    public String getCodeRegion() { return codeRegion; }
    public String getNomRegion() { return nomRegion; }

}
