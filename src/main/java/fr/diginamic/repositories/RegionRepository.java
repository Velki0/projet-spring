package fr.diginamic.repositories;

import fr.diginamic.entites.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region,Integer> {

    Region findByNom(String Nom);

    Region findByCodeRegion(String codeRegion);

}
