package com.kisansathi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "mandi_prices")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MandiPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String crop;
    private int pricePerQuintal;
    private double changePercent;
    private String trend;

    private LocalDateTime lastUpdated;
}