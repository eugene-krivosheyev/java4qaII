package com.tcs.edu.banking.transport.service;

import com.tcs.edu.banking.transport.domain.Message;

import java.time.LocalDateTime;

public class TimestampDecorator {
    public String decorate(String body) {
        return String.format("%s %s", LocalDateTime.now(), body);
    }
}
