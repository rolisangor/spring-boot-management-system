package com.management.system.authservice.controller;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessage {
    private String message;

    public static ResponseMessage message(String s) {
        return new ResponseMessage(s);
    }
}
