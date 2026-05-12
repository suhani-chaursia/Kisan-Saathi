
package com.kisansathi.repository;

import com.kisansathi.entity.WeatherAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeatherAlertRepository extends JpaRepository<WeatherAlert, Long> {

    List<WeatherAlert> findByDistrict(String district);   // ← यही method error दे रहा था

    List<WeatherAlert> findByIsActiveTrue();
}