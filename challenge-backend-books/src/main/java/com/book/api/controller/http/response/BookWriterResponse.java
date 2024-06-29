package com.book.api.controller.http.response;

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
public class BookWriterResponse {

    private Integer id;
    private Integer idBook;
    private Integer idWriter;
    private String writerName;

}
