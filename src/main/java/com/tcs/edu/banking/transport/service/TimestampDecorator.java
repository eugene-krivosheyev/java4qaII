package com.tcs.edu.banking.transport.service;

import com.tcs.edu.banking.transport.domain.Message;

import java.time.LocalDateTime;

public class TimestampDecorator {
    /**
     * @param body строка, включающая тело команды для декорирования перед журналированием
     * @return строка по шаблону 'datetime body'
     * @see java.lang.String#format
     * @see java.time.LocalDateTime#now
     */
    public String decorate(String body) {
        return String.format("%s %s", LocalDateTime.now(), body);
    }
}
