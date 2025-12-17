package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.entites.Ville;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/villes")
public class VilleControleur {

    @GetMapping
    public List<Ville> getVilles() {

        List<Ville> listeVilles = new ArrayList<>();
        listeVilles.add(new Ville("Nice", 343_000));
        listeVilles.add(new Ville("Carcassonne", 47_800));
        listeVilles.add(new Ville("Narbonne", 53_400));
        listeVilles.add(new Ville("Lyon", 484_000));
        listeVilles.add(new Ville("Foix", 9_700));
        listeVilles.add(new Ville("Pau", 77_200));
        listeVilles.add(new Ville("Marseille", 850_700));
        listeVilles.add(new Ville("Tarbes", 40_600));
        return listeVilles;

    }

}
