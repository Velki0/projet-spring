package fr.diginamic.controleurs;

import fr.diginamic.dtos.VilleDto;
import fr.diginamic.entites.Ville;
import fr.diginamic.exceptions.VilleException;
import fr.diginamic.mappers.IVilleMapper;
import fr.diginamic.services.IDepartementService;
import fr.diginamic.services.IRegionService;
import fr.diginamic.services.IVilleService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/villes")
public class VilleControleur implements IVilleControleur {

    @Autowired
    private Validator validator;
    @Autowired
    private IVilleService villeService;
    @Autowired
    private IDepartementService departementService;
    @Autowired
    private IRegionService regionService;
    @Autowired
    private IVilleMapper villeMapper;

    @GetMapping
    @Override
    public ResponseEntity<List<VilleDto>> getVilles(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer taille) {

        Page<Ville> villes = villeService.getAllVilles(page, taille);
        List<VilleDto> villesDto = villes.stream().map(v -> villeMapper.toDto(v)).toList();
        return ResponseEntity.ok(villesDto);

    }

    @GetMapping(path = "/byId/{id}")
    @Override
    public ResponseEntity<VilleDto> getVilleById(@PathVariable int id) throws VilleException {

        Ville ville = villeService.getVilleById(id);
        VilleDto villeDto = villeMapper.toDto(ville);
        return ResponseEntity.ok(villeDto);

    }

    @GetMapping(path = "/byNom/{nom}")
    @Override
    public ResponseEntity<VilleDto> getVillesByNom(@PathVariable String nom) throws VilleException {

        Ville ville = villeService.getVilleByNom(nom);
        VilleDto villeDto = villeMapper.toDto(ville);
        return ResponseEntity.ok(villeDto);

    }

    @GetMapping(path = "/contenir/{nom}")
    @Override
    public ResponseEntity<List<VilleDto>> getVillesContientNom(@PathVariable String nom) throws VilleException {

        List<Ville> villesDB = villeService.getVillesContenantNom(nom);
        return ResponseEntity.ok(villesDB.stream().map(v -> villeMapper.toDto(v)).toList());

    }

    @GetMapping(path = "/popMin")
    @Override
    public ResponseEntity<List<VilleDto>> getVillesPopMin(@RequestParam int min) throws VilleException {

        List<Ville> villesDB = villeService.getVillesPopulationMin(min);
        return ResponseEntity.ok(villesDB.stream().map(v -> villeMapper.toDto(v)).toList());

    }

    @GetMapping(path = "/popBetween")
    @Override
    public ResponseEntity<List<VilleDto>> getVillesPopBetween(@RequestParam int min, @RequestParam int max) throws VilleException {

        List<Ville> villesDB = villeService.getVillesPopulationBetween(min, max);
        return ResponseEntity.ok(villesDB.stream().map(v -> villeMapper.toDto(v)).toList());

    }

    @GetMapping(path = "/popMinFromDptm")
    @Override
    public ResponseEntity<List<VilleDto>> getVillesFromDptmPopMin(@RequestParam int min, @RequestParam String codeDptm) throws VilleException {

        List<Ville> villesDB = villeService.getVillesFromDepartementPopulationMin(codeDptm, min);
        return ResponseEntity.ok(villesDB.stream().map(v -> villeMapper.toDto(v)).toList());

    }

    @GetMapping(path = "/popBetweenFromDptm")
    @Override
    public ResponseEntity<List<VilleDto>> getVillesFromDptmPopBetween(@RequestParam int min, @RequestParam int max, @RequestParam String codeDptm) throws VilleException {

        List<Ville> villesDB = villeService.getVillesFromDepartementPopulationBetween(codeDptm, min, max);
        return ResponseEntity.ok(villesDB.stream().map(v -> villeMapper.toDto(v)).toList());

    }

    @GetMapping(path = "/nPlusGrandesFromDptm")
    @Override
    public ResponseEntity<List<VilleDto>> getNPlusGrandesVillesFromDptm(@RequestParam int top, @RequestParam String codeDptm) throws VilleException {

        List<Ville> villesDB = villeService.getVillesNPlusPeupleesFromDepartement(top, codeDptm);
        return ResponseEntity.ok(villesDB.stream().map(v -> villeMapper.toDto(v)).toList());

    }

    @PostMapping(path = "/ajouter")
    @Override
    public ResponseEntity<String> addVille(@RequestBody VilleDto villeDto) throws VilleException {

        Errors resultat = validator.validateObject(villeDto);
        if (resultat.hasErrors()) {
            List<FieldError> errors = resultat.getFieldErrors();
            String messageErreur = errors.stream().map(e -> e.getField() + " " + e.getDefaultMessage() + "\n").collect(Collectors.joining());
            throw new VilleException(messageErreur);
        }
        Ville ville = villeMapper.toEntity(villeDto);
        ville.getDepartement().setRegion(regionService.addRegionDeDepartement(ville.getDepartement().getRegion()));
        ville.setDepartement(departementService.addDepartementDeVille(ville.getDepartement()));
        villeService.addVille(ville);
        return ResponseEntity.status(HttpStatus.CREATED).body("La ville de : " + ville.getNom() + " a été créée avec succès.");

    }

    @PutMapping(path = "/modifier/{id}")
    @Override
    public ResponseEntity<String> updateVille(@PathVariable int id, @RequestBody VilleDto villeDto) throws VilleException {

        Errors resultat = validator.validateObject(villeDto);
        if (resultat.hasErrors()) {
            List<FieldError> errors = resultat.getFieldErrors();
            String messageErreur = errors.stream().map(e -> e.getField() + " " + e.getDefaultMessage() + "\n").collect(Collectors.joining());
            throw new VilleException(messageErreur);
        }
        Ville ville = villeMapper.toEntity(villeDto);
        ville.getDepartement().setRegion(regionService.addRegionDeDepartement(ville.getDepartement().getRegion()));
        ville.setDepartement(departementService.addDepartementDeVille(ville.getDepartement()));
        villeService.updateVille(id, ville);
        return ResponseEntity.ok("La ville de " + ville.getNom() + " a bien été modifiée.");

    }

    @DeleteMapping(path = "/supprimer/{id}")
    @Override
    public ResponseEntity<String> deleteVille(@PathVariable int id) throws VilleException {

        villeService.deleteVille(id);
        return ResponseEntity.ok("La ville avec l'id " + id + " a bien été supprimée.");

    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/fiche/{min}")
    @Override
    public void exportVillesPopMinCSV(@PathVariable int min, HttpServletResponse response) throws IOException, VilleException {

        response.setHeader("Content-Disposition", "attachment; filename=\"villesavecaumoins" + min + "habitants.csv\"");
        response.getWriter().append("Nom de la ville;Nombre d’habitants;Code département;Nom du département\n");
        List<VilleDto> villesDB = villeService.getVillesPopulationMin(min).stream().map(ville -> villeMapper.toDto(ville)).toList();
        if (!villesDB.isEmpty()) {
            for (VilleDto ville : villesDB) {
                response.getWriter()
                        .append(ville.getNom())
                        .append(";")
                        .append(Integer.toString(ville.getPopulation()))
                        .append(";")
                        .append(ville.getCodeDepartement())
                        .append(";")
                        .append(ville.getNom())
                        .append("\n");
            }
            response.flushBuffer();
        } else {
            throw new VilleException("Impossible de générer le fichier CSV demandé. Aucune ville n'a été trouvée.");
        }

    }

}
