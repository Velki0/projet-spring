package fr.diginamic.controleurs;

import fr.diginamic.entites.Ville;
import fr.diginamic.exceptions.VilleException;
import fr.diginamic.services.DepartementService;
import fr.diginamic.services.VilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/villes")
public class VilleControleur {

    @Autowired
    private Validator validator;
    @Autowired
    private VilleService villeService;
    @Autowired
    private DepartementService departementService;

    @GetMapping
    public ResponseEntity<List<Ville>> getVilles() {

        List<Ville> villes = villeService.getAllVille();
        return ResponseEntity.ok(villes);

    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<String> getVilleById(@PathVariable int id) throws VilleException {

        Ville ville = villeService.getVilleById(id);
        return ResponseEntity.ok("Voici la ville demandée : " + ville);

    }

    @GetMapping(path = "/{nom}")
    public ResponseEntity<String> getVilles(@PathVariable String nom) throws VilleException {

        Ville ville = villeService.getVilleByName(nom);
        return ResponseEntity.ok("Voici la ville demandée : " + ville);

    }

    @PostMapping
    public ResponseEntity<String> addVille(@RequestBody Ville ville) throws VilleException {

        Errors resultat = validator.validateObject(ville);
        if (resultat.hasErrors()) {
            List<FieldError> errors = resultat.getFieldErrors();
            String messageErreur = errors.stream().map(e -> e.getField() + " " + e.getDefaultMessage()).collect(Collectors.joining());
            throw new VilleException(messageErreur);
        }
        villeService.addVille(ville);
        return ResponseEntity.status(HttpStatus.CREATED).body("La ville de : " + ville.getNom() + " a été créée avec succès.");

    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<String> updateVille(@PathVariable int id, @RequestBody Ville ville) throws VilleException {

        Errors resultat = validator.validateObject(ville);
        if (resultat.hasErrors()) {
            List<FieldError> errors = resultat.getFieldErrors();
            String messageErreur = errors.stream().map(e -> e.getField() + " " + e.getDefaultMessage()).collect(Collectors.joining());
            throw new VilleException(messageErreur);
        }
        ville.setDepartement(departementService.addDepartement(ville.getDepartement()));
        villeService.updateVille(id, ville);
        return ResponseEntity.ok("La ville de " + ville.getNom() + " a bien été modifiée.");

    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteVille(@PathVariable int id) throws VilleException {

        villeService.deleteVille(id);
        return ResponseEntity.ok("La ville avec l'id " + id + " a bien été supprimée.");

    }

}
