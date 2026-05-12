package com.kisansathi.service;

import com.kisansathi.dto.CurrentWeather;
import com.kisansathi.dto.DailyForecast;
import com.kisansathi.entity.Alert;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FarmingRuleEngine {

    /**
     * Crop-specific farming intelligence rules
     */
    public List<Alert> generateAlerts(CurrentWeather weather, String crop) {
        List<Alert> alerts = new ArrayList<>();

        // Heat Stress Alert
        if (weather.getTemperature() >= 35) {
            alerts.add(Alert.builder()
                    .title("भीषण गर्मी का खतरा")
                    .titleHindi("भीषण गर्मी का खतरा")
                    .message("तापमान बहुत अधिक है। पौधों पर तनाव हो सकता है।")
                    .messageHindi("तापमान बहुत अधिक है। पौधों पर तनाव हो सकता है।")
                    .action("सुबह या शाम सिंचाई करें। दोपहर में खेत में न जाएं।")
                    .actionHindi("सुबह या शाम सिंचाई करें। दोपहर में खेत में न जाएं।")
                    .severity("HIGH")
                    .icon("🔥")
                    .color("danger")
                    .build());
        }

        // High Humidity + Fungal Risk (Tomato, Potato, etc.)
        if (weather.getHumidity() > 75 && (crop.equalsIgnoreCase("टमाटर") || crop.equalsIgnoreCase("आलू"))) {
            alerts.add(Alert.builder()
                    .title("फफूंद रोग का खतरा")
                    .titleHindi("फफूंद रोग का खतरा")
                    .message("नमी अधिक होने से फंगस का खतरा बढ़ गया है।")
                    .messageHindi("नमी अधिक होने से फंगस का खतरा बढ़ गया है।")
                    .action("नीम का छिड़काव करें। पत्तियों को सूखा रखें।")
                    .actionHindi("नीम का छिड़काव करें। पत्तियों को सूखा रखें।")
                    .severity("MEDIUM")
                    .icon("🍄")
                    .color("warning")
                    .build());
        }

        // Heavy Rain Alert
        if (weather.getRainProbability() > 60) {
            alerts.add(Alert.builder()
                    .title("भारी वर्षा की संभावना")
                    .titleHindi("भारी वर्षा की संभावना")
                    .message("अगले 24-48 घंटे में तेज बारिश हो सकती है।")
                    .messageHindi("अगले 24-48 घंटे में तेज बारिश हो सकती है।")
                    .action("नालियों की सफाई करें। अतिरिक्त पानी निकासी का प्रबंध करें।")
                    .actionHindi("नालियों की सफाई करें। अतिरिक्त पानी निकासी का प्रबंध करें।")
                    .severity("MEDIUM")
                    .icon("🌧️")
                    .color("info")
                    .build());
        }

        // High Wind Alert
        if (weather.getWindSpeed() > 25) {
            alerts.add(Alert.builder()
                    .title("तेज हवाएं")
                    .titleHindi("तेज हवाएं")
                    .message("तेज हवा फसलों को नुकसान पहुंचा सकती है।")
                    .messageHindi("तेज हवा फसलों को नुकसान पहुंचा सकती है।")
                    .action("फसलों को बांस के सहारे दें।")
                    .actionHindi("फसलों को बांस के सहारे दें।")
                    .severity("LOW")
                    .icon("🌬️")
                    .color("success")
                    .build());
        }

        // Default safe alert
        if (alerts.isEmpty()) {
            alerts.add(Alert.builder()
                    .title("फसल सुरक्षित")
                    .titleHindi("फसल सुरक्षित")
                    .message("मौसम फसल के लिए अनुकूल है।")
                    .messageHindi("मौसम फसल के लिए अनुकूल है।")
                    .action("नियमित निगरानी जारी रखें।")
                    .actionHindi("नियमित निगरानी जारी रखें।")
                    .severity("LOW")
                    .icon("✅")
                    .color("success")
                    .build());
        }

        return alerts;
    }

    /**
     * Generate Hindi Voice Message for SpeechSynthesis
     */
    public String generateVoiceMessage(CurrentWeather current, List<Alert> alerts, String crop) {
        StringBuilder voice = new StringBuilder();

        voice.append("नमस्ते किसान भाई। ");
        voice.append("आज ").append(current.getLocation() != null ? current.getLocation() : "आपके क्षेत्र").append(" में तापमान ");
        voice.append(current.getTemperature()).append(" डिग्री सेल्सियस है। ");

        if (!alerts.isEmpty() && !alerts.get(0).getTitleHindi().contains("सुरक्षित")) {
            voice.append(alerts.get(0).getMessageHindi()).append(" ");
            voice.append(alerts.get(0).getActionHindi());
        } else {
            voice.append(crop).append(" की फसल के लिए मौसम अच्छा है। नियमित देखभाल जारी रखें।");
        }

        return voice.toString();
    }

    /**
     * Add farming advice to forecast
     */
    public void enrichForecastWithAdvice(List<DailyForecast> forecasts, String crop) {
        for (DailyForecast day : forecasts) {
            if (day.getRainMm() > 15) {
                day.setFarmingAdviceHindi("भारी बारिश होने वाली है। सिंचाई बंद रखें।");
            } else if (day.getMaxTemp() > 35) {
                day.setFarmingAdviceHindi("गर्मी से बचाव करें। ज्यादा पानी दें।");
            } else {
                day.setFarmingAdviceHindi("फसल की सामान्य देखभाल करें।");
            }
        }
    }
}