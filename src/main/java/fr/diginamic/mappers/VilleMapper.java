package fr.diginamic.mappers;

import fr.diginamic.dtos.VilleDto;
import fr.diginamic.entites.Departement;
import fr.diginamic.entites.Ville;
import org.springframework.stereotype.Component;

@Component
public class VilleMapper {

    public VilleDto toDto(Ville ville) {

        return new VilleDto(ville.getId(),
                            ville.getNom(),
                            ville.getPopulation(),
                            ville.getDepartement().getCodeDepartement(),
                            ville.getDepartement().getNom()
        );

    }

    public Ville toEntity(VilleDto villeDto) {

        return new Ville(villeDto.getNom(),
                         villeDto.getPopulation(),
                         new Departement(villeDto.getCodeDepartement(), villeDto.getNomDepartement())
        );

    }

}
