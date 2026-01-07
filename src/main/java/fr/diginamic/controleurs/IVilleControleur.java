package fr.diginamic.controleurs;

import fr.diginamic.dtos.VilleDto;
import fr.diginamic.exceptions.VilleException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IVilleControleur {

    @Operation(summary = "Retourne la liste de toutes les villes.")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200",
                    description = "Liste des villes au format JSON.",
                    content={@Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = VilleDto.class)))})
    })
    ResponseEntity<List<VilleDto>> getVilles(
            @Parameter(description = "Page à afficher parmi les résultats.", example = "2")Integer page,
            @Parameter(description = "Taille du nombre de résultats dans la page.", example = "10")Integer taille
    );

    @Operation(summary = "Retourne une ville à partir de son identifiant.")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200",
                    description = "Ville au format JSON.",
                    content={@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = VilleDto.class)),
                    }),
            @ApiResponse(responseCode = "400",
                    description = "Ville non trouvée.",
                    content = @Content())
    })
    ResponseEntity<VilleDto> getVilleById(
            @Parameter(description = "Identifiant de la ville à récupérer.", example = "3", required = true)int id
    ) throws VilleException;

    @Operation(summary = "Retourne une ville à partir de son nom.")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200",
                    description = "Ville au format JSON.",
                    content={@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = VilleDto.class)),
                    }),
            @ApiResponse(responseCode = "400",
                    description = "Ville non trouvée.",
                    content = @Content())
    })
    ResponseEntity<VilleDto> getVillesByNom(
            @Parameter(description = "Nom de la ville à récupérer.", example = "Lyon", required = true)String nom
    ) throws VilleException;

    @Operation(summary = "Retourne une liste de villes dont le nom contient la chaine de caractères stipulée.")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200",
                    description = "Ville au format JSON.",
                    content={@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = VilleDto.class)),
                    }),
            @ApiResponse(responseCode = "400",
                    description = "Ville non trouvée.",
                    content = @Content())
    })
    ResponseEntity<List<VilleDto>> getVillesContientNom(
            @Parameter(description = "Chaine de caractères contenu dans le nom des villes à récupérer.", example = "Saint", required = true)String nom
    ) throws VilleException;

    @Operation(summary = "Retourne la liste de toutes les villes avec une population minimale.")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200",
                    description = "Liste des villes au format JSON.",
                    content={@Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = VilleDto.class)))}),
            @ApiResponse(responseCode = "400",
                    description = "Aucune correspondance de villes trouvée.",
                    content = @Content())
    })
    ResponseEntity<List<VilleDto>> getVillesPopMin(
            @Parameter(description = "Valeur minimale de population des villes à récupérer.", example = "30000", required = true)int min
    ) throws VilleException;

    @Operation(summary = "Retourne la liste de toutes les villes avec une population minimale et maximale.")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200",
                    description = "Liste des villes au format JSON.",
                    content={@Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = VilleDto.class)))}),
            @ApiResponse(responseCode = "400",
                    description = "Aucune correspondance de villes trouvée.",
                    content = @Content())
    })
    ResponseEntity<List<VilleDto>> getVillesPopBetween(
            @Parameter(description = "Valeur minimale de population des villes à récupérer.", example = "30000", required = true)int min,
            @Parameter(description = "Valeur maximale de population des villes à récupérer.", example = "100000", required = true)int max
    ) throws VilleException;

    @Operation(summary = "Retourne la liste de toutes les villes avec une population minimale dans un département précisé.")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200",
                    description = "Liste des villes au format JSON.",
                    content={@Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = VilleDto.class)))}),
            @ApiResponse(responseCode = "400",
                    description = "Aucune correspondance de villes trouvée.",
                    content = @Content())
    })
    ResponseEntity<List<VilleDto>> getVillesFromDptmPopMin(
            @Parameter(description = "Valeur minimale de population des villes à récupérer.", example = "30000", required = true)int min,
            @Parameter(description = "Code du département des villes à récupérer.", example = "34", required = true)String codeDptm
    ) throws VilleException;

    @Operation(summary = "Retourne la liste de toutes les villes avec une population minimale et maximale dans un département précisé.")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200",
                    description = "Liste des villes au format JSON.",
                    content={@Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = VilleDto.class)))}),
            @ApiResponse(responseCode = "400",
                    description = "Aucune correspondance de villes trouvée.",
                    content = @Content())
    })
    ResponseEntity<List<VilleDto>> getVillesFromDptmPopBetween(
            @Parameter(description = "Valeur minimale de population des villes à récupérer.", example = "30000", required = true)int min,
            @Parameter(description = "Valeur maximale de population des villes à récupérer.", example = "100000", required = true)int max,
            @Parameter(description = "Code du département des villes à récupérer.", example = "34", required = true)String codeDptm
    ) throws VilleException;

    @Operation(summary = "Retourne la liste des N plus grandes villes dans un département précisé.")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200",
                    description = "Liste des villes au format JSON.",
                    content={@Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = VilleDto.class)))}),
            @ApiResponse(responseCode = "400",
                    description = "Aucune correspondance de villes trouvée.",
                    content = @Content())
    })
    ResponseEntity<List<VilleDto>> getNPlusGrandesVillesFromDptm(
            @Parameter(description = "Chiffre N stipulant les N premières villes en terme de population à récupérer.", example = "10", required = true)int top,
            @Parameter(description = "Code du département des villes à récupérer.", example = "34", required = true)String codeDptm
    ) throws VilleException;

    @Operation(summary = "Ajoute une ville.")
    @ApiResponses(value={
            @ApiResponse(responseCode = "201",
                    description = "L'ajout de la ville a été effectué.",
                    content= @Content()),
            @ApiResponse(responseCode = "400",
                    description = "Ville renseignée non valide ou déjà existante.",
                    content = @Content())
    })
    ResponseEntity<String> addVille(VilleDto villeDto) throws VilleException;

    @Operation(summary = "Modifie une ville.")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200",
                    description = "La modification de la ville a été effectuée.",
                    content= @Content()),
            @ApiResponse(responseCode = "400",
                    description = "Ville renseignée non valide ou introuvable.",
                    content = @Content())
    })
    ResponseEntity<String> updateVille(
            @Parameter(description = "Identifiant de la ville à récupérer.", example = "3", required = true)int id,
            VilleDto villeDto
    ) throws VilleException;

    @Operation(summary = "Supprime une ville.")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200",
                    description = "La suppression de la ville a été effectuée.",
                    content= @Content()),
            @ApiResponse(responseCode = "400",
                    description = "Ville introuvable.",
                    content = @Content())
    })
    ResponseEntity<String> deleteVille(
            @Parameter(description = "Identifiant de la ville à récupérer.", example = "3", required = true)int id
    ) throws VilleException;

}
