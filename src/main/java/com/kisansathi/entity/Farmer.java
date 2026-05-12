package com.kisansathi.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "farmers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Farmer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, length = 15)
    private String mobileNumber;

    private String village;
    private String district = "";
    private String state = "";

    @Column(name = "land_size")
    private Double landSize;

    @Column(name = "registered_at")
    private LocalDateTime registeredAt;

    // Constructor for new user
    
}