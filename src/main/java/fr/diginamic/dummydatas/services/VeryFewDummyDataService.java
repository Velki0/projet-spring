package fr.diginamic.dummydatas.services;

import fr.diginamic.entites.Departement;
import fr.diginamic.entites.Ville;
import fr.diginamic.exceptions.VilleException;
import fr.diginamic.services.IDepartementService;
import fr.diginamic.services.IVilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VeryFewDummyDataService implements IDummyDataService {

    @Autowired
    private IVilleService villeService;
    @Autowired
    private IDepartementService departementService;

    @Override
    public void insererVillesEtDepartements() {

        List<Ville> villes = new ArrayList<>();
        villes.add(new Ville("Nice", 353_000, new Departement("06", "Alpes-Maritimes")));
        villes.add(new Ville("Carcassonne", 47_800, new Departement("11", "Aude")));
        villes.add(new Ville("Narbonne", 53_400, new Departement("11", "Aude")));
        villes.add(new Ville("Lyon", 484_000,  new Departement("69", "Rhône")));
        villes.add(new Ville("Foix", 9_700, new Departement("09", "Ariège Pyrénées")));
        villes.add(new Ville("Marseille", 850_700, new Departement("13", "Bouches-du-Rhône")));
        villes.add(new Ville("Tarbes", 40_600, new Departement("65", "Hautes-Pyrénées")));

        // Insertion en base de données.
        villes.forEach(ville -> ville.setDepartement(departementService.addDepartementDeVille(ville.getDepartement())));
        villes.forEach(v -> {
            try {
                villeService.addVille(v);
            } catch (VilleException ignored) {}
        });

    }

}
