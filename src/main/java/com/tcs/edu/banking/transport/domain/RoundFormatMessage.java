package com.tcs.edu.banking.transport.domain;

/**
 * Инкапсулирует:
 * - данные команды
 * - поведение форматирования тела команды
 */
public class RoundFormatMessage extends FormatMessage {
    public RoundFormatMessage(String body, Severity severity) {
        super(body, severity);
    }

    /**
     * @return строковое представление команды по шаблону '(severity) body'
     * @see java.lang.String#format 
     */
    @Override
    public String getDecoratedValue() {
        return String.format("(%s) %s", getSeverity(), getBody());
    }
}
