package com.cbsa.migration.controller;

import com.cbsa.migration.dto.CreditCardServicesRequestDto;
import com.cbsa.migration.dto.CreditCardServicesResponseDto;
import com.cbsa.migration.service.CreditCardServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/credit-card-services")
public class CreditCardServicesController {

    @Autowired
    private CreditCardServicesService creditCardServicesService;

    @PostMapping("/create-customer")
    public ResponseEntity<CreditCardServicesResponseDto> createCustomerForCreditCard(
            @Valid @RequestBody CreditCardServicesRequestDto request) {
        
        CreditCardServicesResponseDto response = creditCardServicesService.createCustomerForCreditCard(request);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Credit Card Services is running");
    }
}
