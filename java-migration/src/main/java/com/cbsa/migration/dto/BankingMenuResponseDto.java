package com.cbsa.migration.dto;

import java.util.List;

public class BankingMenuResponseDto {
    
    private boolean success;
    private String message;
    private String selectedOption;
    private String transactionId;
    private String description;
    private List<MenuOption> availableOptions;

    public BankingMenuResponseDto() {}

    public BankingMenuResponseDto(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(String selectedOption) {
        this.selectedOption = selectedOption;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<MenuOption> getAvailableOptions() {
        return availableOptions;
    }

    public void setAvailableOptions(List<MenuOption> availableOptions) {
        this.availableOptions = availableOptions;
    }

    public static class MenuOption {
        private String option;
        private String description;
        private String transactionId;

        public MenuOption() {}

        public MenuOption(String option, String description, String transactionId) {
            this.option = option;
            this.description = description;
            this.transactionId = transactionId;
        }

        public String getOption() {
            return option;
        }

        public void setOption(String option) {
            this.option = option;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }
    }
}
