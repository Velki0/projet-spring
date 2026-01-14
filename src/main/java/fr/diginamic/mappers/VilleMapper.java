package fr.diginamic.mappers;

import fr.diginamic.dtos.VilleDto;
import fr.diginamic.entites.Departement;
import fr.diginamic.entites.Region;
import fr.diginamic.entites.Ville;
import org.springframework.stereotype.Component;

@Component
public class VilleMapper implements IVilleMapper {

    @Override
    public VilleDto toDto(Ville ville) {

        if (ville.getDepartement() != null) {
            return new VilleDto(
                    ville.getId(),
                    ville.getCodeVille(),
                    ville.getNom(),
                    ville.getPopulation(),
                    ville.getDepartement().getCodeDepartement(),
                    ville.getDepartement().getNom()
            );
        } else {
            return new VilleDto(
                    ville.getId(),
                    ville.getCodeVille(),
                    ville.getNom(),
                    ville.getPopulation(),
                    null,
                    null
            );
        }

    }

    @Override
    public Ville toEntity(VilleDto villeDto) {

        return new Ville(
                villeDto.getCodeVille(),
                villeDto.getNom(),
                villeDto.getPopulation(),
                new Departement(villeDto.getCodeDepartement(), villeDto.getNomDepartement(), new Region())
        );

    }

}
