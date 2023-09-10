package com.tcs.edu.banking;

import com.tcs.edu.banking.account.domain.CreditAccount;
import com.tcs.edu.banking.account.domain.DepositAccount;
import com.tcs.edu.banking.account.service.AccountService;
import com.tcs.edu.banking.account.service.ReportingService;
import com.tcs.edu.banking.error.ProcessingException;
import com.tcs.edu.banking.transport.domain.Message;
import com.tcs.edu.banking.transport.persist.FileMessageRepository;
import com.tcs.edu.banking.transport.service.MessageDecorator;

import java.util.Objects;

/**
 * Ответственности:
 * - [x] точка входа приложения: афиширует методы публичного API всей системы
 * - [x] инкапсулирует общий алгоритм обработки входящих команд
 * - [x] валидация данных входящих команд
 * - [x] журналирование входящих команд
 * в этом релизе:
 * - [ ] парсинг входящих команд и их параметров
 * в будущих релизах:
 * - [ ] диспетчеризация: вызов нужной логики обработки команды
 */
public class AppController {
    private final FileMessageRepository messageRepository;
    private final MessageDecorator numberDecorator;
    private final MessageDecorator timestampDecorator;
    private final AccountService accountService;
    private final ReportingService reportingService;

    public AppController(FileMessageRepository messageRepository, MessageDecorator numberDecorator, MessageDecorator timestampDecorator, AccountService accountService, ReportingService reportingService) {
        this.messageRepository = messageRepository;
        this.numberDecorator = numberDecorator;
        this.timestampDecorator = timestampDecorator;
        this.accountService = accountService;
        this.reportingService = reportingService;
    }

    /**
     * Метод обработки входящей команды.
     * @param message Входящая команда типа {@link com.tcs.edu.banking.transport.domain.Message}.
     * @return Строковый результат обработки входящей команды.
     * @throws com.tcs.edu.banking.error.ProcessingException в случае ошибки обработки команды. Инкапсулирует ошибку-причину.
     * @throws java.lang.IllegalArgumentException в случае невалидной входящей команды: команда == null, тело == null, значимость == null, команда не соответствует ни одной допустимой структуре
     * @see java.util.Objects#isNull
     * @see java.lang.String#startsWith
     * @see java.lang.String#substring
     * @see java.lang.String#length
     * @see java.lang.String#split
     * @see java.lang.String#valueOf
     */
    public String process(Message message) throws ProcessingException {
        //region Pre Conditions
        if (Objects.isNull(message)) throw new IllegalArgumentException("Message cannot be null");
        if (Objects.isNull(message.getBody())) throw new IllegalArgumentException("Message body cannot be null");
        //endregion

        final var body = message.getBody();
        try {
            //region Log Command
            messageRepository.save(
                    numberDecorator.decorate(
                            timestampDecorator.decorate(
                                    message.getDecoratedValue()
                            )
                    )
            );
            //endregion

            //region Dispatching/Routing
            if (body.startsWith("CREATE ACCOUNT")) {
                final var params = body.substring("CREATE ACCOUNT".length()).split(" ");
                if (params.length != 2) throw new IllegalArgumentException("Invalid command");

                final var accountType = params[1];
                switch (accountType) {
                    case "DEPOSIT":
                        return String.valueOf(accountService.create(new DepositAccount()));
                    case "CREDIT":
                        return String.valueOf(accountService.create(new CreditAccount()));
                    default:
                        throw new IllegalArgumentException("Unknown account type");
                }
            } else if (body.startsWith("TRANSFER")) {
                final var params = body.substring("TRANSFER".length()).split(" ");
                if (params.length != 4) throw new IllegalArgumentException("Invalid command");

                final var from = Integer.parseInt(params[1]);
                final var to = Integer.parseInt(params[2]);
                final var amount = Double.parseDouble(params[3]);

                accountService.transfer(from, to, amount);
                return "OK";

            } else if (body.startsWith("REPORT")) {
                return reportingService.generate();
            } else {
                throw new IllegalArgumentException("Unknown command");
            }
        } catch (Exception e) {
            throw new ProcessingException(e);
        }
        //endregion
    }
}
