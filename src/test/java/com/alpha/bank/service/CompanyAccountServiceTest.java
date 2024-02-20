package com.alpha.bank.service;

import com.alpha.bank.model.CompanyAccount;
import com.alpha.bank.repository.CompanyAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CompanyAccountServiceTest {

    @Mock
    private CompanyAccountRepository companyAccountRepository;

    @InjectMocks
    private CompanyAccountService companyAccountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCompanyAccounts() {
        // Arrange
        CompanyAccount account1 = new CompanyAccount();
        CompanyAccount account2 = new CompanyAccount();
        when(companyAccountRepository.findAll()).thenReturn(Arrays.asList(account1, account2));

        // Act
        List<CompanyAccount> result = companyAccountService.getAllCompanyAccounts();

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    void getCompanyAccountById() {
        // Arrange
        Long accountId = 1L;
        CompanyAccount account = new CompanyAccount();
        when(companyAccountRepository.findById(accountId)).thenReturn(Optional.of(account));

        // Act
        CompanyAccount result = companyAccountService.getCompanyAccountById(accountId);

        // Assert
        assertNotNull(result);
    }

    @Test
    void getCompanyAccountById_NotFound() {
        // Arrange
        Long accountId = 1L;
        when(companyAccountRepository.findById(accountId)).thenReturn(Optional.empty());

        // Act
        CompanyAccount result = companyAccountService.getCompanyAccountById(accountId);

        // Assert
        assertNull(result);
    }

    @Test
    void saveCompanyAccount() {
        // Arrange
        CompanyAccount account = new CompanyAccount();
        when(companyAccountRepository.save(account)).thenReturn(account);

        // Act
        CompanyAccount result = companyAccountService.saveCompanyAccount(account);

        // Assert
        assertNotNull(result);
    }

    @Test
    void updateCompanyAccount() {
        // Arrange
        Long accountId = 1L;
        CompanyAccount existingAccount = new CompanyAccount();
        CompanyAccount updatedAccount = new CompanyAccount();
        when(companyAccountRepository.existsById(accountId)).thenReturn(true);
        when(companyAccountRepository.save(updatedAccount)).thenReturn(updatedAccount);

        // Act
        CompanyAccount result = companyAccountService.updateCompanyAccount(accountId, updatedAccount);

        // Assert
        assertNotNull(result);
        assertEquals(accountId, result.getId());
    }

    @Test
    void updateCompanyAccount_NotFound() {
        // Arrange
        Long accountId = 1L;
        CompanyAccount updatedAccount = new CompanyAccount();
        when(companyAccountRepository.existsById(accountId)).thenReturn(false);

        // Act
        CompanyAccount result = companyAccountService.updateCompanyAccount(accountId, updatedAccount);

        // Assert
        assertNull(result);
    }

    @Test
    void deleteCompanyAccount() {
        // Arrange
        Long accountId = 1L;
        when(companyAccountRepository.existsById(accountId)).thenReturn(true);

        // Act
        boolean result = companyAccountService.deleteCompanyAccount(accountId);

        // Assert
        assertTrue(result);
        verify(companyAccountRepository, times(1)).deleteById(accountId);
    }

    @Test
    void deleteCompanyAccount_NotFound() {
        // Arrange
        Long accountId = 1L;
        when(companyAccountRepository.existsById(accountId)).thenReturn(false);

        // Act
        boolean result = companyAccountService.deleteCompanyAccount(accountId);

        // Assert
        assertFalse(result);
        verify(companyAccountRepository, never()).deleteById(accountId);
    }
}