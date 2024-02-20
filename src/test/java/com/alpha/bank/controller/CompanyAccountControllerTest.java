package com.alpha.bank.controller;

import com.alpha.bank.model.CompanyAccount;
import com.alpha.bank.service.CompanyAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class CompanyAccountControllerTest {

    @Mock
    private CompanyAccountService companyAccountService;

    @InjectMocks
    private CompanyAccountController companyAccountController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllEntities() {
        // Arrange
        CompanyAccount account1 = new CompanyAccount();
        CompanyAccount account2 = new CompanyAccount();
        when(companyAccountService.getAllCompanyAccounts()).thenReturn(Arrays.asList(account1, account2));

        // Act
        List<CompanyAccount> result = companyAccountController.getAllEntities();

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    void getCompanyAccountById() {
        // Arrange
        Long accountId = 1L;
        CompanyAccount account = new CompanyAccount();
        when(companyAccountService.getCompanyAccountById(accountId)).thenReturn(account);

        // Act
        ResponseEntity<CompanyAccount> result = companyAccountController.getCompanyAccountById(accountId);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
    }

    @Test
    void getCompanyAccountById_NotFound() {
        // Arrange
        Long accountId = 1L;
        when(companyAccountService.getCompanyAccountById(accountId)).thenReturn(null);

        // Act
        ResponseEntity<CompanyAccount> result = companyAccountController.getCompanyAccountById(accountId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void createEntity() {
        // Arrange
        CompanyAccount account = new CompanyAccount();
        when(companyAccountService.saveCompanyAccount(account)).thenReturn(account);

        // Act
        CompanyAccount result = companyAccountController.createCompanyAccount(account);

        // Assert
        assertNotNull(result);
    }

    @Test
    void updateCompanyAccount() {
        // Arrange
        Long accountId = 1L;
        CompanyAccount updatedAccount = new CompanyAccount();
        when(companyAccountService.updateCompanyAccount(accountId, updatedAccount)).thenReturn(updatedAccount);

        // Act
        ResponseEntity<CompanyAccount> result = companyAccountController.updateCompanyAccount(accountId, updatedAccount);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
    }

    @Test
    void updateCompanyAccount_NotFound() {
        // Arrange
        Long accountId = 1L;
        CompanyAccount updatedAccount = new CompanyAccount();
        when(companyAccountService.updateCompanyAccount(accountId, updatedAccount)).thenReturn(null);

        // Act
        ResponseEntity<CompanyAccount> result = companyAccountController.updateCompanyAccount(accountId, updatedAccount);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void deleteCompanyAccount() {
        // Arrange
        Long accountId = 1L;
        when(companyAccountService.deleteCompanyAccount(accountId)).thenReturn(true);

        // Act
        ResponseEntity<Void> result = companyAccountController.deleteCompanyAccount(accountId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    @Test
    void deleteCompanyAccount_NotFound() {
        // Arrange
        Long accountId = 1L;
        when(companyAccountService.deleteCompanyAccount(accountId)).thenReturn(false);

        // Act
        ResponseEntity<Void> result = companyAccountController.deleteCompanyAccount(accountId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }
}