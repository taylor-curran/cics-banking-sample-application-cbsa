package com.cbsa.migration.controller;

import com.cbsa.migration.dto.DisplayAccountRequestDto;
import com.cbsa.migration.dto.DisplayAccountResponseDto;
import com.cbsa.migration.service.DisplayAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/display-account")
public class DisplayAccountController {

    @Autowired
    private DisplayAccountService displayAccountService;

    @PostMapping("/display")
    public ResponseEntity<DisplayAccountResponseDto> displayAccount(
            @Valid @RequestBody DisplayAccountRequestDto request) {
        
        DisplayAccountResponseDto response = displayAccountService.displayAccount(request);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<DisplayAccountResponseDto> deleteAccount(
            @Valid @RequestBody DisplayAccountRequestDto request) {
        
        DisplayAccountResponseDto response = displayAccountService.deleteAccount(request);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Display Account Service is running");
    }
}
