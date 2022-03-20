package com.example.balanceservice.service;

import com.example.balanceservice.model.Balance;
import com.example.balanceservice.repository.BalanceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class BalanceServiceImpl implements BalanceService{

    private final BalanceRepository balanceRepository;

    @Override
    public Optional<Balance> create(Balance balance) {
        return Optional.of(balanceRepository.save(balance));
    }

    @Override
    public Optional<Balance> findById(Long id) {
        return balanceRepository.findById(id);
    }

    @Override
    public Optional<Balance> findByName(String name) {
        return balanceRepository.findFirstByBalanceName(name);
    }

    @Override
    public boolean existById(Long id) {
        return balanceRepository.existsById(id);
    }

    @Override
    public boolean existByName(String name) {
        return balanceRepository.existsBalanceByBalanceName(name);
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
