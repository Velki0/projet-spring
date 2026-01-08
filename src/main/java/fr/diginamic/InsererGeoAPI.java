package fr.diginamic;

import fr.diginamic.entites.Region;
import fr.diginamic.repositories.DepartementRepository;
import fr.diginamic.repositories.RegionRepository;
import fr.diginamic.dtos.DepartementDto;
import fr.diginamic.dtos.RegionDto;
import fr.diginamic.entites.Departement;
import fr.diginamic.mappers.DepartementMapper;
import fr.diginamic.mappers.IRegionMapper;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class InsererGeoAPI implements CommandLineRunner {

    @Autowired
    private DepartementRepository departementRepository;
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private DepartementMapper departementMapper;
    @Autowired
    private IRegionMapper regionMapper;

    public static void main(String[] args) {

        new SpringApplicationBuilder(InsererGeoAPI.class).web(WebApplicationType.NONE).run(args);

    }

    @Transactional
    @Override
    public void run(String @NonNull ... args) {

        RestTemplate restTemplate = new RestTemplate();
        RegionDto[] regionsGeoAPI = restTemplate.getForObject("https://geo.api.gouv.fr/regions", RegionDto[].class);
        DepartementDto[] departementsGeoAPI = restTemplate.getForObject("https://geo.api.gouv.fr/departements", DepartementDto[].class);

        assert regionsGeoAPI != null;
        for (RegionDto region : regionsGeoAPI) {

            Region regionDB = regionRepository.findByCodeRegion(region.getCodeRegion());
            if (regionDB == null) {
                regionRepository.save(regionMapper.toEntity(region));
            }

        }

        assert departementsGeoAPI != null;
        for (DepartementDto dto : departementsGeoAPI) {

            Departement departementDB = departementRepository.findByCodeDepartement(dto.getCodeDepartement());
            if (departementDB != null) {
                departementDB.setNom(dto.getNomDepartement());
                departementDB.setRegion(regionRepository.findByCodeRegion(dto.getCodeRegion()));
            } else {
                departementRepository.save(departementMapper.toEntity(dto));
            }

        }

    }

}
