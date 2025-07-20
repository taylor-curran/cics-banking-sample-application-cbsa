package com.cbsa.migration.controller;

import com.cbsa.migration.dto.DebitCreditRequestDto;
import com.cbsa.migration.dto.DebitCreditResponseDto;
import com.cbsa.migration.service.DebitCreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/debit-credit")
public class DebitCreditController {

    @Autowired
    private DebitCreditService debitCreditService;

    @PostMapping("/process")
    public ResponseEntity<DebitCreditResponseDto> processDebitCredit(
            @Valid @RequestBody DebitCreditRequestDto request) {
        
        DebitCreditResponseDto response = debitCreditService.processDebitCredit(request);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Debit Credit Service is running");
    }
}
