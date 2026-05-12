package com.kisansathi.service;

import java.time.LocalDateTime;
import java.util.List;

import org.json.JSONObject;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.kisansathi.client.OpenMeteoClient;
import com.kisansathi.dto.CurrentWeather;
import com.kisansathi.dto.DailyForecast;
import com.kisansathi.dto.FarmerWeatherRequest;
import com.kisansathi.dto.FarmerWeatherResponse;
import com.kisansathi.entity.Alert;

@Service
public class WeatherService {

    private final OpenMeteoClient openMeteoClient;
    private final FarmingRuleEngine farmingRuleEngine;

    public WeatherService(OpenMeteoClient openMeteoClient, FarmingRuleEngine farmingRuleEngine) {
        this.openMeteoClient = openMeteoClient;
        this.farmingRuleEngine = farmingRuleEngine;
    }

    // ============== MAIN METHOD (Used by weather.html) ==============
    @Cacheable(value = "farmerWeather", key = "#request.latitude + '_' + #request.longitude + '_' + #request.crop")
    public FarmerWeatherResponse getFarmerWeatherIntelligence(FarmerWeatherRequest request) {
        OpenMeteoClient.WeatherData rawWeather = openMeteoClient.fetchWeatherData(
                request.getLatitude(), request.getLongitude());

        CurrentWeather current = CurrentWeather.builder()
                .temperature(rawWeather.getCurrentTemperature())
                .feelsLike(rawWeather.getFeelsLike())
                .maxTemp(rawWeather.getCurrentTemperature() + 5)
                .minTemp(rawWeather.getCurrentTemperature() - 10)
                .humidity(rawWeather.getHumidity())
                .windSpeed(rawWeather.getWindSpeed())
                .condition("आंशिक बादल")
                .conditionHindi("आंशिक बादल")
                .rainProbability(calculateRainProbability(rawWeather))
                .aqi(45)
                .aqiCategory("Good")
                .location("छतरपुर, मध्य प्रदेश")
                .build();

        List<Alert> alerts = farmingRuleEngine.generateAlerts(current, request.getCrop());
        List<DailyForecast> forecasts = rawWeather.getDailyForecasts();
        farmingRuleEngine.enrichForecastWithAdvice(forecasts, request.getCrop());

        String voiceMessage = farmingRuleEngine.generateVoiceMessage(current, alerts, request.getCrop());

        return FarmerWeatherResponse.builder()
                .current(current)
                .alerts(alerts)
                .forecast(forecasts)
                .voiceMessage(voiceMessage)
                .location(current.getLocation())
                .lastUpdated(LocalDateTime.now())
                .offlineMode(false)
                .build();
    }

    // ============== NEW METHOD for SowingService ==============
    public JSONObject getWeather(String location) {
        // For now returning demo data (you can enhance later with geocoding)
        try {
            // Default coordinates for Chhatarpur
            OpenMeteoClient.WeatherData raw = openMeteoClient.fetchWeatherData(24.916, 79.581);

            JSONObject json = new JSONObject();
            JSONObject current = new JSONObject();
            current.put("temperature_2m", raw.getCurrentTemperature());
            current.put("soil_temperature_0cm", 26.5); // Mock soil temp
            current.put("precipitation", 5.0);

            json.put("current", current);
            return json;

        } catch (Exception e) {
            // Fallback
            JSONObject json = new JSONObject();
            JSONObject current = new JSONObject();
            current.put("temperature_2m", 28);
            current.put("soil_temperature_0cm", 25);
            current.put("precipitation", 0);
            json.put("current", current);
            return json;
        }
    }

    // ============== Helper Methods ==============
    private double calculateRainProbability(OpenMeteoClient.WeatherData data) {
        return Math.min(85.0, data.getDailyForecasts().stream()
                .mapToDouble(DailyForecast::getRainMm)
                .sum() * 8);
    }

    public FarmerWeatherResponse getDemoResponse() {
        CurrentWeather current = CurrentWeather.builder()
                .temperature(34)
                .maxTemp(38)
                .humidity(52)
                .windSpeed(18)
                .conditionHindi("साफ आसमान")
                .location("छतरपुर, मध्य प्रदेश")
                .build();

        return FarmerWeatherResponse.builder()
                .current(current)
                .alerts(List.of())
                .forecast(List.of())
                .voiceMessage("आज छतरपुर में मौसम सामान्य है। गेहूँ की फसल के लिए अच्छा दिन है।")
                .location("छतरपुर, मध्य प्रदेश")
                .lastUpdated(LocalDateTime.now())
                .offlineMode(true)
                .build();
    }
}