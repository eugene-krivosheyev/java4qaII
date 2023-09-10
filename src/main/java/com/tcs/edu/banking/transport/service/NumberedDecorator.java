package com.tcs.edu.banking.transport.service;

/**
 * Инкапсулирует:
 * - Поведение декорирования.
 * - Состояние счетчика. Требований к персистированию счетчика не предъявляется, допускается сброс счетчика при пересоздании объекта этого декоратора.
 */
public class NumberedDecorator implements MessageDecorator {
    private int counter;

    /**
     * @param body строка, включающая тело команды для декорирования перед журналированием
     * @return строка по шаблону 'counter body'
     * @see java.lang.String#format
     */
    @Override
    public String decorate(String body) {
        return String.format("%d %s", counter++, body);
    }
}
