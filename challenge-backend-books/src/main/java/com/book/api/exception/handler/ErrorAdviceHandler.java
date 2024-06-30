package com.book.api.exception.handler;

import com.book.api.exception.DatabaseException;
import com.book.api.exception.ValidationException;
import com.book.api.exception.response.ErrorResponse;
import com.book.api.type.LogMessageType;
import com.book.api.util.ApiUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@RequiredArgsConstructor
public class ErrorAdviceHandler {

    private static final String ERROR_UNKNOW = "Erro desconhecido";
    private final ApiUtil apiUtil;

    @ExceptionHandler(DatabaseException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ErrorResponse handlerDatabaseException(final DatabaseException exception,
                                                                final HttpServletRequest request) {
        int httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();

        loggerHandler(exception.getMessage(),
            request.getRequestURI(),
            httpStatusCode,
            request.getMethod(),
            exception);

        return exceptionResponseHandler(request.getRequestURI(),
            exception.getMessage(), httpStatusCode, request.getMethod());
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(value = HttpStatus.PRECONDITION_FAILED)
    public @ResponseBody ErrorResponse handlerValidationException(final ValidationException exception,
                                                                  final HttpServletRequest request) {
        int httpStatusCode = HttpStatus.PRECONDITION_FAILED.value();

        loggerHandler(exception.getMessage(),
            request.getRequestURI(),
            httpStatusCode,
            request.getMethod(),
            exception);

        return exceptionResponseHandler(request.getRequestURI(),
            exception.getMessage(), httpStatusCode, request.getMethod());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ErrorResponse handlerException(final Exception exception,
                                                                      final HttpServletRequest request) {
        String messageHandle = exception == null ? ERROR_UNKNOW : exception.getMessage();
        int httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();

        loggerHandler(messageHandle,
            request.getRequestURI(),
            httpStatusCode,
            request.getMethod(), exception);

        return exceptionResponseHandler(request.getRequestURI(),
            messageHandle, httpStatusCode, request.getMethod());
    }

    private ErrorResponse exceptionResponseHandler(String requestURI,
                                                   String errorMessage,
                                                   int statusCode,
                                                   String method) {
        return ErrorResponse.builder()
            .calledUrl(requestURI)
            .dateTime(apiUtil.dateTimeFormated())
            .errorMessage(errorMessage)
            .statusCode(statusCode)
            .method(method)
            .build();
    }

    private void loggerHandler(String message, String calledURL,
                               int statusCode, String method, Throwable exception) {
        apiUtil.logMessage("Erro: " + message + " URL: " + calledURL + " Status code: "
            + statusCode + " Método: " + method + " Exceção: " + exception, LogMessageType.ERROR);
    }
}
