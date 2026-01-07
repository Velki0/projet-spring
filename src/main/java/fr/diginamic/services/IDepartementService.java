package fr.diginamic.services;

import fr.diginamic.entites.Departement;
import fr.diginamic.exceptions.DepartementException;
import org.springframework.data.domain.Page;

public interface IDepartementService {

    Page<Departement> getAllDepartements(Integer page, Integer taille);

    Departement getDepartementById(int id) throws DepartementException;

    Departement getDepartementByNom(String nom) throws DepartementException;

    Departement getDepartementByCode(String codeDptm) throws DepartementException;

    Departement addDepartementDeVille(Departement departement);

    void addDepartement(Departement departement) throws DepartementException;

    void updateDepartement(int id, Departement departement) throws DepartementException;

    void deleteDepartement(int id) throws DepartementException;

}
