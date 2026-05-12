package com.kisansathi.controller;

import com.kisansathi.dto.AnalysisResponse;
import com.kisansathi.service.ClaudeVisionService;
import com.kisansathi.service.OfflineDiagnosisService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Base64;
import java.util.Map;

@RestController
@RequestMapping("/api/analysis")
@CrossOrigin(origins = "*")
public class CropAnalysisController {

    private final ClaudeVisionService claudeService;
    private final OfflineDiagnosisService offlineService;

    public CropAnalysisController(ClaudeVisionService claudeService, OfflineDiagnosisService offlineService) {
        this.claudeService = claudeService;
        this.offlineService = offlineService;
    }

    @PostMapping("/detect")
    public Mono<ResponseEntity<AnalysisResponse>> detectDisease(
            @RequestBody Map<String, String> payload) {
        
        String base64 = payload.get("image");
        String mimeType = payload.get("mimeType");

        if (base64 == null) {
            return Mono.just(ResponseEntity.badRequest().build());
        }

        // Remove data URL prefix if present
        if (base64.startsWith("data:")) {
            base64 = base64.split(",")[1];
        }

        return claudeService.analyzeImage(base64, mimeType)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> 
                    Mono.just(ResponseEntity.ok(offlineService.getFallbackDiagnosis())));
    }
}