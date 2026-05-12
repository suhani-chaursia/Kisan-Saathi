// SowingResponse.java
package com.kisansathi.dto;

import lombok.Data;
import java.util.List;

@Data
public class SowingResponse {
    private String bestSowingDate;
    private String riskLevel;           // LOW / MEDIUM / HIGH
    private String riskColor;           // GREEN / ORANGE / RED
    private String soilTemperature;
    private String rainfallForecast;
    private String recommendation;
    private List<String> tips;
    private int confidence;

    // NEW FIELDS - As per your review
    private String aiInsight;
    private String weatherAlert;
    private String decisionReason;
    private String farmingAdvice;
}