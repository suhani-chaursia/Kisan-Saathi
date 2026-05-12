// CropSowingRepository.java
package com.kisansathi.repository;

import com.kisansathi.entity.CropSowingData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CropSowingRepository extends JpaRepository<CropSowingData, Long> {
    
    CropSowingData findByCropAndSoilType(String crop, String soilType);
}