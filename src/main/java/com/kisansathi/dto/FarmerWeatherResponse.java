package com.kisansathi.dto;

import com.kisansathi.entity.Alert;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FarmerWeatherResponse {

    private CurrentWeather current;
    private List<Alert> alerts;
    private List<DailyForecast> forecast;
    private String voiceMessage;           // Hindi voice alert text
    private String location;
    private LocalDateTime lastUpdated;
    private boolean offlineMode;
    private String advisorySummary;
}