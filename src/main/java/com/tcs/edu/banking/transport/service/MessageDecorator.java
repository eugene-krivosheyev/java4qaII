package com.tcs.edu.banking.transport.service;

/**
 * Инкапсулирует поведение обогащения тела сообщения при журналировании.
 */
@FunctionalInterface
public interface MessageDecorator {
    String decorate(String body);
}
