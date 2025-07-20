package com.cbsa.migration.controller;

import com.cbsa.migration.dto.CreditDebitRequestDto;
import com.cbsa.migration.dto.CreditDebitResponseDto;
import com.cbsa.migration.service.CreditDebitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/credit-debit")
public class CreditDebitController {

    @Autowired
    private CreditDebitService creditDebitService;

    @PostMapping("/process")
    public ResponseEntity<CreditDebitResponseDto> processCreditDebit(
            @Valid @RequestBody CreditDebitRequestDto request) {
        
        CreditDebitResponseDto response = creditDebitService.processCreditDebit(request);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Credit Debit Service is running");
    }
}
