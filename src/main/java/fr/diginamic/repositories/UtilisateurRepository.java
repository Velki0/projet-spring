package fr.diginamic.repositories;

import fr.diginamic.entites.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRepository extends JpaRepository<Utilisateur,Integer> {

    Utilisateur findByUsername(String username);

}
