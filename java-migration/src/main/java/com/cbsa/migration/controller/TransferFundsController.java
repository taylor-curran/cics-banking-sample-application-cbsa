package com.cbsa.migration.controller;

import com.cbsa.migration.dto.TransferFundsRequestDto;
import com.cbsa.migration.dto.TransferFundsResponseDto;
import com.cbsa.migration.service.TransferFundsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/transfer-funds")
public class TransferFundsController {

    @Autowired
    private TransferFundsService transferFundsService;

    @PostMapping("/transfer")
    public ResponseEntity<TransferFundsResponseDto> transferFunds(
            @Valid @RequestBody TransferFundsRequestDto request) {
        
        TransferFundsResponseDto response = transferFundsService.transferFunds(request);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Transfer Funds Service is running");
    }
}
