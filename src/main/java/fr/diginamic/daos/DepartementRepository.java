package fr.diginamic.daos;

import fr.diginamic.entites.Departement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartementRepository extends JpaRepository<Departement,Integer> {

    Departement findById(int id);

    Departement findByNom(String nom);

    Departement findByCodeDepartement(String codeDepartement);

}
