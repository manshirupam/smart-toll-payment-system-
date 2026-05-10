package com.tolls.smart_toll_system.entity;



import jakarta.persistence.*;
        import lombok.*;

@Entity
@Table(name = "vehicles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reg_number", nullable = false, unique = true)
    private String regNumber;

    @Column(name = "fastag_id", nullable = false, unique = true)
    private String fastagId;

    @Column(name = "vehicle_type")
    private String vehicleType;

    @Column(name = "is_blacklisted")
    private boolean blacklisted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}

