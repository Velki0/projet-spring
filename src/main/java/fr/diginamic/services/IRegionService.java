package fr.diginamic.services;

import fr.diginamic.entites.Region;
import org.springframework.data.domain.Page;

public interface IRegionService {
    Page<Region> getAllRegions(Integer page, Integer taille);
}
