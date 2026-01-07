package fr.diginamic.services;

import fr.diginamic.daos.DepartementRepository;
import fr.diginamic.entites.Departement;
import fr.diginamic.exceptions.DepartementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepartementService implements IDepartementService {

    @Autowired
    private DepartementRepository departementRepository;

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

        Departement departementDB = departementRepository.findByCodeDepartement(departement.getCodeDepartement());
        if (departementDB != null) {
            return departementDB;
        } else {
            departementRepository.save(departement);
            return departement;
        }

    }

    @Transactional
    @Override
    public void addDepartement(Departement departement) throws DepartementException {

        Departement departementDB = departementRepository.findByCodeDepartement(departement.getCodeDepartement());
        if (departementDB == null) {
            departementRepository.save(departement);
        } else {
            throw new DepartementException("Le département avec l'id " + departement.getId() + " existe déjà en base de données. Impossible de l'ajouter.");
        }

    }

    @Transactional
    @Override
    public void updateDepartement(int id, Departement departement) throws DepartementException {

        Departement departementDB = departementRepository.findById(id);
        if (departementDB != null) {
            departementDB.setCodeDepartement(departement.getCodeDepartement());
            departementDB.setNom(departement.getNom());
            departementDB.setVilles(departement.getVilles());
        } else {
            throw new DepartementException("Le département avec l'id " + id + " n'existe pas en base de données. Impossible de le modifier.");
        }

    }

    @Transactional
    @Override
    public void deleteDepartement(int id) throws DepartementException {

        Departement departementDB = departementRepository.findById(id);
        if (departementDB != null) {
            departementRepository.delete(departementDB);
        } else {
            throw new DepartementException("Le département avec l'id " + id + " n'existe pas en base de données. Impossible de le supprimer.");
        }

    }

}
