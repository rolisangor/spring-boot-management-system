package com.managementsystem.accountservice.model;

import lombok.*;

@Builder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BalanceDto {

    private Long id;
    private String balanceName;
    private String number;
}
