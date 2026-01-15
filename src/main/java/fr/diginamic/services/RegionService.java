package fr.diginamic.services;

import fr.diginamic.entites.Region;
import fr.diginamic.repositories.RegionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RegionService implements IRegionService {

    @Autowired
    private RegionRepository regionRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(RegionService.class);

    @Override
    public Page<Region> getAllRegions(Integer page, Integer taille){

        if (page == null){ page = 0; }
        if (taille == null){ taille = Integer.MAX_VALUE; }
        PageRequest pagination = PageRequest.of(page, taille);
        return regionRepository.findAll(pagination);

    }

    @Override
    public Region addRegionDeDepartement(Region region) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Region regionDB = regionRepository.findByCodeRegion(region.getCodeRegion());
        if (regionDB == null && auth != null) {
            region.setUserMaj(auth.getName());
            region.setDateMaj(LocalDateTime.now());
            regionRepository.save(region);
            LOGGER.info("L'utilisateur {} a ajouté la région avec l'id {} à partir de l'ajout d'un département.", auth.getName(), region.getId());
            return region;
        } else {
            return regionDB;
        }

    }



}
