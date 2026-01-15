package fr.diginamic;

import fr.diginamic.dtos.VilleDto;
import fr.diginamic.entites.Region;
import fr.diginamic.entites.Ville;
import fr.diginamic.mappers.IDepartementMapper;
import fr.diginamic.mappers.VilleMapper;
import fr.diginamic.repositories.DepartementRepository;
import fr.diginamic.repositories.RegionRepository;
import fr.diginamic.dtos.DepartementDto;
import fr.diginamic.dtos.RegionDto;
import fr.diginamic.entites.Departement;
import fr.diginamic.mappers.IRegionMapper;
import fr.diginamic.repositories.VilleRepository;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private VilleRepository villeRepository;
    @Autowired
    private DepartementRepository departementRepository;
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private VilleMapper villeMapper;
    @Autowired
    private IDepartementMapper departementMapper;
    @Autowired
    private IRegionMapper regionMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger("LoggerGeoAPI");

    public static void main(String[] args) {

        new SpringApplicationBuilder(InsererGeoAPI.class).web(WebApplicationType.NONE).run(args);

    }

    @Transactional
    @Override
    public void run(String @NonNull ... args) {

        double debut = System.currentTimeMillis();

        RestTemplate restTemplate = new RestTemplate();
        RegionDto[] regionsGeoAPI = restTemplate.getForObject("https://geo.api.gouv.fr/regions", RegionDto[].class);
        DepartementDto[] departementsGeoAPI = restTemplate.getForObject("https://geo.api.gouv.fr/departements", DepartementDto[].class);
        VilleDto[] villesGeoAPI = restTemplate.getForObject("https://geo.api.gouv.fr/communes", VilleDto[].class);

        assert regionsGeoAPI != null;
        if(regionsGeoAPI.length != regionRepository.count()) {
            for (RegionDto region : regionsGeoAPI) {

                Region regionDB = regionRepository.findByCodeRegion(region.getCodeRegion());
                if (regionDB != null) {
                    regionDB.setNom(region.getNomRegion());
                } else {
                    regionRepository.save(regionMapper.toEntity(region));
                }

            }
        }

        assert departementsGeoAPI != null;
        if(departementsGeoAPI.length != departementRepository.count()) {
            for (DepartementDto dptm : departementsGeoAPI) {

                Departement departementDB = departementRepository.findByCodeDepartement(dptm.getCodeDepartement());
                if (departementDB != null) {
                    departementDB.setNom(dptm.getNomDepartement());
                    departementDB.setRegion(regionRepository.findByCodeRegion(dptm.getCodeRegion()));
                } else {
                    Departement nouveauDepartement = departementMapper.toEntity(dptm);
                    nouveauDepartement.setRegion(regionRepository.findByCodeRegion(dptm.getCodeRegion()));
                    departementRepository.save(nouveauDepartement);
                }

            }
        }

        assert villesGeoAPI != null;
        if(villesGeoAPI.length != villeRepository.count()) {
            int i = 0;
            for (VilleDto dto : villesGeoAPI) {

                LOGGER.info("Parsing de la ville numéro : {}", i++);
                Ville villeDB = villeRepository.findByCodeVille(dto.getCodeVille());
                if (villeDB != null) {
                    villeDB.setNom(dto.getNom());
                    villeDB.setPopulation(dto.getPopulation());
                    villeDB.setDepartement(departementRepository.findByCodeDepartement(dto.getCodeDepartement()));
                } else {
                    Ville nouvelleVille = villeMapper.toEntity(dto);
                    nouvelleVille.setDepartement(departementRepository.findByCodeDepartement(dto.getCodeDepartement()));
                    villeRepository.save(nouvelleVille);
                }

            }
        }

        double fin = System.currentTimeMillis();
        LOGGER.info("Fin du parse de GeoAPI. Temps écoulé : {} secondes", (fin - debut) / 1000);

    }

}
