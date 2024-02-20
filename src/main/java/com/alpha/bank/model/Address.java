package com.alpha.bank.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Embeddable
public class Address {

    @NotBlank(message = "Street address is required")
    @Size(max = 255, message = "Street address must be less than or equal to 255 characters")
    @Column(name = "street_address", length = 255)
    private String street;

    @NotBlank(message = "Number is required")
    @Size(max = 10, message = "Number must be less than or equal to 10 characters")
    @Column(name = "number", length = 10)
    private String number;

    @Size(max = 255, message = "Complement must be less than or equal to 255 characters")
    @Column(name = "complement", length = 255)
    private String complement;

    @NotBlank(message = "Neighborhood is required")
    @Size(max = 100, message = "Neighborhood must be less than or equal to 100 characters")
    @Column(name = "neighborhood", length = 100)
    private String neighborhood;

    @NotBlank(message = "City is required")
    @Size(max = 100, message = "City must be less than or equal to 100 characters")
    @Column(name = "city", length = 100)
    private String city;

    @NotBlank(message = "State is required")
    @Size(max = 100, message = "State must be less than or equal to 100 characters")
    @Column(name = "state", length = 100)
    private String state;

    @NotBlank(message = "ZIP code is required")
    @Size(max = 10, message = "ZIP code must be less than or equal to 10 characters")
    @Column(name = "cep", length = 10)
    private String zipCode;
}