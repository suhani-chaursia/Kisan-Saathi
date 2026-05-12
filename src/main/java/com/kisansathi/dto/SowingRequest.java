// src/main/java/com/kisansathi/dto/SowingRequest.java
package com.kisansathi.dto;

import lombok.Data;

@Data
public class SowingRequest {
    private String crop;
    private String location;
    private String soilType;
    private double landAcres;
}