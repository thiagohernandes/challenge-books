package com.book.api.controller.http.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Jacksonized
public class BookFilterRequest {

    private String title;
    private String publishingCompany;
    private Integer page;
    private Integer size;
}
