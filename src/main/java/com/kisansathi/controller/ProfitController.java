package com.kisansathi.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kisansathi.dto.ProfitRequest;
import com.kisansathi.dto.ProfitResponse;
import com.kisansathi.service.MandiService;
import com.kisansathi.service.ProfitService;

@RestController
@RequestMapping("/api")
public class ProfitController {

    private final MandiService mandiService;
    private final ProfitService profitService;

    public ProfitController(MandiService mandiService, ProfitService profitService) {
        this.mandiService = mandiService;
        this.profitService = profitService;
    }

    @GetMapping("/mandi")
    public ResponseEntity<Map<String, Object>> getMandiRates() {
        return ResponseEntity.ok(mandiService.getDemoMandiRates());
    }

    @PostMapping("/profit")
    public ResponseEntity<ProfitResponse> calculateProfit(@RequestBody ProfitRequest request) {
        try {
            ProfitResponse response = profitService.calculateSmartProfit(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}