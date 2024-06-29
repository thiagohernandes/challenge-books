package com.book.api.exception.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    private String errorMessage;
    private String dateTime;
    private String calledUrl;
    private int statusCode;
    private String method;
}
