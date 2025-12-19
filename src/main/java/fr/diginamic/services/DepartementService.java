package fr.diginamic.services;

import fr.diginamic.daos.DepartementDao;
import fr.diginamic.entites.Departement;
import fr.diginamic.exceptions.DepartementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartementService {

    @Autowired
    private DepartementDao departementDao;

    public List<Departement> getAllDepartements(){

        return departementDao.getAllDepartements();

    }

    public Departement getDepartementByName(String name) throws DepartementException {

        return departementDao.getDepartementByNom(name);

    }

    @Transactional
    public Departement addDepartement(Departement departement) {

        try {
            return departementDao.getDepartementByNom(departement.getNom());
        } catch (DepartementException e) {
            departementDao.addDepartement(departement);
            return departement;
        }

    }

    @Transactional
    public void deleteDepartement(Departement departement) throws DepartementException {

        Departement departementEnBase = departementDao.getDepartementByNom(departement.getNom());
        departementDao.deleteDepartement(departementEnBase);

    }

}
