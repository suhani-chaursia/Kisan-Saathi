package com.kisansathi.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.kisansathi.dto.DailyForecast;

@Component
public class OpenMeteoClient {

    private final RestTemplate restTemplate = new RestTemplate();

    public WeatherData fetchWeatherData(double latitude, double longitude) {
        
        String url = "https://api.open-meteo.com/v1/forecast?" +
                "latitude=" + latitude +
                "&longitude=" + longitude +
                "&current=temperature_2m,relative_humidity_2m,apparent_temperature,weather_code,wind_speed_10m" +
                "&daily=weather_code,temperature_2m_max,temperature_2m_min,precipitation_sum" +
                "&timezone=Asia/Kolkata";

        @SuppressWarnings("unchecked")
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        if (response == null) {
            throw new RuntimeException("Failed to fetch weather data");
        }

        return mapToWeatherData(response);
    }

    @SuppressWarnings("unchecked")
    private WeatherData mapToWeatherData(Map<String, Object> response) {
        WeatherData data = new WeatherData();

        Map<String, Object> current = (Map<String, Object>) response.get("current");
        Map<String, Object> daily = (Map<String, Object>) response.get("daily");

        if (current != null) {
            data.setCurrentTemperature((int) Math.round((Double) current.get("temperature_2m")));
            data.setFeelsLike((int) Math.round((Double) current.getOrDefault("apparent_temperature", 0.0)));
            data.setHumidity((int) Math.round((Double) current.get("relative_humidity_2m")));
            data.setWindSpeed((Double) current.getOrDefault("wind_speed_10m", 0.0));
        }

        if (daily != null) {
            List<Double> maxTemps = (List<Double>) daily.get("temperature_2m_max");
            List<Double> minTemps = (List<Double>) daily.get("temperature_2m_min");
            List<Double> precipitation = (List<Double>) daily.get("precipitation_sum");

            List<DailyForecast> forecasts = new ArrayList<>();
            String[] dayNames = {"कल", "परसों", "तत्पश्चात"};

            for (int i = 1; i <= 3 && i < maxTemps.size(); i++) {
                DailyForecast forecast = DailyForecast.builder()
                        .day(dayNames[i - 1])
                        .maxTemp((int) Math.round(maxTemps.get(i)))
                        .minTemp((int) Math.round(minTemps.get(i)))
                        .rainMm(precipitation.get(i))
                        .build();
                forecasts.add(forecast);
            }
            data.setDailyForecasts(forecasts);
        }

        return data;
    }

    // Inner Class
    public static class WeatherData {
        private int currentTemperature;
        private int feelsLike;
        private int humidity;
        private double windSpeed;
        private List<DailyForecast> dailyForecasts;

        public int getCurrentTemperature() { return currentTemperature; }
        public void setCurrentTemperature(int val) { this.currentTemperature = val; }
        public int getFeelsLike() { return feelsLike; }
        public void setFeelsLike(int val) { this.feelsLike = val; }
        public int getHumidity() { return humidity; }
        public void setHumidity(int val) { this.humidity = val; }
        public double getWindSpeed() { return windSpeed; }
        public void setWindSpeed(double val) { this.windSpeed = val; }
        public List<DailyForecast> getDailyForecasts() { return dailyForecasts; }
        public void setDailyForecasts(List<DailyForecast> val) { this.dailyForecasts = val; }
    }
}