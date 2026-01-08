package fr.diginamic.controleurs;

import fr.diginamic.dtos.RegionDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IRegionControleur {

    @Operation(summary = "Retourne la liste de toutes les régions.")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200",
                    description = "Liste des régions au format JSON.",
                    content={@Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = RegionDto.class)))})
    })
    ResponseEntity<List<RegionDto>> getRegions(
            @Parameter(description = "Page à afficher parmi les résultats.", example = "2")Integer page,
            @Parameter(description = "Taille du nombre de résultats dans la page.", example = "10")Integer taille
    );

}
