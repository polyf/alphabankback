package com.alpha.bank.controller;

import com.alpha.bank.model.CompanyAccount;
import com.alpha.bank.service.CompanyAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/company-accounts")
public class CompanyAccountController {

    private final CompanyAccountService companyAccountService;

    @Autowired
    public CompanyAccountController(CompanyAccountService companyAccountService) {
        this.companyAccountService = companyAccountService;
    }

    @GetMapping
    public List<CompanyAccount> getAllEntities() {
        return companyAccountService.getAllCompanyAccounts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyAccount> getCompanyAccountById(@PathVariable Long id) {
        CompanyAccount companyAccount = companyAccountService.getCompanyAccountById(id);
        if (companyAccount != null) {
            return ResponseEntity.ok(companyAccount);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public CompanyAccount createCompanyAccount(@RequestBody CompanyAccount companyAccount) {
        return companyAccountService.saveCompanyAccount(companyAccount);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyAccount> updateCompanyAccount(@PathVariable Long id, @RequestBody CompanyAccount updatedCompanyAccount) {
        CompanyAccount entity = companyAccountService.updateCompanyAccount(id, updatedCompanyAccount);
        if (entity != null) {
            return ResponseEntity.ok(entity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompanyAccount(@PathVariable Long id) {
        if (companyAccountService.deleteCompanyAccount(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/status-check")
    public ResponseEntity<String> checkStatus(@RequestParam String cnpj) {
        try {
            companyAccountService.getAccountStatus(cnpj);
            return ResponseEntity.ok("Status checked and email sent successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error checking status and sending email.");
        }
    }
}