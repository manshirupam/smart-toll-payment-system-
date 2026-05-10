package com.tolls.smart_toll_system.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class TollBoothRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Location is required")
    private String location;

    @DecimalMin(value = "1.0", message = "Fee must be at least ₹1")
    private BigDecimal feeAmount;
}