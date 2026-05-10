package com.tolls.smart_toll_system.dto;

import jakarta.validation.constraints.DecimalMin;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class WalletRequest {
    @DecimalMin(value = "1.0", message = "Minimum recharge amount is ₹1")
    private BigDecimal amount;
}