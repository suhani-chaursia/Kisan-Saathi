package com.kisansathi.dto;

import lombok.Data;

@Data
public class ProfitRequest {
    private String crop;
    private double acres;
    private String location;
}