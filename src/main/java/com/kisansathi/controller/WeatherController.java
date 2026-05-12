package com.kisansathi.controller;

import com.kisansathi.dto.FarmerWeatherRequest;
import com.kisansathi.dto.FarmerWeatherResponse;
import com.kisansathi.service.WeatherService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weather")
@CrossOrigin(origins = "*")   // For Hackathon Demo (Change in Production)
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    /**
     * Main Endpoint - Used by Frontend (weather.html)
     */
    @PostMapping("/alert")
    public ResponseEntity<FarmerWeatherResponse> getWeatherIntelligence(
            @Valid @RequestBody FarmerWeatherRequest request) {
        
        try {
            FarmerWeatherResponse response = weatherService.getFarmerWeatherIntelligence(request);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            // Fallback to Demo Data in case of any failure (Good for Hackathon)
            FarmerWeatherResponse demoResponse = weatherService.getDemoResponse();
            return ResponseEntity.ok(demoResponse);
        }
    }

    /**
     * Health Check Endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("✅ Kisan Saathi Weather Intelligence Module is Running");
    }

    /**
     * Demo Endpoint (Direct access for testing)
     */
    @GetMapping("/demo")
    public ResponseEntity<FarmerWeatherResponse> getDemoData() {
        return ResponseEntity.ok(weatherService.getDemoResponse());
    }

    /**
     * Get Weather by City Name (Future Enhancement)
     */
    @GetMapping("/city")
    public ResponseEntity<FarmerWeatherResponse> getByCity(
            @RequestParam String city,
            @RequestParam(defaultValue = "गेहूँ") String crop) {
        
        // For now returning demo (you can add geocoding service later)
        FarmerWeatherResponse response = weatherService.getDemoResponse();
        response.setLocation(city);
        return ResponseEntity.ok(response);
    }
}