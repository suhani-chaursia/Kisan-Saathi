package com.kisansathi.service;

import com.kisansathi.dto.AnalysisResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class OfflineDiagnosisService {

    private final List<AnalysisResponse> fallbackDiagnoses = List.of(
            createHealthyResponse(),
            createLeafBlightResponse(),
            createPowderyMildewResponse(),
            createRustResponse()
    );

    public AnalysisResponse getFallbackDiagnosis() {
        return fallbackDiagnoses.get(new Random().nextInt(fallbackDiagnoses.size()));
    }

    // Healthy Crop
    private AnalysisResponse createHealthyResponse() {
        AnalysisResponse r = new AnalysisResponse();
        r.setDisease_name_hindi("स्वस्थ फसल");
        r.setDisease_name_english("Healthy Crop");
        r.setConfidence(94);
        r.setSeverity("स्वस्थ");
        r.setSeverity_color("success");
        r.setIs_healthy(true);
        r.setAi_description_hindi("आपकी फसल पूरी तरह स्वस्थ दिख रही है।");
        r.setTreatment(List.of("कोई दवा जरूरी नहीं", "नियमित देखभाल जारी रखें"));
        r.setPrevention(List.of("समय-समय पर निरीक्षण करें"));
        r.setUrgency("नियमित देखभाल करें");

        AnalysisResponse.Technical t = new AnalysisResponse.Technical();
        t.setTexture_analysis("स्वस्थ");
        t.setColor_pattern("प्राकृतिक हरा");
        t.setAffected_area_percent(0);
        r.setTechnical(t);
        return r;
    }

    // Leaf Blight
    private AnalysisResponse createLeafBlightResponse() {
        AnalysisResponse r = new AnalysisResponse();
        r.setDisease_name_hindi("लीफ ब्लाइट");
        r.setDisease_name_english("Leaf Blight");
        r.setConfidence(87);
        r.setSeverity("मध्यम");
        r.setSeverity_color("warning");
        r.setIs_healthy(false);
        r.setAi_description_hindi("पत्ती पर भूरे-पीले धब्बे दिख रहे हैं।");
        r.setTreatment(List.of("नीम तेल स्प्रे", "ट्राइसाइक्लाजोल फंगीसाइड"));
        r.setPrevention(List.of("हवा आने दें"));
        r.setUrgency("इस सप्ताह में उपचार करें");

        AnalysisResponse.Technical t = new AnalysisResponse.Technical();
        t.setAffected_area_percent(32);
        r.setTechnical(t);
        return r;
    }

    // Powdery Mildew
    private AnalysisResponse createPowderyMildewResponse() {
        AnalysisResponse r = new AnalysisResponse();
        r.setDisease_name_hindi("पाउडरी मिल्ड्यू");
        r.setDisease_name_english("Powdery Mildew");
        r.setConfidence(91);
        r.setSeverity("उच्च");
        r.setSeverity_color("danger");
        r.setIs_healthy(false);
        r.setAi_description_hindi("पत्ती पर सफेद पाउडर दिख रहा है।");
        r.setTreatment(List.of("सल्फर फंगीसाइड", "पोटेशियम बायकार्बोनेट"));
        r.setPrevention(List.of("घना न लगाएं"));
        r.setUrgency("⚠ तुरंत उपचार करें");

        AnalysisResponse.Technical t = new AnalysisResponse.Technical();
        t.setAffected_area_percent(48);
        r.setTechnical(t);
        return r;
    }

    // Rust
    private AnalysisResponse createRustResponse() {
        AnalysisResponse r = new AnalysisResponse();
        r.setDisease_name_hindi("रस्ट रोग");
        r.setDisease_name_english("Rust");
        r.setConfidence(83);
        r.setSeverity("मध्यम");
        r.setSeverity_color("warning");
        r.setIs_healthy(false);
        r.setAi_description_hindi("पत्ती पर नारंगी-भूरे धब्बे हैं।");
        r.setTreatment(List.of("हैक्साकोनाजोल स्प्रे"));
        r.setPrevention(List.of("सफाई रखें"));
        r.setUrgency("इस सप्ताह में उपचार करें");

        AnalysisResponse.Technical t = new AnalysisResponse.Technical();
        t.setAffected_area_percent(30);
        r.setTechnical(t);
        return r;
    }
}