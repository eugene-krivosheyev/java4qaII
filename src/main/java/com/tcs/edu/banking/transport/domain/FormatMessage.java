package com.tcs.edu.banking.transport.domain;

/**
 * Инкапсулирует:
 * - body: строковое тело команды для обработки
 * - severity: уровень значимости для журналирования
 */
public abstract class FormatMessage implements Message {
    protected final String body;
    protected final Severity severity;

    public FormatMessage(String body, Severity severity) {
        this.body = body;
        this.severity = severity;
    }

    @Override
    public String getBody() {
        return body;
    }

    protected Severity getSeverity() {
        return severity;
    }
}
