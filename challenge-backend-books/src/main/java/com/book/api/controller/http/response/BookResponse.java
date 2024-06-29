package com.book.api.controller.http.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Jacksonized
public class BookResponse {

    private Integer id;
    private String title;
    private String publishingCompany;
    private Integer edition;
    private String publishYear;
    private BigDecimal price;
    private List<BookSubjectResponse> subjects;
    private List<BookWriterResponse> writers;
}
