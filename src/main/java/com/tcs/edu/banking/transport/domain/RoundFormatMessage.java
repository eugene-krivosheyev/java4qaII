package com.tcs.edu.banking.transport.domain;

public class RoundFormatMessage extends FormatMessage {
    public RoundFormatMessage(String body, Severity severity) {
        super(body, severity);
    }

    @Override
    public String getDecoratedValue() {
        return String.format("(%s) %s", getSeverity(), getBody());
    }
}
