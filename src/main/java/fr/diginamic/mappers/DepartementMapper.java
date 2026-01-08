package fr.diginamic.mappers;

import fr.diginamic.dtos.DepartementDto;
import fr.diginamic.entites.Departement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class DepartementMapper implements IDepartementMapper {

    @Autowired
    private IVilleMapper villeMapper;

    @Override
    public DepartementDto toDto(Departement departement) {

        return new DepartementDto(departement.getId(),
                departement.getCodeDepartement(),
                departement.getNom(),
                departement.getVilles().stream().map(ville -> villeMapper.toDto(ville)).collect(Collectors.toSet()),
                departement.getRegion().getCodeRegion(),
                departement.getRegion().getNom()
        );

    }

    @Override
    public Departement toEntity(DepartementDto departementDto) {

        return new Departement(departementDto.getCodeDepartement(), departementDto.getNomDepartement());

    }

}
