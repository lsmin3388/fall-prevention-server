package com.happyaging.fallprevention.region.api;

import com.happyaging.fallprevention.region.dto.RegionRequestDto;
import com.happyaging.fallprevention.region.dto.RegionUpdateRequestDto;
import com.happyaging.fallprevention.region.entity.Region;
import com.happyaging.fallprevention.region.service.RegionService;
import com.happyaging.fallprevention.util.api.ApiResponse;
import com.happyaging.fallprevention.util.api.ApiSuccessResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/region")
public class RegionController {
    private final RegionService regionService;

    @PostMapping("/create")
    public ResponseEntity<ApiSuccessResult<Region>> createRegion(
            @Valid @RequestBody RegionRequestDto requestDto
            ) {
        Region region = regionService.createRegion(requestDto.toEntity());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(HttpStatus.CREATED,region));
    }

    @GetMapping("/list")
    public ResponseEntity<ApiSuccessResult<List<Region>>> getAllRegions() {
        List<Region> regions = regionService.getAllRegions();

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(HttpStatus.OK,regions));
    }

    @PutMapping("/update")
    public ResponseEntity<ApiSuccessResult<Object>> updateRegion(
            @Valid @RequestBody RegionUpdateRequestDto requestDto
            ) {
        Region region = regionService.updateRegion(requestDto.currentRegionName(), requestDto.newRegionName());

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(HttpStatus.OK, region));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiSuccessResult<Object>> deleteRegion(
            @RequestParam String regionName
            ) {
        regionService.deleteRegion(regionName);

        return ResponseEntity.noContent().build();
    }
}