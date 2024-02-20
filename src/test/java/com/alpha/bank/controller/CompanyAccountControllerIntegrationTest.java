package com.alpha.bank.controller;

import com.alpha.bank.model.CompanyAccount;
import com.alpha.bank.service.CompanyAccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
class CompanyAccountControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompanyAccountService companyAccountService;

    @Test
    void getAllEntities() throws Exception {
        // Arrange
        CompanyAccount account1 = new CompanyAccount();
        CompanyAccount account2 = new CompanyAccount();
        when(companyAccountService.getAllCompanyAccounts()).thenReturn(Arrays.asList(account1, account2));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/company-accounts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));
    }

    @Test
    void getEntityById() throws Exception {
        // Arrange
        Long accountId = 1L;
        CompanyAccount account = new CompanyAccount();
        account.setId(accountId);
        when(companyAccountService.getCompanyAccountById(accountId)).thenReturn(account);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/company-accounts/{id}", accountId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(accountId));
    }

    @Test
    void createEntity() throws Exception {
        // Arrange
        CompanyAccount account = new CompanyAccount();
        account.setId(1L);
        when(companyAccountService.saveCompanyAccount(any(CompanyAccount.class))).thenReturn(account);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/company-accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    void updateEntity() throws Exception {
        // Arrange
        Long accountId = 1L;
        CompanyAccount updatedAccount = new CompanyAccount();
        updatedAccount.setId(accountId);
        when(companyAccountService.updateCompanyAccount(eq(accountId), any(CompanyAccount.class))).thenReturn(updatedAccount);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/company-accounts/{id}", accountId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(accountId));
    }

    @Test
    void deleteEntity() throws Exception {
        // Arrange
        Long accountId = 1L;
        when(companyAccountService.deleteCompanyAccount(accountId)).thenReturn(true);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/company-accounts/{id}", accountId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}