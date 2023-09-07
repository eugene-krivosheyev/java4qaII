package com.tcs.edu.banking;

import com.tcs.edu.banking.account.domain.CreditAccount;
import com.tcs.edu.banking.account.domain.DepositAccount;
import com.tcs.edu.banking.account.service.AccountService;
import com.tcs.edu.banking.account.service.ReportingService;
import com.tcs.edu.banking.transport.domain.Message;
import com.tcs.edu.banking.transport.persist.FileMessageRepository;
import com.tcs.edu.banking.transport.service.NumberedDecorator;
import com.tcs.edu.banking.transport.service.TimestampDecorator;

import java.io.IOException;
import java.util.Objects;

public class AppController {
    private final FileMessageRepository messageRepository;
    private final NumberedDecorator numberDecorator;
    private final TimestampDecorator timestampDecorator;
    private final AccountService accountService;
    private final ReportingService reportingService;

    public AppController(FileMessageRepository messageRepository, NumberedDecorator numberDecorator, TimestampDecorator timestampDecorator, AccountService accountService, ReportingService reportingService) {
        this.messageRepository = messageRepository;
        this.numberDecorator = numberDecorator;
        this.timestampDecorator = timestampDecorator;
        this.accountService = accountService;
        this.reportingService = reportingService;
    }

    public String process(Message message) throws IOException {
        //region Pre Conditions
        if (Objects.isNull(message)) throw new IllegalArgumentException("Message cannot be null");
        if (Objects.isNull(message.getBody())) throw new IllegalArgumentException("Message body cannot be null");
        //endregion

        final var body = message.getBody();
        //region Log Command
        messageRepository.save(
                numberDecorator.decorate(
                        timestampDecorator.decorate(
                                body
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
        }
        else if (body.startsWith("TRANSFER")) {
            final var params = body.substring("TRANSFER".length()).split(" ");
            if (params.length != 4) throw new IllegalArgumentException("Invalid command");

            final var from = Integer.parseInt(params[1]);
            final var to = Integer.parseInt(params[2]);
            final var amount = Double.parseDouble(params[3]);

            accountService.transfer(from, to, amount);
            return "OK";

        }
        else if (body.startsWith("REPORT")) {
            return reportingService.generate();
        }
        else {
            throw new IllegalArgumentException("Unknown command");
        }
        //endregion
    }
}
