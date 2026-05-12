package com.kisansathi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "weather_alerts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeatherAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String alertType;     // Heavy Rain, Heatwave, etc.

    private String severity;      // HIGH, MEDIUM, LOW

    @Column(columnDefinition = "TEXT")
    private String message;

    private String location;      // ← यही field चाहिए था

    private String district = "Indore";

    private LocalDateTime alertTime = LocalDateTime.now();

    private boolean isActive = true;
}