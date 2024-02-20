package com.alpha.bank.service;

import com.alpha.bank.exceptions.EmailSendingException;
import com.alpha.bank.exceptions.NotFoundException;
import com.alpha.bank.model.CompanyAccount;
import com.alpha.bank.repository.CompanyAccountRepository;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CompanyAccountService {

    private final CompanyAccountRepository companyAccountRepository;
    private final JavaMailSender javaMailSender;

    @Value("${mail.username}")
    private String username;

    @Autowired
    public CompanyAccountService(CompanyAccountRepository companyAccountRepository, JavaMailSender javaMailSender) {
        this.companyAccountRepository = companyAccountRepository;
        this.javaMailSender = javaMailSender;
    }

    public List<CompanyAccount> getAllCompanyAccounts() {
        return companyAccountRepository.findAll();
    }

    public CompanyAccount getCompanyAccountById(Long id) {
        return companyAccountRepository.findById(id).orElse(null);
    }

    public CompanyAccount saveCompanyAccount(CompanyAccount companyAccount) {
        Optional<CompanyAccount> existingCompany = companyAccountRepository.findByContactEmail(companyAccount.getContact().getEmail());
        if (existingCompany.isPresent()) {
            throw new DuplicateKeyException(String.format("Company with the email address '%s' already exists.", existingCompany.get().getContact().getEmail()));
        }
        return companyAccountRepository.save(companyAccount);
    }

    public CompanyAccount updateCompanyAccount(Long id, CompanyAccount updatedEntity) {
        if (companyAccountRepository.existsById(id)) {
            updatedEntity.setId(id);
            return companyAccountRepository.save(updatedEntity);
        } else {
            return null;
        }
    }

    public boolean deleteCompanyAccount(Long id) {
        if (companyAccountRepository.existsById(id)) {
            companyAccountRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public void getAccountStatus(String cnpj) {
        CompanyAccount companyAccount = companyAccountRepository.findByCnpj(cnpj)
                .orElseThrow(() -> new NotFoundException("Account status not found for CNPJ: " + cnpj));
        sendStatusEmail(companyAccount);
    }

    public void sendStatusEmail(CompanyAccount companyAccount) {
        String email = companyAccount.getContact().getEmail();
        String cnpj = companyAccount.getCnpj();
        String status = companyAccount.getStatus().getLabel();

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(username);
            helper.setTo(email);
            helper.setSubject("Account Status Update for CNPJ: " + cnpj);
            helper.setText("Your account status is: " + status);

            javaMailSender.send(message);
        } catch (Exception e) {
            log.error("Error sending email: {}", e.getMessage(), e);
            throw new EmailSendingException("Error sending status email to '" + email + "' for CNPJ: " + cnpj, e);
        }
    }
}