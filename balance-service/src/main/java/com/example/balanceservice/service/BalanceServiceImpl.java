package com.example.balanceservice.service;

import com.example.balanceservice.model.Balance;
import com.example.balanceservice.repository.BalanceRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class BalanceServiceImpl implements BalanceService{

    private final BalanceRepository balanceRepository;

    @Override
    public Optional<Balance> create(String username) {
        log.info("BALANCE_SERVICE: {}",username);
        Balance balance = Balance.builder().balanceUsername(username).number("MS568437756").build();
        return Optional.of(balanceRepository.save(balance));
    }

    @Override
    public Optional<Balance> findById(Long id) {
        return balanceRepository.findById(id);
    }

    @Override
    public Optional<Balance> findByBalanceUsername(String name) {
        return balanceRepository.findFirstByBalanceUsername(name);
    }

    @Override
    public boolean existById(Long id) {
        return balanceRepository.existsById(id);
    }

    @Override
    public boolean existByBalanceUsername(String name) {
        return balanceRepository.existsBalanceByBalanceUsername(name);
    }

    @Override
    public void deleteById(Long id) {
        balanceRepository.deleteById(id);
    }

    @Override
    public Iterable<Balance> findAll() {
        return balanceRepository.findAll();
    }
}
