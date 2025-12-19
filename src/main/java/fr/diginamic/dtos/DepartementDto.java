package fr.diginamic.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

public class DepartementDto {

    @Min(value = 0, message = "du département doit être positifs.")
    private Integer id;

    @NotNull
    @Size(min = 2, message = "doit contenir au moins 2 caractères.")
    private final String codeDepartement;

    @NotNull
    @Size(min = 2, message = "du département doit avoir un nom contenant au moins 2 lettres.")
    private final String nomDepartement;

    private int nombreVilles;

    private final Set<String> nomsVilles = new HashSet<>();

    public DepartementDto(Integer id, String codeDepartement, String nomDepartement, Set<VilleDto> villesDto) {

        this.id = id;
        this.codeDepartement = codeDepartement;
        this.nomDepartement = nomDepartement;
        if (villesDto != null) {
            this.nombreVilles = villesDto.size();
            villesDto.forEach(villeDto -> nomsVilles.add(villeDto.getNom()));
        }

    }

    public Integer getId() { return id; }
    public String getCodeDepartement() { return codeDepartement; }
    public String getNomDepartement() { return nomDepartement; }
    public int getNombreVilles() { return nombreVilles; }
    public Set<String> getNomsVilles() { return nomsVilles; }

}
