package com.tcs.edu.banking.transport.persist;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Инкапсулирует:
 * - путь к файлу журнала истории команд
 * - поведение сохранения строки в файл журнала истории команд
 * @see java.nio.file.Path
 */
public class FileMessageRepository {
    private final Path path;

    public FileMessageRepository(Path path) {
        this.path = path;
    }

    /**
     * @param message сохраняемая строка
     * @throws java.io.IOException в случае ошибок при сохранении в файл
     * @see java.nio.file.Files#writeString
     * @see java.nio.file.StandardOpenOption#CREATE
     * @see java.nio.file.StandardOpenOption#APPEND
     * @see System#lineSeparator()
     */
    public void save(String message) throws IOException {
        Files.writeString(path,
                message + System.lineSeparator(),
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }
}
