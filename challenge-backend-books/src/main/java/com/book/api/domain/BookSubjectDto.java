package com.book.api.domain;

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
public class BookSubjectDto {

    private Integer id;
    private Integer idBook;
    private Integer idSubject;
    private String subjectDescription;
}
