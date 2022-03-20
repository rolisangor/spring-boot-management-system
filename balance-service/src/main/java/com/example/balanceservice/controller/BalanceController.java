package com.example.balanceservice.controller;

import com.example.balanceservice.model.Balance;
import com.example.balanceservice.service.BalanceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/balance")
@AllArgsConstructor
@Slf4j
public class BalanceController {

    private final BalanceService balanceService;

    @PostMapping
    @PreAuthorize("#oauth2.hasScope('server')")
    public ResponseEntity<?> create(@RequestBody Balance balance) {
        return ResponseEntity.ok(balanceService.create(balance).orElseThrow());
    }

    @GetMapping
    public ResponseEntity<?> getBalance(Principal principal) {
        log.info("PRINCIPAL: {}", principal.getName());
        return ResponseEntity.ok(balanceService.findByName(principal.getName()).orElseThrow());
    }
}
