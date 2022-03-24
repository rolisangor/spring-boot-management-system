package com.example.balanceservice.service;

import com.example.balanceservice.model.Balance;

import java.util.Optional;

public interface BalanceService {

    Optional<Balance> create(String username);
    Optional<Balance> findById(Long id);
    Optional<Balance> findByBalanceUsername(String name);
    boolean existById(Long id);
    boolean existByBalanceUsername(String name);
    void deleteById(Long id);
    Iterable<Balance> findAll();
}
