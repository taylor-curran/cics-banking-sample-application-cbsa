package com.cbsa.migration.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Min;
import javax.validation.constraints.Max;

public class CreditCardServicesRequestDto {
    
    @NotBlank(message = "Customer title is required")
    private String customerTitle;
    
    @NotBlank(message = "First name is required")
    private String firstName;
    
    private String middleName;
    
    @NotBlank(message = "Last name is required")
    private String lastName;
    
    @NotBlank(message = "Address line 1 is required")
    private String addressLine1;
    
    private String addressLine2;
    
    private String addressLine3;
    
    @NotNull(message = "Birth day is required")
    @Min(value = 1, message = "Birth day must be between 1 and 31")
    @Max(value = 31, message = "Birth day must be between 1 and 31")
    private Integer birthDay;
    
    @NotNull(message = "Birth month is required")
    @Min(value = 1, message = "Birth month must be between 1 and 12")
    @Max(value = 12, message = "Birth month must be between 1 and 12")
    private Integer birthMonth;
    
    @NotNull(message = "Birth year is required")
    @Min(value = 1900, message = "Birth year must be valid")
    @Max(value = 2100, message = "Birth year must be valid")
    private Integer birthYear;

    public CreditCardServicesRequestDto() {}

    public CreditCardServicesRequestDto(String customerTitle, String firstName, String middleName, 
                                      String lastName, String addressLine1, String addressLine2, 
                                      String addressLine3, Integer birthDay, Integer birthMonth, 
                                      Integer birthYear) {
        this.customerTitle = customerTitle;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.addressLine3 = addressLine3;
        this.birthDay = birthDay;
        this.birthMonth = birthMonth;
        this.birthYear = birthYear;
    }

    public String getCustomerTitle() {
        return customerTitle;
    }

    public void setCustomerTitle(String customerTitle) {
        this.customerTitle = customerTitle;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    public Integer getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Integer birthDay) {
        this.birthDay = birthDay;
    }

    public Integer getBirthMonth() {
        return birthMonth;
    }

    public void setBirthMonth(Integer birthMonth) {
        this.birthMonth = birthMonth;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }
}
