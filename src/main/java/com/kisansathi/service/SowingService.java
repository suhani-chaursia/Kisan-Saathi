// SowingService.java
package com.kisansathi.service;

import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kisansathi.dto.SowingRequest;
import com.kisansathi.dto.SowingResponse;
import com.kisansathi.repository.CropSowingRepository;

@Service
public class SowingService {

    @Autowired
    private CropSowingRepository repository;

    @Autowired
    private WeatherService weatherService;

    public SowingResponse getRecommendation(SowingRequest request) {
        SowingResponse res = new SowingResponse();
        boolean isOffline = false;

        JSONObject weatherData = null;
        double temp = 26.0;
        double soilTemp = 25.5;
        double rain = 0.0;

        try {
            weatherData = weatherService.getWeather(request.getLocation());
            if (weatherData != null) {
                temp = weatherData.getJSONObject("current").getDouble("temperature_2m");
                soilTemp = weatherData.getJSONObject("current").getDouble("soil_temperature_0cm");
                rain = weatherData.getJSONObject("current").optDouble("precipitation", 0.0);
            }
        } catch (Exception e) {
            isOffline = true;
        }

        // Smart Logic
        int riskScore = calculateRiskScore(request.getCrop(), temp, soilTemp, rain, request.getLandAcres());

        // Risk Level
        if (riskScore <= 35) {
            res.setRiskLevel("LOW");
            res.setRiskColor("GREEN");
        } else if (riskScore <= 65) {
            res.setRiskLevel("MEDIUM");
            res.setRiskColor("ORANGE");
        } else {
            res.setRiskLevel("HIGH");
            res.setRiskColor("RED");
        }

        // Core Data
        res.setBestSowingDate(getBestSowingDate(request.getCrop()));
        res.setSoilTemperature(String.format("%.1f°C", soilTemp) + " (मिट्टी तापमान)");
        res.setRainfallForecast(getRainfallText(rain, isOffline));
        res.setRecommendation(getRecommendationText(riskScore));
        res.setConfidence(isOffline ? 72 : (100 - riskScore));

        // === NEW SMART FIELDS ===
        res.setWeatherAlert(getWeatherAlert(temp, rain));
        res.setDecisionReason(getDecisionReason(request.getCrop(), soilTemp, rain, riskScore));
        res.setAiInsight(getAIInsight(request.getCrop(), isOffline));
        res.setFarmingAdvice(getFarmingAdvice(request.getLandAcres()));

        // Tips
        res.setTips(getDynamicTips(request.getCrop(), request.getLandAcres()));

        return res;
    }

    private int calculateRiskScore(String crop, double temp, double soilTemp, double rain, double acres) {
        int score = 0;
        if (temp < 15 || temp > 35) score += 45;
        if (soilTemp < 18 || soilTemp > 32) score += 30;
        if (rain > 15) score += 35;
        if (acres > 8) score += 15;
        return Math.min(95, score);
    }

    private String getBestSowingDate(String crop) {
        return switch (crop.toLowerCase()) {
            case "wheat" -> "15 अक्टूबर - 10 नवंबर 2025";
            case "rice" -> "20 जून - 05 जुलाई 2025";
            case "soybean" -> "10 जून - 30 जून 2025";
            case "mustard" -> "15 अक्टूबर - 05 नवंबर 2025";
            default -> "05 जुलाई - 25 जुलाई 2025";
        };
    }

    private String getRainfallText(double rain, boolean offline) {
        if (offline) return "🌐 ऑफलाइन मोड • कैश्ड डेटा";
        return rain > 10 ? "⚠ भारी बारिश की संभावना" : "अगले 5 दिन हल्की/कोई बारिश नहीं";
    }

    private String getWeatherAlert(double temp, double rain) {
        if (temp > 38) return "⚠ अत्यधिक गर्मी - बोआई में देरी करें";
        if (rain > 15) return "⚠ भारी वर्षा की आशंका - जलभराव का खतरा";
        if (temp < 12) return "⚠ ठंडा मौसम - बीज अंकुरण धीमा हो सकता है";
        return "✅ मौसम बोआई के लिए अनुकूल है";
    }

    private String getDecisionReason(String crop, double soilTemp, double rain, int risk) {
        if (risk <= 35) 
            return "मिट्टी का तापमान और वर्तमान मौसम दोनों फसल " + crop + " के लिए आदर्श हैं";
        else if (rain > 12)
            return "भारी बारिश की संभावना के कारण जोखिम बढ़ गया है";
        else
            return "मौसम थोड़ा अनिश्चित है, सावधानी बरतें";
    }

    private String getAIInsight(String crop, boolean offline) {
        if (offline)
            return "AI ने आपके पिछले डेटा और सामान्य मौसम पैटर्न के आधार पर सलाह तैयार की है";
        return "AI ने Real-time मौसम + मिट्टी तापमान + फसल चक्र का विश्लेषण किया है";
    }

    private String getFarmingAdvice(double acres) {
        if (acres > 5)
            return "बड़े क्षेत्रफल में 2-3 हिस्सों में विभाजित करके बोआई करें";
        return "सामान्य घनत्व में बोआई करें। समय पर सिंचाई बहुत जरूरी है";
    }

    private List<String> getDynamicTips(String crop, double acres) {
        return Arrays.asList(
            "उन्नत बीज (HYV) का उपयोग करें",
            "मिट्टी परीक्षण करवाएं",
            acres > 5 ? "ट्रैक्टर या मशीन से बोआई करें" : "हाथ से सावधानी से बोएं",
            "पहली सिंचाई 18-25 दिन बाद करें"
        );
    }

    private String getRecommendationText(int riskScore) {
        if (riskScore <= 35) return "बोना शुरू कर सकते हैं ✅";
        if (riskScore <= 60) return "सावधानी के साथ बोआई करें ⚠";
        return "2-3 दिन इंतजार करें और मौसम देखें";
    }
}