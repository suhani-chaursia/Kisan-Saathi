package com.kisansathi.service;

import com.kisansathi.dto.ProfitRequest;
import com.kisansathi.dto.ProfitResponse;
import org.springframework.stereotype.Service;

@Service
public class ProfitService {

    public ProfitResponse calculateSmartProfit(ProfitRequest request) {
        String crop = request.getCrop() != null ? request.getCrop() : "गेहूं";
        double acres = request.getAcres() > 0 ? request.getAcres() : 2.0;

        // Realistic Yield per Acre (Quintal)
        double yieldPerAcre = switch (crop) {
            case "गेहूं" -> 22;
            case "धान" -> 28;
            case "सोयाबीन" -> 14;
            case "बाजरा" -> 18;
            default -> 20;
        };

        int mandiPrice = getMandiPrice(crop);

        double totalYield = acres * yieldPerAcre;
        double revenue = totalYield * mandiPrice;

        // Cost Breakdown
        double seedCost = acres * 3500;
        double fertilizer = acres * 6800;
        double labour = acres * 5200;
        double irrigation = acres * 3000;
        double transport = acres * 1500;

        double totalCost = seedCost + fertilizer + labour + irrigation + transport;
        double netProfit = revenue - totalCost;

        int risk = calculateRisk(crop);

        String suggestion = generateAISuggestion(crop, netProfit, risk);

        return ProfitResponse.builder()
                .revenue(Math.round(revenue))
                .totalCost(Math.round(totalCost))
                .netProfit(Math.round(netProfit))
                .riskPercentage(risk)                    // Fixed: Correct method name
                .aiSuggestion(suggestion)
                .bestAction(netProfit > 50000 ? "फसल बढ़ाएं" : "सावधानी बरतें")
                .weatherImpact("मध्यम वर्षा से सिंचाई खर्च 15% कम")
                .build();
    }

    private int getMandiPrice(String crop) {
        return switch (crop) {
            case "गेहूं" -> 2420;
            case "धान" -> 2180;
            case "सोयाबीन" -> 4250;
            case "बाजरा" -> 1850;
            default -> 2400;
        };
    }

    private int calculateRisk(String crop) {
        return switch (crop) {
            case "गेहूं" -> 22;
            case "धान" -> 35;
            case "सोयाबीन" -> 18;
            default -> 28;
        };
    }

    private String generateAISuggestion(String crop, double profit, int risk) {
        if (profit > 80000) 
            return "बहुत अच्छा मुनाफा! " + crop + " इस सीजन में बेहतरीन है।";
        if (risk > 30) 
            return "जोखिम अधिक है। मौसम निगरानी रखें और फफूंद रोग से बचाव करें।";
        return "स्थिर मुनाफा। अगले सीजन में क्षेत्रफल 20% बढ़ाने की सलाह।";
    }
}