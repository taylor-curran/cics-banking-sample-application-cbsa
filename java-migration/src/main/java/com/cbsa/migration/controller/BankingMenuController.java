package com.cbsa.migration.controller;

import com.cbsa.migration.dto.BankingMenuRequestDto;
import com.cbsa.migration.dto.BankingMenuResponseDto;
import com.cbsa.migration.service.BankingMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/banking-menu")
public class BankingMenuController {

    @Autowired
    private BankingMenuService bankingMenuService;

    @GetMapping("/options")
    public ResponseEntity<BankingMenuResponseDto> getMenuOptions() {
        
        BankingMenuResponseDto response = bankingMenuService.getMenuOptions();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/select")
    public ResponseEntity<BankingMenuResponseDto> selectMenuOption(
            @Valid @RequestBody BankingMenuRequestDto request) {
        
        BankingMenuResponseDto response = bankingMenuService.processMenuSelection(request);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/option/{option}")
    public ResponseEntity<BankingMenuResponseDto> getMenuOptionDetails(
            @PathVariable String option) {
        
        BankingMenuResponseDto response = bankingMenuService.getMenuOptionDetails(option);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Banking Menu Service is running");
    }
}
