package com.example.balanceservice.controller;

import com.example.balanceservice.exception.BalanceCreateException;
import com.example.balanceservice.model.Balance;
import com.example.balanceservice.model.BalanceDto;
import com.example.balanceservice.service.BalanceService;
import com.example.balanceservice.service.mapper.BalanceMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/balance")
@AllArgsConstructor
@Slf4j
public class BalanceController {

    private final BalanceService balanceService;
    private final BalanceMapper balanceMapper;

    @PostMapping("/{username}")
    @PreAuthorize("#oauth2.hasScope('server')")
    public ResponseEntity<?> create(@PathVariable String username) {
        Balance balance = balanceService.create(username).orElseThrow(() -> new BalanceCreateException("Can't create balance"));
        return ResponseEntity.ok(balanceMapper.toBalanceDto(balance));
    }

    @GetMapping
    public ResponseEntity<?> getBalance(Principal principal) {
        log.info("PRINCIPAL: {}", principal.getName());
        return ResponseEntity.ok(balanceService.findByBalanceUsername(principal.getName()).orElseThrow());
    }
}
