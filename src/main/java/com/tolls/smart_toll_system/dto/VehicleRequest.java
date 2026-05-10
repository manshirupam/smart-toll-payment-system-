package com.tolls.smart_toll_system.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VehicleRequest {
    @NotBlank(message = "Registration number is required")
    private String regNumber;

    @NotBlank(message = "FASTag ID is required")
    private String fastagId;

    @NotBlank(message = "Vehicle type is required")
    private String vehicleType;
}