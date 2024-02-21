package com.tcs.edu.banking.account;

import com.tcs.edu.banking.AppController;
import com.tcs.edu.banking.account.persist.InmemoryRepository;
import com.tcs.edu.banking.account.service.AccountService;
import com.tcs.edu.banking.account.service.ReportingService;
import com.tcs.edu.banking.error.ProcessingException;
import com.tcs.edu.banking.transport.domain.RoundFormatMessage;
import com.tcs.edu.banking.transport.domain.Severity;
import com.tcs.edu.banking.transport.domain.SquareFormatMessage;
import com.tcs.edu.banking.transport.persist.FileMessageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Системные тесты:
 * для операций используется только публичный API AppController
 */
public class AppControllerProcessTest {
    private final AtomicInteger counter = new AtomicInteger();
    //region fixture
    private final Path path = Paths.get("target/log.txt");
    private final InmemoryRepository accounts = new InmemoryRepository();
    private final AppController appController = new AppController(
            new FileMessageRepository(path),
            (String body) -> String.format("%d %s", counter.incrementAndGet(), body),
            (String body) -> String.format("%s %s", LocalDateTime.now(), body),
            new AccountService(accounts),
            new ReportingService(accounts));
    //endregion

    @ParameterizedTest
    @ValueSource(strings = {"CREDIT", "DEPOSIT"})
    public void shouldCreateAccount(String type) throws ProcessingException {
        var id = Integer.parseInt(
                appController.process(
                        new SquareFormatMessage("CREATE ACCOUNT " + type, Severity.INFO)));

        var report = appController.process(
                new SquareFormatMessage("REPORT", Severity.INFO));

        assertThat(report).containsOnlyOnce(id + ": " + type + " 0.0");
    }

    @Test
    public void shouldTransferFromCreditToCredit() throws ProcessingException {
        var fromId = Integer.parseInt(
                appController.process(
                        new SquareFormatMessage("CREATE ACCOUNT CREDIT", Severity.INFO)));
        var toId = Integer.parseInt(
                appController.process(
                        new SquareFormatMessage("CREATE ACCOUNT CREDIT", Severity.INFO)));

        appController.process(new RoundFormatMessage(
                "TRANSFER " + fromId + " " + toId + " 100.0", Severity.INFO));

        var report = appController.process(
                new SquareFormatMessage("REPORT", Severity.INFO));

        assertThat(report)
                .containsOnlyOnce(fromId + ": CREDIT -100.0")
                .containsOnlyOnce(toId + ": CREDIT 100.0");
    }
}
