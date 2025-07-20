package com.cbsa.migration.dto;

import javax.validation.constraints.NotBlank;

public class BankingMenuRequestDto {
    
    @NotBlank(message = "Menu option is required")
    private String option;

    public BankingMenuRequestDto() {}

    public BankingMenuRequestDto(String option) {
        this.option = option;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
}
