package com.tolls.smart_toll_system.entity;



import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "toll_booths")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TollBooth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    @Column(name = "fee_amount", nullable = false)
    private BigDecimal feeAmount;

    @Column(name = "is_active")
    private boolean active = true;
}