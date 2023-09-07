package com.tcs.edu.banking.transport.domain;

public abstract class FormatMessage implements Message {
    protected final String body;
    protected final Severity severity;

    public FormatMessage(String body, Severity severity) {
        this.body = body;
        this.severity = severity;
    }

    public String getBody() {
        return body;
    }

    protected Severity getSeverity() {
        return severity;
    }

    @Override
    public abstract String getDecoratedValue();
}
