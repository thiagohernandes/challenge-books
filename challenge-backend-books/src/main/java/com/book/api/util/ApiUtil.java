package com.book.api.util;

import com.book.api.type.LogMessageType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class ApiUtil {

    private static final String MESSAGE_PREFIX = "[API-BOOKS] ";

    public void logMessage(final String message,
                           final LogMessageType type) {
        if (type.equals(LogMessageType.INFO)) {
            log.info(MESSAGE_PREFIX.concat(message));
        } else if (type.equals(LogMessageType.WARN)) {
            log.warn(MESSAGE_PREFIX.concat(message));
        } else {
            log.error(MESSAGE_PREFIX.concat(message));
        }
    }

    public String dateTimeFormated() {
        return LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }
}
