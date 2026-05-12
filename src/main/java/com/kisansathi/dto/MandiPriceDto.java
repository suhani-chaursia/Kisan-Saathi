package com.kisansathi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MandiPriceDto {
    private String crop;
    private int price;
    private double changePercent;
    private String trend; // UP / DOWN
}