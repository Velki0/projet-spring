package fr.diginamic.repositories;

import fr.diginamic.entites.Departement;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartementRepository extends JpaRepository<Departement,Integer> {

    @EntityGraph("region")
    Departement findById(int id);

    @EntityGraph("region")
    Departement findByNom(String nom);

    @EntityGraph("region")
    Departement findByCodeDepartement(String codeDepartement);

}
