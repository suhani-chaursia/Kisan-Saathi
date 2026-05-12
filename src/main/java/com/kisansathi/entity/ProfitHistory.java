package com.kisansathi.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "profit_history")
@Getter @Setter
public class ProfitHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String crop;
    private Double acres;
    private Double estimatedProfit;
    private LocalDateTime calculatedAt = LocalDateTime.now();
}