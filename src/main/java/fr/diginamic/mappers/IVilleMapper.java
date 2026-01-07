package fr.diginamic.mappers;

import fr.diginamic.dtos.VilleDto;
import fr.diginamic.entites.Ville;

public interface IVilleMapper {

    VilleDto toDto(Ville ville);

    Ville toEntity(VilleDto villeDto);

}
