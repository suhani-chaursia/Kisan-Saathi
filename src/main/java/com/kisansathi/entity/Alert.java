package com.kisansathi.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Alert {

    private String title;
    private String titleHindi;
    private String message;
    private String messageHindi;
    private String action;
    private String actionHindi;
    private String severity;     // HIGH, MEDIUM, LOW
    private String icon;
    private String color;        // danger, warning, success
} 