package fr.diginamic.daos;

import fr.diginamic.entites.Ville;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VilleRepository extends JpaRepository<Ville,Integer> {

    Ville findById(int id);

    Ville findByNom(String nom);

    // Recherche de toutes les villes dont le nom commence par une chaine de caractères donnée.
    List<Ville> findByNomContainingIgnoreCase(String nom);

    // Recherche de toutes les villes dont la population est supérieure à min. Les villes sont retournées par population descendante.
    List<Ville> findByPopulationGreaterThanEqualOrderByPopulationDesc(int min);

    // Recherche de toutes les villes dont la population est supérieure à min et inférieure à max. Les villes sont retournées par population descendante.
    List<Ville> findByPopulationBetweenOrderByPopulationDesc(int min, int max);

    // Recherche de toutes les villes d’un département dont la population est supérieure à min. Les villes sont retournées par population descendante.
    @Query("SELECT DISTINCT v FROM Ville v WHERE v.departement.codeDepartement = :codeDptm AND v.population >= :min ORDER BY v.population DESC")
    List<Ville> findAllVilleFromDepartementPopulationMin(String codeDptm, int min);

    // Recherche de toutes les villes d’un département dont la population est supérieure à min et inférieure à max. Les villes sont retournées par population descendante.
    @Query("SELECT DISTINCT v FROM Ville v WHERE v.departement.codeDepartement = :codeDptm AND v.population BETWEEN :min AND :max ORDER BY v.population DESC")
    List<Ville> findAllVilleFromDepartementPopulationBetween(String codeDptm, int min, int max);

    // Recherche des n villes les plus peuplées d’un département donné.
    @Query("SELECT DISTINCT v FROM Ville v WHERE v.departement.codeDepartement = :codeDptm ORDER BY v.population LIMIT :top")
    List<Ville> findTopVilleFromDepartement(int top, String codeDptm);

}
