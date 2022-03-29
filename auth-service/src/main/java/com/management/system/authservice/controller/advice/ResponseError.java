package com.management.system.authservice.controller.advice;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ResponseError {

    private LocalDateTime timestamp;
    private String message;
    private int status;
}
