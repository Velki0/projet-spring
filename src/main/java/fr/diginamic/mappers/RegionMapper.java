package fr.diginamic.mappers;

import fr.diginamic.dtos.RegionDto;
import fr.diginamic.entites.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RegionMapper implements IRegionMapper {

    @Autowired
    private IDepartementMapper departementMapper;

    @Override
    public RegionDto toDto(Region region) {

        return new RegionDto(region.getId(),
                region.getCodeRegion(),
                region.getNom(),
                region.getDepartements().stream().map(departement -> departementMapper.toDto(departement)).collect(Collectors.toSet())
        );

    }

    @Override
    public Region toEntity(RegionDto regionDto) {

        return new Region(regionDto.getCodeRegion(), regionDto.getNomRegion());

    }

}
