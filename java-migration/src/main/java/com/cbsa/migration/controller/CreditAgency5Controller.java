package com.cbsa.migration.controller;

import com.cbsa.migration.dto.CreditAgencyRequestDto;
import com.cbsa.migration.dto.CreditAgencyResponseDto;
import com.cbsa.migration.service.CreditAgency5Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/credit-agency-5")
public class CreditAgency5Controller {

    @Autowired
    private CreditAgency5Service creditAgency5Service;

    @PostMapping("/score")
    public ResponseEntity<CreditAgencyResponseDto> processCreditScoring(
            @Valid @RequestBody CreditAgencyRequestDto request) {
        
        CreditAgencyResponseDto response = creditAgency5Service.processCreditScoring(request);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/info")
    public ResponseEntity<CreditAgencyResponseDto> getAgencyInfo() {
        
        CreditAgencyResponseDto response = creditAgency5Service.getAgencyInfo();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Credit Agency 5 Service is running");
    }
}
