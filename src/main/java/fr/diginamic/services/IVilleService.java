package fr.diginamic.services;

import fr.diginamic.entites.Ville;
import fr.diginamic.exceptions.VilleException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IVilleService {

    Page<Ville> getAllVilles(Integer page, Integer taille);

    Ville getVilleById(int id) throws VilleException;

    Ville getVilleByNom(String nom) throws VilleException;

    List<Ville> getVillesContenantNom(String nom) throws VilleException;

    List<Ville> getVillesPopulationMin(int min) throws VilleException;

    List<Ville> getVillesPopulationBetween(int min, int max) throws VilleException;

    List<Ville> getVillesFromDepartementPopulationMin(String codeDptm, int min) throws VilleException;

    List<Ville> getVillesFromDepartementPopulationBetween(String codeDptm, int min, int max) throws VilleException;

    List<Ville> getVillesNPlusPeupleesFromDepartement(int top, String codeDptm) throws VilleException;

    void addVille(Ville ville) throws VilleException;

    void updateVille(int id, Ville ville) throws VilleException;

    void deleteVille(int id) throws VilleException;

}
