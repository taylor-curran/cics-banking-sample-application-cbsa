package com.cbsa.migration.controller;

import com.cbsa.migration.dto.TransferFundsUIRequestDto;
import com.cbsa.migration.dto.TransferFundsUIResponseDto;
import com.cbsa.migration.service.TransferFundsUIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/transfer-ui")
public class TransferFundsUIController {

    @Autowired
    private TransferFundsUIService transferFundsUIService;

    @PostMapping("/transfer")
    public ResponseEntity<TransferFundsUIResponseDto> processTransfer(
            @Valid @RequestBody TransferFundsUIRequestDto request) {
        
        TransferFundsUIResponseDto response = transferFundsUIService.processTransfer(request);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Transfer Funds UI Service is running");
    }
}
