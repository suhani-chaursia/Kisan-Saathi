package com.kisansathi.service;

import com.kisansathi.dto.MandiPriceDto;
import com.kisansathi.entity.MandiPrice;
import com.kisansathi.repository.MandiPriceRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MandiService {

    private final MandiPriceRepository repository;

    public MandiService(MandiPriceRepository repository) {
        this.repository = repository;
    }

    @Cacheable(value = "mandiCache")
    public Map<String, Object> getCurrentMandiRates() {
        List<MandiPrice> prices = repository.findAll();

        if (prices.isEmpty()) {
            // Seed demo data
            seedDemoData();
            prices = repository.findAll();
        }

        Map<String, Object> response = new HashMap<>();
        for (MandiPrice p : prices) {
            response.put(p.getCrop(), MandiPriceDto.builder()
                    .crop(p.getCrop())
                    .price(p.getPricePerQuintal())
                    .changePercent(p.getChangePercent())
                    .trend(p.getTrend())
                    .build());
        }
        return response;
    }

    private void seedDemoData() {
        repository.saveAll(List.of(
            MandiPrice.builder().crop("गेहूं").pricePerQuintal(2420).changePercent(2.3).trend("UP").lastUpdated(LocalDateTime.now()).build(),
            MandiPrice.builder().crop("धान").pricePerQuintal(2180).changePercent(-1.2).trend("DOWN").lastUpdated(LocalDateTime.now()).build(),
            MandiPrice.builder().crop("सोयाबीन").pricePerQuintal(4250).changePercent(3.8).trend("UP").lastUpdated(LocalDateTime.now()).build(),
            MandiPrice.builder().crop("बाजरा").pricePerQuintal(1850).changePercent(0.5).trend("UP").lastUpdated(LocalDateTime.now()).build()
        ));
    }


    public Map<String, Object> getDemoMandiRates() {
        Map<String, Object> map = new HashMap<>();
        map.put("गेहूं", Map.of("price", 2420, "change", 2.3, "trend", "UP"));
        map.put("धान", Map.of("price", 2180, "change", -1.2, "trend", "DOWN"));
        map.put("सोयाबीन", Map.of("price", 4250, "change", 3.8, "trend", "UP"));
        map.put("बाजरा", Map.of("price", 1850, "change", 0.5, "trend", "UP"));
        return map;
    }
}