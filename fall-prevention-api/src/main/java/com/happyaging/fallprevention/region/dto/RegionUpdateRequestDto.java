package com.happyaging.fallprevention.region.dto;

import jakarta.validation.constraints.NotBlank;

public record RegionUpdateRequestDto(
        @NotBlank(message = "Current region name must not be blank")
        String currentRegionName,
        @NotBlank(message = "New region name must not be blank")
        String newRegionName
) {

}
