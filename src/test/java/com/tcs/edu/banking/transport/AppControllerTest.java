package com.tcs.edu.banking.transport;

import com.tcs.edu.banking.AppController;
import com.tcs.edu.banking.account.persist.InmemoryAccountRepository;
import com.tcs.edu.banking.account.service.AccountService;
import com.tcs.edu.banking.account.service.ReportingService;
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

public class AppControllerTest {
    private final Path path = Paths.get("target/test.txt");
    private final InmemoryAccountRepository accounts = new InmemoryAccountRepository();
    private final AppController appController = new AppController(
            new FileMessageRepository(path),
            new NumberedDecorator(),
            new TimestampDecorator(),
            new AccountService(accounts),
            new ReportingService(accounts));

    @BeforeEach
    public void tearDown() throws IOException {
        path.toFile().delete();
    }

    @Test
    public void shouldLogMessageWhenSquareFormat() throws IOException {
        appController.process(new SquareFormatMessage("square", Severity.INFO));

        assertThat(Files.readString(path))
                .contains("0")
                .contains("[INFO]")
                .contains("square");
    }

    @Test
    public void shouldLogMessageWhenRoundFormat() throws IOException {
        appController.process(new RoundFormatMessage("round", Severity.INFO));

        assertThat(Files.readString(path))
                .contains("0")
                .contains("(INFO)")
                .contains("round");
    }
}
