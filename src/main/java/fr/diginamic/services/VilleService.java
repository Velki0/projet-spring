package fr.diginamic.services;

import fr.diginamic.daos.VilleDao;
import fr.diginamic.entites.Ville;
import fr.diginamic.exceptions.VilleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VilleService {

    @Autowired
    private VilleDao villeDao;

    public List<Ville> getAllVille(){

        return villeDao.getAllVille();

    }

    public Ville getVilleById(int id) throws VilleException {

        return villeDao.getVilleById(id);

    }

    public Ville getVilleByName(String nom) throws VilleException {

        return villeDao.getVilleByName(nom);

    }

    @Transactional
    public void addVille(Ville ville) {

        try {
            villeDao.getVilleByName(ville.getNom());
        } catch (VilleException e) {
            villeDao.addVille(ville);
        }

    }

    @Transactional
    public void updateVille(int id, Ville ville) throws VilleException {

        Ville villeEnBase = villeDao.getVilleById(id);
        villeEnBase.setNom(ville.getNom());
        villeEnBase.setPopulation(ville.getPopulation());
        villeEnBase.setDepartement(ville.getDepartement());

    }

    @Transactional
    public void deleteVille(int id) throws VilleException {

        Ville villeEnBase = villeDao.getVilleById(id);
        villeDao.deleteVille(villeEnBase);

    }

}
