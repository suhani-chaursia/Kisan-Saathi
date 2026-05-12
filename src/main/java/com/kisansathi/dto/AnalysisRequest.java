package com.kisansathi.dto;

import lombok.Data;

@Data
public class AnalysisRequest {
    private String image;      // base64
    private String mimeType;
}