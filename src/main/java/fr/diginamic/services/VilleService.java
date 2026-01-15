package fr.diginamic.services;

import fr.diginamic.repositories.VilleRepository;
import fr.diginamic.entites.Ville;
import fr.diginamic.exceptions.VilleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VilleService implements IVilleService {

    @Autowired
    private VilleRepository villeRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(VilleService.class);

    @Override
    public Page<Ville> getAllVilles(Integer page, Integer taille) {

        if (page == null){ page = 0; }
        if (taille == null){ taille = Integer.MAX_VALUE; }
        PageRequest pagination = PageRequest.of(page, taille);
        return villeRepository.findAll(pagination);

    }

    @Override
    public Ville getVilleById(int id) throws VilleException {

        Ville villeDB = villeRepository.findById(id);
        if (villeDB != null) {
            return villeDB;
        } else {
            throw new VilleException("La ville avec l'id " + id + " n'existe pas en base de données.");
        }

    }

    @Override
    public Ville getVilleByNom(String nom) throws VilleException {

        Ville villeDB = villeRepository.findByNom(nom);
        if (villeDB != null) {
            return villeDB;
        } else {
            throw new VilleException("La ville avec le nom " + nom + " n'existe pas en base de données.");
        }

    }

    @Override
    public List<Ville> getVillesContenantNom(String nom) throws VilleException {

        List<Ville> villesDB = villeRepository.findByNomContainingIgnoreCase(nom);
        if (!villesDB.isEmpty()) {
            return villesDB;
        } else {
            throw new VilleException("Aucune correspondance de nom de Ville avec " + nom + " n'a été trouvé en base de données.");
        }

    }

    @Override
    public List<Ville> getVillesPopulationMin(int min) throws VilleException {

        List<Ville> villesDB = villeRepository.findByPopulationGreaterThanEqualOrderByPopulationDesc(min);
        if (!villesDB.isEmpty()) {
            return villesDB;
        } else {
            throw new VilleException("Aucune correspondance de Ville avec pour population minimale " + min + " n'a été trouvé en base de données.");
        }

    }

    @Override
    public List<Ville> getVillesPopulationBetween(int min, int max) throws VilleException {

        List<Ville> villesDB = villeRepository.findByPopulationBetweenOrderByPopulationDesc(min, max);
        if (!villesDB.isEmpty()) {
            return villesDB;
        } else {
            throw new VilleException("Aucune correspondance de Ville avec une population minimale de " + min + " et maximale de " + max + " n'a été trouvé en base de données.");
        }

    }

    @Override
    public List<Ville> getVillesFromDepartementPopulationMin(String codeDptm, int min) throws VilleException {

        List<Ville> villesDB = villeRepository.findAllVilleFromDepartementPopulationMin(codeDptm, min);
        if (!villesDB.isEmpty()) {
            return villesDB;
        } else {
            throw new VilleException("Aucune correspondance de Ville dans le " + codeDptm + " et avec une population minimale de " + min + " n'a été trouvé en base de données.");
        }

    }

    @Override
    public List<Ville> getVillesFromDepartementPopulationBetween(String codeDptm, int min, int max) throws VilleException {

        List<Ville> villesDB = villeRepository.findAllVilleFromDepartementPopulationBetween(codeDptm, min, max);
        if (!villesDB.isEmpty()) {
            return villesDB;
        } else {
            throw new VilleException("Aucune correspondance de Ville dans le " + codeDptm + " et avec une population minimale de " + min + " et maximale de " + max + " n'a été trouvé en base de données.");
        }

    }

    @Override
    public List<Ville> getVillesNPlusPeupleesFromDepartement(int top, String codeDptm) throws VilleException {

        List<Ville> villesDB = villeRepository.findTopVilleFromDepartement(top, codeDptm);
        if (!villesDB.isEmpty()) {
            return villesDB;
        } else {
            throw new VilleException("Aucune correspondance de Ville dans le " + codeDptm + " n'a été trouvé en base de données.");
        }

    }

    @Transactional
    @Override
    public void addVille(Ville ville) throws VilleException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (villeRepository.findByNom(ville.getNom()) == null && auth != null) {
            ville.setUserMaj(auth.getName());
            ville.setDateMaj(LocalDateTime.now());
            villeRepository.save(ville);
            LOGGER.info("L'utilisateur {} a ajouté la ville avec l'id {}.", auth.getName(), ville.getId());
        } else {
           throw new VilleException(ville.getNom() + " existe déjà en base de données. Impossible de l'ajouter.");
        }

    }

    @Transactional
    @Override
    public void updateVille(int id, Ville ville) throws VilleException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Ville villeDB = villeRepository.findById(id);
        if (villeDB != null && auth != null) {
            villeDB.setNom(ville.getNom());
            villeDB.setPopulation(ville.getPopulation());
            villeDB.setDepartement(ville.getDepartement());
            villeDB.setUserMaj(auth.getName());
            villeDB.setDateMaj(LocalDateTime.now());
            LOGGER.info("L'utilisateur {} a modifié la ville avec l'id {}.", auth.getName(), villeDB.getId());
        } else {
            throw new VilleException("La ville avec l'id " + id + " n'existe pas en base de données. Impossible de la modifier.");
        }

    }

    @Transactional
    @Override
    public void deleteVille(int id) throws VilleException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Ville villeDB = villeRepository.findById(id);
        if (villeDB != null && auth != null) {
            villeRepository.delete(villeDB);
            LOGGER.info("L'utilisateur {} a supprimé la ville avec l'id {}.", auth.getName(), villeDB.getId());
        } else {
            throw new VilleException("La ville avec l'id " + id + " n'existe pas en base de données. Impossible de la supprimer.");
        }

    }

}
