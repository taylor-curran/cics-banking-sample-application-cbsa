package com.cbsa.migration.service;

import com.cbsa.migration.dto.BankingMenuRequestDto;
import com.cbsa.migration.dto.BankingMenuResponseDto;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class BankingMenuService {

    public BankingMenuResponseDto getMenuOptions() {
        
        BankingMenuResponseDto response = new BankingMenuResponseDto(true, "Banking Menu Options");
        
        List<BankingMenuResponseDto.MenuOption> options = Arrays.asList(
            new BankingMenuResponseDto.MenuOption("1", "Display CUSTOMER details", "ODCS"),
            new BankingMenuResponseDto.MenuOption("2", "Display ACCOUNT details", "ODAC"),
            new BankingMenuResponseDto.MenuOption("3", "Create a CUSTOMER", "OCCS"),
            new BankingMenuResponseDto.MenuOption("4", "Create an account", "OCAC"),
            new BankingMenuResponseDto.MenuOption("5", "Update an account", "OUAC"),
            new BankingMenuResponseDto.MenuOption("6", "Credit/Debit funds to an account", "OCRA"),
            new BankingMenuResponseDto.MenuOption("7", "Transfer funds between accounts", "OTFN"),
            new BankingMenuResponseDto.MenuOption("A", "Look up accounts for a given customer", "OCCA")
        );
        
        response.setAvailableOptions(options);
        return response;
    }

    public BankingMenuResponseDto processMenuSelection(BankingMenuRequestDto request) {
        
        String option = request.getOption();
        if (option == null || option.trim().isEmpty()) {
            return new BankingMenuResponseDto(false, "Menu option is required");
        }

        option = option.trim().toUpperCase();

        if (!isValidOption(option)) {
            return new BankingMenuResponseDto(false, "You must enter a valid value (1-7 or A).");
        }

        BankingMenuResponseDto response = new BankingMenuResponseDto(true, "Menu option selected successfully");
        response.setSelectedOption(option);

        switch (option) {
            case "1":
                response.setTransactionId("ODCS");
                response.setDescription("Display CUSTOMER details");
                break;
            case "2":
                response.setTransactionId("ODAC");
                response.setDescription("Display ACCOUNT details");
                break;
            case "3":
                response.setTransactionId("OCCS");
                response.setDescription("Create a CUSTOMER");
                break;
            case "4":
                response.setTransactionId("OCAC");
                response.setDescription("Create an account");
                break;
            case "5":
                response.setTransactionId("OUAC");
                response.setDescription("Update an account");
                break;
            case "6":
                response.setTransactionId("OCRA");
                response.setDescription("Credit/Debit funds to an account");
                break;
            case "7":
                response.setTransactionId("OTFN");
                response.setDescription("Transfer funds between accounts");
                break;
            case "A":
                response.setTransactionId("OCCA");
                response.setDescription("Look up accounts for a given customer");
                break;
        }

        return response;
    }

    public BankingMenuResponseDto getMenuOptionDetails(String option) {
        
        if (option == null || option.trim().isEmpty()) {
            return new BankingMenuResponseDto(false, "Menu option is required");
        }

        option = option.trim().toUpperCase();

        if (!isValidOption(option)) {
            return new BankingMenuResponseDto(false, "Invalid menu option");
        }

        BankingMenuRequestDto request = new BankingMenuRequestDto(option);
        return processMenuSelection(request);
    }

    private boolean isValidOption(String option) {
        return "1".equals(option) || "2".equals(option) || "3".equals(option) || 
               "4".equals(option) || "5".equals(option) || "6".equals(option) || 
               "7".equals(option) || "A".equals(option);
    }
}
