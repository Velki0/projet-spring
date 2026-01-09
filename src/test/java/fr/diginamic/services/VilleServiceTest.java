package fr.diginamic.services;

import fr.diginamic.ProjetSpringApplication;
import fr.diginamic.entites.Departement;
import fr.diginamic.entites.Region;
import fr.diginamic.entites.Ville;
import fr.diginamic.exceptions.VilleException;
import fr.diginamic.repositories.VilleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = ProjetSpringApplication.class)
@ActiveProfiles("test")
public class VilleServiceTest {

    @Autowired
    private IVilleService villeService;
    @Autowired
    private VilleRepository villeRepository;

    @Transactional
    @Test
    public void testGetVilleById() throws VilleException {

        Ville montpellier = new Ville("34520", "Montpellier", 235000, new Departement("34", "Hérault", new Region()));
        Ville lyon = new Ville("69123", "Lyon", 553000, new Departement("69", "Rhône", new Region()));

        villeRepository.save(montpellier);
        villeRepository.save(lyon);

        Ville ville = villeService.getVilleById(2);

        assertEquals("Lyon", ville.getNom());
        assertEquals(553000, ville.getPopulation());
        assertEquals("Rhône", ville.getDepartement().getNom());

    }


}
