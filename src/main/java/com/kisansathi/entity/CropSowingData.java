// CropSowingData.java
package com.kisansathi.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class CropSowingData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String crop;
    private String soilType;
    private String region;           // Madhya Pradesh, Rajasthan etc.
    private String bestSowingWindow;
    private String optimalTempRange;
    private int minRainfallMm;
    private String recommendedVarieties;
    private String tips;
}