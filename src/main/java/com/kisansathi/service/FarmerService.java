package com.kisansathi.service;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kisansathi.entity.Farmer;
import com.kisansathi.repository.FarmerRepository;

@Service
public class FarmerService {

    @Autowired
    private FarmerRepository farmerRepository;

    public Farmer getFarmerByMobile(String mobile) {
        return farmerRepository.findByMobileNumber(mobile).orElse(null);
    }

  
    
    
    public Farmer saveOrUpdate(Farmer farmer) {
        if (farmer.getRegisteredAt() == null) {
            farmer.setRegisteredAt(LocalDateTime.now());
        }
        return farmerRepository.save(farmer);
    }
}