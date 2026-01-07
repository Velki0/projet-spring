package fr.diginamic.controleurs;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import fr.diginamic.dtos.DepartementDto;
import fr.diginamic.entites.Departement;
import fr.diginamic.entites.Ville;
import fr.diginamic.exceptions.DepartementException;
import fr.diginamic.exceptions.VilleException;
import fr.diginamic.mappers.IDepartementMapper;
import fr.diginamic.services.IDepartementService;
import fr.diginamic.services.IVilleService;
import jakarta.servlet.http.HttpServletResponse;
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

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dptms")
public class DepartementControleur implements IDepartementControleur {

    @Autowired
    private Validator validator;
    @Autowired
    private IVilleService villeService;
    @Autowired
    private IDepartementService departementService;
    @Autowired
    private IDepartementMapper departementMapper;

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

    @GetMapping("/fiche/{codeDptm}")
    @Override
    public void exportDepartementPDF(@PathVariable String codeDptm, HttpServletResponse response) throws IOException, DocumentException, DepartementException {

        response.setHeader("Content-Disposition", "attachment; filename=\"departement.pdf\"");
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        Departement departementDB = departementService.getDepartementByCode(codeDptm);

//        document.open();
//        document.addTitle("Departement du " + departementDB.getCodeDepartement());
//        document.newPage();
//        BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
//        Phrase p1 = new Phrase("COUCOU", new Font(baseFont, 32.0f, 1, new BaseColor(0, 51, 80)));
//        document.add(p1);
//        document.close();
//        response.flushBuffer();

    }

}
