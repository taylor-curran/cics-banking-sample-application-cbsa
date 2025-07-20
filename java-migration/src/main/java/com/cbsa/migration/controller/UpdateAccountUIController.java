package com.cbsa.migration.controller;

import com.cbsa.migration.dto.UpdateAccountUIRequestDto;
import com.cbsa.migration.dto.UpdateAccountUIResponseDto;
import com.cbsa.migration.service.UpdateAccountUIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/update-account-ui")
public class UpdateAccountUIController {

    @Autowired
    private UpdateAccountUIService updateAccountUIService;

    @PostMapping("/inquire")
    public ResponseEntity<UpdateAccountUIResponseDto> inquireAccount(
            @Valid @RequestBody UpdateAccountUIRequestDto request) {
        
        UpdateAccountUIResponseDto response = updateAccountUIService.inquireAccount(request);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<UpdateAccountUIResponseDto> updateAccount(
            @Valid @RequestBody UpdateAccountUIRequestDto request) {
        
        UpdateAccountUIResponseDto response = updateAccountUIService.updateAccount(request);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/enable-update")
    public ResponseEntity<UpdateAccountUIResponseDto> enableUpdateMode(
            @Valid @RequestBody UpdateAccountUIRequestDto request) {
        
        UpdateAccountUIResponseDto response = updateAccountUIService.enableUpdateMode(request);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Update Account UI Service is running");
    }
}
