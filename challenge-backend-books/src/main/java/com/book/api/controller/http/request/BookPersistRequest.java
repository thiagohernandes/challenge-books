package com.book.api.controller.http.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Jacksonized
public class BookPersistRequest {

    private Integer id;
    private String title;
    private String publishingCompany;
    private Integer edition;
    private String publishYear;
    private BigDecimal price;
    private Integer[] writers;
    private Integer[] subjects;
}
