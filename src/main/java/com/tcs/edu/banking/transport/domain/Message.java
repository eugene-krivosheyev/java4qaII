package com.tcs.edu.banking.transport.domain;

/**
 * Инкапсулирует команду для обработки системой.
 */
public interface Message {
    /**
     * @return строковое тело команды для обработки.
     */
    String getBody();

    /**
     * @return дополненная доп. информацией команда для журналирования в истории операций.
     */
    String getDecoratedValue();
}
