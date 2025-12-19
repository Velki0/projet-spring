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

    public Departement getDepartementById(int id) throws DepartementException {

        return departementDao.getDepartementById(id);

    }

    public Departement getDepartementByNom(String name) throws DepartementException {

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
    public void updateDepartement(int id, Departement departement) throws DepartementException {

        Departement departementEnBase = departementDao.getDepartementById(id);
        departementEnBase.setCodeDepartement(departement.getCodeDepartement());
        departementEnBase.setNom(departement.getNom());
        departementEnBase.setVilles(departement.getVilles());

    }

    @Transactional
    public void deleteDepartement(int id) throws DepartementException {

        Departement departementEnBase = departementDao.getDepartementById(id);
        departementDao.deleteDepartement(departementEnBase);

    }

}
