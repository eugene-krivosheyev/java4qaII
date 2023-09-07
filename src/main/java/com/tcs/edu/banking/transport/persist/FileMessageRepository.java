package com.tcs.edu.banking.transport.persist;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileMessageRepository {
    private final Path path;

    public FileMessageRepository(Path path) {
        this.path = path;
    }

    public void save(String message) throws IOException {
        Files.writeString(path,
                message + System.lineSeparator(),
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }
}
