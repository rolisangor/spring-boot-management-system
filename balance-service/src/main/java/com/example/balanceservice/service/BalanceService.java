package com.example.balanceservice.service;

import com.example.balanceservice.model.Balance;

import java.util.Optional;

public interface BalanceService {

    Optional<Balance> create(Balance balance);
    Optional<Balance> findById(Long id);
    Optional<Balance> findByName(String name);
    boolean existById(Long id);
    boolean existByName(String name);
    void deleteById(Long id);
    Iterable<Balance> findAll();
}
