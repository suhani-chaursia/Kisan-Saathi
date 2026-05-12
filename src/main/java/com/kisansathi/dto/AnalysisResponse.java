package com.kisansathi.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalysisResponse {

    private String disease_name_hindi;
    private String disease_name_english;
    private int confidence;
    private String severity;
    private String severity_color;
    private boolean is_healthy;                    // ← Yeh field zaroori hai
    private String ai_description_hindi;
    private List<String> treatment;
    private List<String> prevention;
    private Technical technical;
    private String urgency;





    public boolean isIs_healthy() { return is_healthy; }
    public void setIs_healthy(boolean is_healthy) { this.is_healthy = is_healthy; }

    @Data
    public static class Technical {
        private String texture_analysis;
        private String color_pattern;
        private String morphology;
        private int affected_area_percent;
        private String analysis_method;
    }
}