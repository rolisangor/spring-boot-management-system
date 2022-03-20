package com.managementsystem.accountservice.repository;

import com.managementsystem.accountservice.model.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {

}
