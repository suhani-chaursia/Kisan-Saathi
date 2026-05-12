// src/main/java/com/kisansathi/controller/SowingController.java
package com.kisansathi.controller;

import com.kisansathi.dto.SowingRequest;
import com.kisansathi.dto.SowingResponse;
import com.kisansathi.service.SowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sowing")
@CrossOrigin(origins = "*")   // Frontend से connect करने के लिए
public class SowingController {

    @Autowired
    private SowingService sowingService;

    @PostMapping("/recommend")
    public ResponseEntity<SowingResponse> getSowingRecommendation(@RequestBody SowingRequest request) {
        SowingResponse response = sowingService.getRecommendation(request);
        return ResponseEntity.ok(response);
    }
}