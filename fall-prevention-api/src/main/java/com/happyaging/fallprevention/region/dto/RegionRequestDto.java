package com.happyaging.fallprevention.region.dto;

import com.happyaging.fallprevention.region.entity.Region;
import jakarta.validation.constraints.NotBlank;

public record RegionRequestDto(
        @NotBlank(message = "Region name must not be blank")
        String regionName) {

    public  Region toEntity() {
        return Region.builder()
                .regionName(regionName)
                .build();
    }
}