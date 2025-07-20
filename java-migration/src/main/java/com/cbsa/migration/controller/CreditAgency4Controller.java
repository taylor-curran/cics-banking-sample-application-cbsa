package com.cbsa.migration.controller;

import com.cbsa.migration.dto.CreditAgencyRequestDto;
import com.cbsa.migration.dto.CreditAgencyResponseDto;
import com.cbsa.migration.service.CreditAgency4Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/credit-agency-4")
public class CreditAgency4Controller {

    @Autowired
    private CreditAgency4Service creditAgency4Service;

    @PostMapping("/score")
    public ResponseEntity<CreditAgencyResponseDto> processCreditScoring(
            @Valid @RequestBody CreditAgencyRequestDto request) {
        
        CreditAgencyResponseDto response = creditAgency4Service.processCreditScoring(request);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/info")
    public ResponseEntity<CreditAgencyResponseDto> getAgencyInfo() {
        
        CreditAgencyResponseDto response = creditAgency4Service.getAgencyInfo();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Credit Agency 4 Service is running");
    }
}
