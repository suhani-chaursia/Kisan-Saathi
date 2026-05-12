package com.kisansathi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrentWeather {

    private int temperature;
    private int feelsLike;
    private int maxTemp;
    private int minTemp;
    private int humidity;
    private double windSpeed;
    private String condition;
    private String conditionHindi;
    private int aqi;
    private String aqiCategory;
    private double rainProbability;

    // Added for your errors
    private String location;
}