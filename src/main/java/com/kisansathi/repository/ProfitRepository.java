package com.kisansathi.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.kisansathi.entity.ProfitHistory;

public interface ProfitRepository extends JpaRepository<ProfitHistory, Long> {
}