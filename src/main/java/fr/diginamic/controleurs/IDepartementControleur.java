package fr.diginamic.controleurs;

import com.itextpdf.text.DocumentException;
import fr.diginamic.dtos.DepartementDto;
import fr.diginamic.exceptions.DepartementException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

public interface IDepartementControleur {

    @Operation(summary = "Retourne la liste de tous les départements.")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200",
                    description = "Liste des départements au format JSON.",
                    content={@Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = DepartementDto.class)))})
    })
    ResponseEntity<List<DepartementDto>> getDepartements(
            @Parameter(description = "Page à afficher parmi les résultats.", example = "2")Integer page,
            @Parameter(description = "Taille du nombre de résultats dans la page.", example = "10")Integer taille
    );

    @Operation(summary = "Retourne un département à partir de son identifiant.")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200",
                    description = "Département au format JSON.",
                    content={@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DepartementDto.class)),
                    }),
            @ApiResponse(responseCode = "400",
                    description = "Département non trouvé.",
                    content = @Content())
    })
    ResponseEntity<DepartementDto> getDepartementsById(
            @Parameter(description = "Identifiant du département à récupérer.", example = "3", required = true)int id
    ) throws DepartementException;

    @Operation(summary = "Retourne un département à partir de son nom.")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200",
                    description = "Département au format JSON.",
                    content={@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DepartementDto.class)),
                    }),
            @ApiResponse(responseCode = "400",
                    description = "Département non trouvé.",
                    content = @Content())
    })
    ResponseEntity<DepartementDto> getDepartementsByNom(
            @Parameter(description = "Nom du département à récupérer.", example = "Seine-Maritime", required = true)String nom
    ) throws DepartementException;

    @Operation(summary = "Retourne un département à partir de son code.")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200",
                    description = "Département au format JSON.",
                    content= {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DepartementDto.class)),
                    }),
            @ApiResponse(responseCode = "400",
                    description = "Département non trouvé.",
                    content = @Content())
    })
    ResponseEntity<DepartementDto> getDepartementsByCode(
            @Parameter(description = "Code du département à récupérer.", example = "34", required = true)String codeDptm
    ) throws DepartementException;

    @Operation(summary = "Ajoute un département.")
    @ApiResponses(value={
            @ApiResponse(responseCode = "201",
                    description = "L'ajout du département a été effectué.",
                    content= @Content()),
            @ApiResponse(responseCode = "400",
                    description = "Département renseigné non valide ou déjà existant.",
                    content = @Content())
    })
    ResponseEntity<String> addDepartement(DepartementDto departementDto) throws DepartementException;

    @Operation(summary = "Modifie un département.")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200",
                    description = "La modification du département a été effectuée.",
                    content= @Content()),
            @ApiResponse(responseCode = "400",
                    description = "Département renseigné non valide ou introuvable.",
                    content = @Content())
    })
    ResponseEntity<String> updateDepartement(
            @Parameter(description = "Identifiant du département à récupérer.", example = "2", required = true)int id,
            DepartementDto departementDto
    ) throws DepartementException;

    @Operation(summary = "Supprime un département.")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200",
                    description = "La suppression du département a été effectuée.",
                    content= @Content()),
            @ApiResponse(responseCode = "400",
                    description = "Département introuvable.",
                    content = @Content())
    })
    ResponseEntity<String> deleteDepartement(
            @Parameter(description = "Identifiant du département à récupérer.", example = "2", required = true)int id
    ) throws DepartementException;

    void exportDepartementPDF(String codeDptm, HttpServletResponse response) throws IOException, DocumentException, DepartementException;

}
