package fr.diginamic.mappers;

import fr.diginamic.dtos.DepartementDto;
import fr.diginamic.entites.Departement;

public interface IDepartementMapper {

    DepartementDto toDto(Departement departement);

    Departement toEntity(DepartementDto departementDto);

}
