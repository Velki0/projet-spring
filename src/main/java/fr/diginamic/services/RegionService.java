package fr.diginamic.services;

import fr.diginamic.entites.Region;
import fr.diginamic.repositories.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class RegionService implements IRegionService {

    @Autowired
    private RegionRepository regionRepository;

    @Override
    public Page<Region> getAllRegions(Integer page, Integer taille){

        if (page == null){ page = 0; }
        if (taille == null){ taille = Integer.MAX_VALUE; }
        PageRequest pagination = PageRequest.of(page, taille);
        return regionRepository.findAll(pagination);

    }

}
