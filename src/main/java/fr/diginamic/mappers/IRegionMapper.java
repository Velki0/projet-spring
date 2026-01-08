package fr.diginamic.mappers;

import fr.diginamic.dtos.RegionDto;
import fr.diginamic.entites.Region;

public interface IRegionMapper {

    RegionDto toDto(Region region);

    Region toEntity(RegionDto regionDto);

}
