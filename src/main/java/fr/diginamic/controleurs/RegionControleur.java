package fr.diginamic.controleurs;

import fr.diginamic.dtos.RegionDto;
import fr.diginamic.entites.Region;
import fr.diginamic.mappers.IRegionMapper;
import fr.diginamic.services.IRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/regions")
public class RegionControleur implements IRegionControleur {

    @Autowired
    private IRegionService regionService;
    @Autowired
    private IRegionMapper regionMapper;

    @GetMapping
    @Override
    public ResponseEntity<List<RegionDto>> getRegions(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer taille){

        Page<Region> regions = regionService.getAllRegions(page, taille);
        List<RegionDto> regionsDto = regions.stream().map(region -> regionMapper.toDto(region)).toList();
        return ResponseEntity.ok(regionsDto);

    }

}
