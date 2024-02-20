package com.alpha.bank.repository;

import com.alpha.bank.model.CompanyAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyAccountRepository extends JpaRepository<CompanyAccount, Long> {
    Optional<CompanyAccount> findByContactEmail(String email);

    Optional<CompanyAccount> findByCnpj(String cnpj);
}