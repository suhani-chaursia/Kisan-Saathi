package com.kisansathi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfitResponse {
    
    private double revenue;
    private double totalCost;
    private double netProfit;
    private int riskPercentage;           // Must match builder method
    private String aiSuggestion;
    private String bestAction;
    private String weatherImpact;
}