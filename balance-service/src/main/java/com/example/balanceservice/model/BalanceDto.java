package com.example.balanceservice.model;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BalanceDto {

    private Long id;
    private String balanceUsername;
    private String number;
}
