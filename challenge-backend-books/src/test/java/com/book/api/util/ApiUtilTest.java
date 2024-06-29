package com.book.api.util;

import com.book.api.support.SuportTests;
import com.book.api.type.LogMessageType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;

@ExtendWith(MockitoExtension.class)
class ApiUtilTest extends SuportTests {

    private final String message = "ANY";
    @Mock
    private ApiUtil apiUtil;

    @Test
    void shouldLogInfo() {
        doCallRealMethod().when(apiUtil).logMessage(anyString(), any());

        apiUtil.logMessage(message, LogMessageType.INFO);
        assertNotNull(message);
    }

    @Test
    void shouldLogWarn() {
        doCallRealMethod().when(apiUtil).logMessage(anyString(), any());

        apiUtil.logMessage(message, LogMessageType.WARN);
        assertNotNull(message);
    }

    @Test
    void shouldLogError() {
        doCallRealMethod().when(apiUtil).logMessage(anyString(), any());

        apiUtil.logMessage(message, LogMessageType.ERROR);
        assertNotNull(message);
    }

    @Test
    void shouldFormatDateTime() {
        doCallRealMethod().when(apiUtil).dateTimeFormated();

        String formated = apiUtil.dateTimeFormated();

        assertNotNull(formated);
    }


}
