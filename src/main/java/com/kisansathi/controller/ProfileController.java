package com.kisansathi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kisansathi.entity.Farmer;
import com.kisansathi.service.FarmerService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class ProfileController {

    @Autowired
    private FarmerService farmerService;

    // Profile लाने के लिए
    @GetMapping("/profile/{mobile}")
    public ResponseEntity<Farmer> getProfile(@PathVariable String mobile) {
        Farmer farmer = farmerService.getFarmerByMobile(mobile);
        if (farmer == null) {
            farmer = new Farmer();
            farmer.setMobileNumber(mobile);
        }
        return ResponseEntity.ok(farmer);
    }

    // Profile Update करने के लिए
    @PostMapping("/profile")
    public ResponseEntity<Farmer> updateProfile(@RequestBody Farmer farmer) {
        Farmer updated = farmerService.saveOrUpdate(farmer);
        return ResponseEntity.ok(updated);
    }
}