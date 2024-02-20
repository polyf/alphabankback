package com.alpha.bank.model;

import com.alpha.bank.model.enums.CompanySizeEnum;
import com.alpha.bank.model.enums.StatusEnum;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "company_accounts")
public class CompanyAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Company Legal Name is required")
    @Column(name = "company_legal_name", nullable = false)
    private String companyLegalName;

    @NotBlank(message = "Business Name is required")
    @Column(name = "business_name", nullable = false)
    private String businessName;

    @Enumerated(EnumType.STRING)
    @Column(name = "company_size", nullable = false)
    private CompanySizeEnum companySize;

    @NotBlank(message = "CNPJ is required")
    @Size(min = 14, max = 18, message = "CNPJ must be 14 characters long")
    @Column(name = "cnpj", unique = true, nullable = false)
    private String cnpj;

    @Embedded
    @Valid
    private Address address;

    @Embedded
    @Valid
    private Contact contact;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusEnum status = StatusEnum.PENDING;
}