package com.managementsystem.accountservice.service;

import com.managementsystem.accountservice.model.Account;
import com.managementsystem.accountservice.model.BalanceDto;
import com.managementsystem.accountservice.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
//@AllArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService{

    private final AccountRepository accountRepository;
    private final WebClient.Builder webClient;

    public AccountServiceImpl(AccountRepository accountRepository, @Qualifier(value = "server-only") WebClient.Builder webClient) {
        this.accountRepository = accountRepository;
        this.webClient = webClient;
    }

    @Transactional
    @Override
    public Optional<Account> create(Account account) {
       BalanceDto balanceDto =  webClient
                .baseUrl("http://balance-service").build()
                .post()
                .uri("/api/balance")
                .header(MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(BalanceDto.builder().number("PS2222222").build()), BalanceDto.class)
                .retrieve()
                .bodyToMono(BalanceDto.class).block();

       log.info(balanceDto.getNumber());
        return Optional.of(accountRepository.save(account));
    }

    @Transactional
    @Override
    public Optional<Account> findById(Long id) {
        return accountRepository.findById(id);
    }

    @Transactional
    @Override
    public boolean existById(Long id) {
        return accountRepository.existsById(id);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        accountRepository.deleteById(id);
    }
}
