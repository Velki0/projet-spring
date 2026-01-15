package fr.diginamic.services;

import fr.diginamic.repositories.DepartementRepository;
import fr.diginamic.entites.Departement;
import fr.diginamic.exceptions.DepartementException;
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

@Service
public class DepartementService implements IDepartementService {

    @Autowired
    private DepartementRepository departementRepository;
    @Autowired
    private IRegionService regionService;

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartementService.class);

    @Override
    public Page<Departement> getAllDepartements(Integer page, Integer taille){

        if (page == null){ page = 0; }
        if (taille == null){ taille = Integer.MAX_VALUE; }
        PageRequest pagination = PageRequest.of(page, taille);
        return departementRepository.findAll(pagination);

    }

    @Override
    public Departement getDepartementById(int id) throws DepartementException {

        Departement departementDB = departementRepository.findById(id);
        if (departementDB != null) {
            return departementDB;
        } else {
            throw new DepartementException("Le département avec l'id " + id + " n'existe pas en base de données.");
        }

    }

    @Override
    public Departement getDepartementByNom(String nom) throws DepartementException {

        Departement departementDB = departementRepository.findByNom(nom);
        if (departementDB != null) {
            return departementDB;
        } else {
            throw new DepartementException("Le département avec le nom " + nom + " n'existe pas en base de données.");
        }

    }

    @Override
    public Departement getDepartementByCode(String codeDptm) throws DepartementException {

        Departement departementDB = departementRepository.findByCodeDepartement(codeDptm);
        if (departementDB != null) {
            return departementDB;
        } else {
            throw new DepartementException("Le département avec le code de département " + codeDptm + " n'existe pas en base de données.");
        }

    }

    @Transactional
    @Override
    public Departement addDepartementDeVille(Departement departement) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Departement departementDB = departementRepository.findByCodeDepartement(departement.getCodeDepartement());
        if (departementDB == null && auth != null) {
            departement.setUserMaj(auth.getName());
            departement.setDateMaj(LocalDateTime.now());
            departementRepository.save(departement);
            LOGGER.info("L'utilisateur {} a ajouté le département avec l'id {} à partir de l'ajout d'une ville.", auth.getName(), departement.getId());
            return departement;
        } else {
            return departementDB;
        }

    }

    @Transactional
    @Override
    public void addDepartement(Departement departement) throws DepartementException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Departement departementDB = departementRepository.findByCodeDepartement(departement.getCodeDepartement());
        if (departementDB == null && auth != null) {
            departement.setUserMaj(auth.getName());
            departement.setDateMaj(LocalDateTime.now());
            departementRepository.save(departement);
            LOGGER.info("L'utilisateur {} a ajouté le département avec l'id {}.", auth.getName(), departement.getId());
        } else {
            throw new DepartementException("Le département avec l'id " + departement.getId() + " existe déjà en base de données. Impossible de l'ajouter.");
        }

    }

    @Transactional
    @Override
    public void updateDepartement(int id, Departement departement) throws DepartementException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Departement departementDB = departementRepository.findById(id);
        if (departementDB != null && auth != null) {
            departementDB.setCodeDepartement(departement.getCodeDepartement());
            departementDB.setNom(departement.getNom());
            departementDB.setVilles(departement.getVilles());
            departementDB.setRegion(regionService.addRegionDeDepartement(departement.getRegion()));
            departementDB.setUserMaj(auth.getName());
            departementDB.setDateMaj(LocalDateTime.now());
            LOGGER.info("L'utilisateur {} a modifié le département avec l'id {}.", auth.getName(), departementDB.getId());
        } else {
            throw new DepartementException("Le département avec l'id " + id + " n'existe pas en base de données. Impossible de le modifier.");
        }

    }

    @Transactional
    @Override
    public void deleteDepartement(int id) throws DepartementException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Departement departementDB = departementRepository.findById(id);
        if (departementDB != null && auth != null) {
            departementRepository.delete(departementDB);
            LOGGER.info("L'utilisateur {} a supprimé le département avec l'id {}.", auth.getName(), departementDB.getId());
        } else {
            throw new DepartementException("Le département avec l'id " + id + " n'existe pas en base de données. Impossible de le supprimer.");
        }

    }

}
