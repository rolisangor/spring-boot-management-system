package com.managementsystem.accountservice.service;

import com.managementsystem.accountservice.model.Account;

import java.util.Optional;

public interface AccountService {

    Optional<Account> create(Account account); //TODO: change to AccountDTO
    Optional<Account> findById(Long id);
    boolean existById(Long id);
    void deleteById(Long id);
}
