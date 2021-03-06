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

        balanceService.create("user");
        balanceService.create("admin");
    }
}
