package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.entites.Ville;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(path = "/villes")
public class VilleControleur {

    private static final Set<Ville> listeVilles = new HashSet<>();

    @PostConstruct
    public void initData() {

        listeVilles.add(new Ville("Nice", 343_000));
        listeVilles.add(new Ville("Carcassonne", 47_800));
        listeVilles.add(new Ville("Narbonne", 53_400));
        listeVilles.add(new Ville("Lyon", 484_000));
        listeVilles.add(new Ville("Foix", 9_700));
        listeVilles.add(new Ville("Pau", 77_200));
        listeVilles.add(new Ville("Marseille", 850_700));
        listeVilles.add(new Ville("Tarbes", 40_600));

    }

    @GetMapping
    public ResponseEntity<Set<Ville>> getVilles() {

        return ResponseEntity.ok(listeVilles);

    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<String> getVilleById(@PathVariable int id) {

        Optional<Ville> villeEnBase = listeVilles.stream().filter(v -> v.getId() == id).findFirst();
        if (villeEnBase.isPresent()) {
            return ResponseEntity.ok("Voici la ville demandée : " + villeEnBase.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La ville avec l'id : " + id + " n'est pas présente.");
        }

    }

    @PostMapping
    public ResponseEntity<String> addVille(@RequestBody Ville ville) {

        Optional<Ville> villeEnBase = listeVilles.stream().filter(v -> v.getNom().equals(ville.getNom())).findFirst();
        if(villeEnBase.isEmpty()) {
            listeVilles.add(ville);
            return ResponseEntity.status(HttpStatus.CREATED).body("La ville de : " + ville.getNom() + " a été créée avec succès.");
        } else {
            return ResponseEntity.badRequest().body("La ville de : " + ville.getNom() + " existe déjà.");
        }

    }

    @PutMapping
    public ResponseEntity<String> updateVille(@RequestBody Ville ville) {

        Optional<Ville> villeEnBase = listeVilles.stream().filter(v -> v.getNom().equals(ville.getNom())).findFirst();
        if(villeEnBase.isPresent()) {
            villeEnBase.get().setNom(ville.getNom());
            villeEnBase.get().setPopulation(ville.getPopulation());
            return ResponseEntity.ok("La ville de " + ville.getNom() + " a bien été modifiée.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La ville de : " + ville.getNom() + " n'est pas présente. Impossible de la modifier.");
        }

    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteVille(@PathVariable int id) {

        Optional<Ville> villeEnBase = listeVilles.stream().filter(v -> v.getId() == id).findFirst();
        if(villeEnBase.isPresent()) {
            listeVilles.remove(villeEnBase.get());
            return ResponseEntity.ok("La ville de " + villeEnBase.get().getNom() + " a bien été supprimée.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La ville avec l'id : " + id + " n'est pas présente. Impossible de la supprimer.");
        }

    }

}
