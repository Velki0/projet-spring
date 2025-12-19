package fr.diginamic.mappers;

import fr.diginamic.dtos.DepartementDto;
import fr.diginamic.entites.Departement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class DepartementMapper {

    @Autowired
    private VilleMapper villeMapper;

    public DepartementDto toDto(Departement departement) {

        return new DepartementDto(departement.getId(),
                departement.getCodeDepartement(),
                departement.getNom(),
                departement.getVilles().stream().map(ville -> villeMapper.toDto(ville)).collect(Collectors.toSet())
        );

    }

    public Departement toEntity(DepartementDto departementDto) {

        return new Departement(departementDto.getCodeDepartement(), departementDto.getNomDepartement());

    }

}
