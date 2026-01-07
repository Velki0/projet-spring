package fr.diginamic.controleurs;

import fr.diginamic.dtos.DepartementDto;
import fr.diginamic.entites.Departement;
import fr.diginamic.entites.Ville;
import fr.diginamic.exceptions.DepartementException;
import fr.diginamic.exceptions.VilleException;
import fr.diginamic.mappers.DepartementMapper;
import fr.diginamic.services.DepartementService;
import fr.diginamic.services.VilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dptms")
public class DepartementControleur implements IDepartementControleur {

    @Autowired
    private Validator validator;
    @Autowired
    private VilleService villeService;
    @Autowired
    private DepartementService departementService;
    @Autowired
    private DepartementMapper departementMapper;

    @GetMapping
    @Override
    public ResponseEntity<List<DepartementDto>> getDepartements(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer taille){

        Page<Departement> departements = departementService.getAllDepartements(page, taille);
        List<DepartementDto> departementsDto = departements.stream().map(dptm -> departementMapper.toDto(dptm)).toList();
        return ResponseEntity.ok(departementsDto);

    }

    @GetMapping(path = "/byId/{id}")
    @Override
    public ResponseEntity<DepartementDto> getDepartementsById(@PathVariable int id) throws DepartementException {

        Departement departements = departementService.getDepartementById(id);
        DepartementDto departementDto = departementMapper.toDto(departements);
        return ResponseEntity.ok(departementDto);

    }

    @GetMapping(path = "/byNom/{nom}")
    @Override
    public ResponseEntity<DepartementDto> getDepartementsByNom(@PathVariable String nom) throws DepartementException {

        Departement departements = departementService.getDepartementByNom(nom);
        DepartementDto departementDto = departementMapper.toDto(departements);
        return ResponseEntity.ok(departementDto);

    }

    @GetMapping(path = "/byCode/{codeDptm}")
    @Override
    public ResponseEntity<DepartementDto> getDepartementsByCode(@PathVariable String codeDptm) throws DepartementException {

        Departement departements = departementService.getDepartementByCode(codeDptm);
        DepartementDto departementDto = departementMapper.toDto(departements);
        return ResponseEntity.ok(departementDto);

    }

    @PostMapping(path = "/ajouter")
    @Override
    public ResponseEntity<String> addDepartement(@RequestBody DepartementDto departementDto) throws DepartementException {

        Errors resultat = validator.validateObject(departementDto);
        if (resultat.hasErrors()) {
            List<FieldError> errors = resultat.getFieldErrors();
            String messageErreur = errors.stream().map(e -> e.getField() + " " + e.getDefaultMessage() + "\n").collect(Collectors.joining());
            throw new DepartementException(messageErreur);
        }
        Departement departement = departementMapper.toEntity(departementDto);
        departementService.addDepartement(departement);
        return ResponseEntity.status(HttpStatus.CREATED).body("Le département : " + departement.getNom() + " a été créée avec succès.");

    }

    @PutMapping(path = "/modifier/{id}")
    @Override
    public ResponseEntity<String> updateDepartement(@PathVariable int id, @RequestBody DepartementDto departementDto) throws DepartementException {

        Errors resultat = validator.validateObject(departementDto);
        if (resultat.hasErrors()) {
            List<FieldError> errors = resultat.getFieldErrors();
            String messageErreur = errors.stream().map(e -> e.getField() + " " + e.getDefaultMessage() + "\n").collect(Collectors.joining());
            throw new DepartementException(messageErreur);
        }
        Departement departement = departementMapper.toEntity(departementDto);
        departementService.updateDepartement(id, departement);
        return ResponseEntity.ok("Le département : " + departement.getNom() + " a bien été modifiée.");

    }

    @DeleteMapping(path = "/supprimer/{id}")
    @Override
    public ResponseEntity<String> deleteDepartement(@PathVariable int id) throws DepartementException {

        Departement departement = departementService.getDepartementById(id);
        departement.getVilles().stream().map(Ville::getId).forEach(villeId -> {
            try {
                villeService.deleteVille(villeId);
            } catch (VilleException ignored) {}
        });
        departementService.deleteDepartement(id);
        return ResponseEntity.ok("Le département avec l'id :  " + id + " a bien été supprimée.");

    }

}
