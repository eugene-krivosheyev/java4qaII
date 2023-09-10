package com.tcs.edu.banking.transport.domain;

/**
 * Immutable POJO.
 */
public class SquareFormatMessage extends FormatMessage {
    @Override
    public Severity getSeverity() {
        return super.getSeverity();
    }

    public SquareFormatMessage(String body, Severity severity) {
        super(body, severity);
    }

    @Override
    public String getDecoratedValue() {
        return String.format("[%s] %s", getSeverity(), getBody());
    }
}
