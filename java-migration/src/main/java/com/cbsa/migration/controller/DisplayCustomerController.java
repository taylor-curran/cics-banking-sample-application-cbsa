package com.cbsa.migration.controller;

import com.cbsa.migration.dto.DisplayCustomerRequestDto;
import com.cbsa.migration.dto.DisplayCustomerResponseDto;
import com.cbsa.migration.service.DisplayCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/display-customer")
public class DisplayCustomerController {

    @Autowired
    private DisplayCustomerService displayCustomerService;

    @PostMapping("/display")
    public ResponseEntity<DisplayCustomerResponseDto> displayCustomer(
            @Valid @RequestBody DisplayCustomerRequestDto request) {
        
        DisplayCustomerResponseDto response = displayCustomerService.displayCustomer(request);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<DisplayCustomerResponseDto> deleteCustomer(
            @Valid @RequestBody DisplayCustomerRequestDto request) {
        
        DisplayCustomerResponseDto response = displayCustomerService.deleteCustomer(request);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<DisplayCustomerResponseDto> updateCustomer(
            @Valid @RequestBody DisplayCustomerRequestDto request) {
        
        DisplayCustomerResponseDto response = displayCustomerService.updateCustomer(request);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/enable-update")
    public ResponseEntity<DisplayCustomerResponseDto> enableUpdateMode(
            @Valid @RequestBody DisplayCustomerRequestDto request) {
        
        DisplayCustomerResponseDto response = displayCustomerService.enableUpdateMode(request);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Display Customer Service is running");
    }
}
