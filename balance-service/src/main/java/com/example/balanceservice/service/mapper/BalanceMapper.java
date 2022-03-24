package com.example.balanceservice.service.mapper;

import com.example.balanceservice.model.Balance;
import com.example.balanceservice.model.BalanceDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BalanceMapper {

    Balance toBalance(BalanceDto balanceDto);
    BalanceDto toBalanceDto(Balance balance);
}
