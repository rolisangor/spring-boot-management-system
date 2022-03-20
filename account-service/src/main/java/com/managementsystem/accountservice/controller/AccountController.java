package com.managementsystem.accountservice.controller;

import com.managementsystem.accountservice.model.Account;
import com.managementsystem.accountservice.model.AccountDto;
import com.managementsystem.accountservice.service.AccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@AllArgsConstructor
@RequestMapping("/api/account")
@Slf4j
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findAccountById(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.findById(id).orElseThrow());
    }

    @PostMapping()
    @PreAuthorize("#oauth2.hasScope('server') || hasAuthority('ADMIN')")
    public ResponseEntity<?> createAccount(@RequestBody AccountDto accountDto, Principal principal) {
        log.info("CREATE_ACCOUNT_PRINCIPAL_NAME: {}", principal.getName());
        return ResponseEntity.ok(accountService.create(Account.builder().name(accountDto.getName()).build()).orElseThrow());
    }
}
