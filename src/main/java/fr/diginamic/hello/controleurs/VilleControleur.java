package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.entites.Ville;
import fr.diginamic.hello.exceptions.VilleException;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(path = "/villes")
public class VilleControleur {

    @Autowired
    private Validator validator;
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
    public ResponseEntity<String> getVilleById(@PathVariable int id) throws VilleException {

        Optional<Ville> villeEnBase = listeVilles.stream().filter(v -> v.getId() == id).findFirst();
        if (villeEnBase.isPresent()) {
            return ResponseEntity.ok("Voici la ville demandée : " + villeEnBase.get());
        } else {
            throw new VilleException("La ville avec l'id : " + id + " n'est pas présente.");
        }

    }

    @PostMapping
    public ResponseEntity<String> addVille(@Valid @RequestBody Ville ville, BindingResult resultat) throws VilleException {

        if (resultat.hasErrors()) {
            List<FieldError> errors = resultat.getFieldErrors();
            throw new VilleException(errors.getFirst().getField() + " " + errors.getFirst().getDefaultMessage());
        }

        Optional<Ville> villeEnBase = listeVilles.stream().filter(v -> v.getNom().equals(ville.getNom())).findFirst();
        if(villeEnBase.isEmpty()) {
            listeVilles.add(ville);
            return ResponseEntity.status(HttpStatus.CREATED).body("La ville de : " + ville.getNom() + " a été créée avec succès.");
        } else {
            throw new VilleException("La ville de : " + ville.getNom() + " existe déjà.");
        }

    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<String> updateVille(@PathVariable int id, @RequestBody Ville ville) throws VilleException {

        Errors resultat = validator.validateObject(ville);
        if (resultat.hasErrors()) {
            List<FieldError> errors = resultat.getFieldErrors();
            throw new VilleException(errors.getFirst().getField() + " " + errors.getFirst().getDefaultMessage());
        }

        Optional<Ville> villeEnBase = listeVilles.stream().filter(v -> v.getId() == id).findFirst();
        if(villeEnBase.isPresent()) {
            villeEnBase.get().setNom(ville.getNom());
            villeEnBase.get().setPopulation(ville.getPopulation());
            return ResponseEntity.ok("La ville de " + ville.getNom() + " a bien été modifiée.");
        } else {
            throw new VilleException("La ville avec l'id : " + id + " n'est pas présente. Impossible de la modifier.");
        }

    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteVille(@PathVariable int id) throws VilleException {

        Optional<Ville> villeEnBase = listeVilles.stream().filter(v -> v.getId() == id).findFirst();
        if(villeEnBase.isPresent()) {
            listeVilles.remove(villeEnBase.get());
            return ResponseEntity.ok("La ville de " + villeEnBase.get().getNom() + " a bien été supprimée.");
        } else {
            throw new VilleException("La ville avec l'id : " + id + " n'est pas présente. Impossible de la supprimer.");
        }

    }

}
