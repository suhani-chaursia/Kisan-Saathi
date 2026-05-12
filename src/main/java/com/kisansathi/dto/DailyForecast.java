package com.kisansathi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyForecast {

    private String day;                    // कल, परसों, etc.
    private int maxTemp;
    private int minTemp;
    private String condition;
    private String conditionHindi;
    private double rainMm;
    private String farmingAdvice;
    private String farmingAdviceHindi;
}