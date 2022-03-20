package com.example.balanceservice.application;

import com.example.balanceservice.model.Balance;
import com.example.balanceservice.service.BalanceService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class Init implements CommandLineRunner {

    private final BalanceService balanceService;

    @Override
    public void run(String... args) throws Exception {

        Balance balanceUser = Balance.builder().number("PS1111111111").balanceName("user").build();
        Balance balanceAdmin = Balance.builder().number("PS2222222222").balanceName("admin").build();

        balanceService.create(balanceUser);
        balanceService.create(balanceAdmin);
    }
}
