package com.kisansathi.service;

import com.kisansathi.dto.AnalysisResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
public class ClaudeVisionService {

    private final WebClient webClient;

    // Fixed Constructor - WebClient.Builder ko properly inject kar rahe hain
    public ClaudeVisionService(WebClient.Builder webClientBuilder,
                               @Value("${claude.api-key}") String apiKey,
                               @Value("${claude.base-url:}") String baseUrl) {
        
        this.webClient = webClientBuilder
                .baseUrl(baseUrl.isEmpty() ? "https://api.anthropic.com" : baseUrl)
                .defaultHeader("x-api-key", apiKey)
                .defaultHeader("anthropic-version", "2023-06-01")
                .build();
    }

    public Mono<AnalysisResponse> analyzeImage(String base64Image, String mimeType) {
        
        String systemPrompt = """
            You are an expert agricultural plant pathologist for "Kisan Saathi" app for Indian farmers.
            Analyze the image and respond ONLY with valid JSON. No extra text.
            """;

        Map<String, Object> requestBody = Map.of(
                "model", "claude-3-5-sonnet-20240620",
                "max_tokens", 1200,
                "system", systemPrompt,
                "messages", List.of(Map.of(
                        "role", "user",
                        "content", List.of(
                                Map.of("type", "image",
                                        "source", Map.of(
                                                "type", "base64",
                                                "media_type", mimeType,
                                                "data", base64Image
                                        )),
                                Map.of("type", "text", "text", "Analyze this leaf image and return JSON only.")
                        )
                ))
        );

        return webClient.post()
                .uri("/v1/messages")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .map(this::parseClaudeResponse);
    }

    private AnalysisResponse parseClaudeResponse(String rawResponse) {
        // Temporary fallback response (real parsing baad mein kar sakte hain)
        AnalysisResponse response = new AnalysisResponse();
        response.setDisease_name_hindi("लीफ ब्लाइट");
        response.setDisease_name_english("Leaf Blight");
        response.setConfidence(85);
        response.setSeverity("मध्यम");
        response.setSeverity_color("warning");
        response.setIs_healthy(false);
        response.setAi_description_hindi("पत्ती पर भूरे धब्बे दिख रहे हैं जो फंगल संक्रमण के लक्षण हैं।");
        response.setTreatment(List.of("नीम का तेल स्प्रे करें", "रोगग्रस्त पत्तियाँ हटाएं"));
        response.setPrevention(List.of("फसल में हवा का आवागमन बनाए रखें"));
        response.setUrgency("इस सप्ताह में उपचार करें");

        AnalysisResponse.Technical tech = new AnalysisResponse.Technical();
        tech.setTexture_analysis("Brown irregular spots");
        tech.setColor_pattern("Yellow-brown patches");
        tech.setAffected_area_percent(35);
        tech.setAnalysis_method("Claude Vision Model");
        response.setTechnical(tech);

        return response;
    }
}