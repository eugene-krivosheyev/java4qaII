package com.tcs.edu.banking.transport;

import com.tcs.edu.banking.AppController;
import com.tcs.edu.banking.account.persist.InmemoryAccountRepository;
import com.tcs.edu.banking.account.service.AccountService;
import com.tcs.edu.banking.account.service.ReportingService;
import com.tcs.edu.banking.error.ProcessingException;
import com.tcs.edu.banking.transport.domain.RoundFormatMessage;
import com.tcs.edu.banking.transport.domain.Severity;
import com.tcs.edu.banking.transport.domain.SquareFormatMessage;
import com.tcs.edu.banking.transport.persist.FileMessageRepository;
import com.tcs.edu.banking.transport.service.NumberedDecorator;
import com.tcs.edu.banking.transport.service.TimestampDecorator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @see java.nio.file.Paths#get
 * @see org.junit.jupiter.api.BeforeEach
 * @see java.nio.file.Files#deleteIfExists
 * @see java.nio.file.Files#readString
 */
public class AppControllerLogTest {
    private final Path path = Paths.get("target/log.txt");
    private final InmemoryAccountRepository accounts = new InmemoryAccountRepository();
    private final AppController appController = new AppController(
            new FileMessageRepository(path),
            new NumberedDecorator(),
            new TimestampDecorator(),
            new AccountService(accounts),
            new ReportingService(accounts));

    @BeforeEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(path);
    }

    @Test
    public void shouldLogInfoMessageWhenSquareFormat() throws IOException, ProcessingException {
        appController.process(new SquareFormatMessage("REPORT", Severity.INFO));

        assertThat(Files.readString(path))
                .contains("0")
                .contains("[INFO]")
                .contains("REPORT");
    }

    @Test
    public void shouldLogInfoMessageWhenRoundFormat() throws IOException, ProcessingException {
        appController.process(new RoundFormatMessage("REPORT", Severity.INFO));

        assertThat(Files.readString(path))
                .contains("0")
                .contains("(INFO)")
                .contains("REPORT");
    }
}
