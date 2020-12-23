package com.pull.law.controller;

import com.pull.law.config.ExConfig;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/api/v1")
public class HealthController {

    private final ExConfig config;

    @Autowired
    public HealthController(ExConfig config) {
        this.config = config;
    }

    @GetMapping("/health")
    public ResponseEntity<String> findAllStates() {
        try {
            log.info("Health is here");
            return ResponseEntity.ok().body(String.format("OK : '%s'", "aaa"));
        } catch (Exception e) {
            log.info("Error:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
