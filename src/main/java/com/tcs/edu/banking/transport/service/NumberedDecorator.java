package com.tcs.edu.banking.transport.service;

public class NumberedDecorator {
    private int counter;

    public String decorate(String body) {
        return String.format("%d %s", counter++, body);
    }
}
