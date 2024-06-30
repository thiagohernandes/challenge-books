package com.book.api.factory;

import com.book.api.controller.http.request.BookPersistRequest;
import com.book.api.controller.http.response.BookResponse;
import com.book.api.controller.http.response.BookSubjectResponse;
import com.book.api.controller.http.response.BookWriterResponse;
import com.book.api.database.entity.BookEntity;
import com.book.api.domain.BookSubjectDto;
import com.book.api.domain.BookWriterDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookFactory {

    public BookResponse entityToResponse(final BookEntity entity,
                                         final List<BookSubjectDto> bookSubjectDtos,
                                         final List<BookWriterDto> bookWriterDtos) {
        return BookResponse.builder()
            .id(entity.getId())
            .title(entity.getTitle())
            .edition(entity.getEdition())
            .price(entity.getPrice())
            .publishingCompany(entity.getPublishingCompany())
            .publishYear(entity.getPublishYear())
            .subjects(bookSubjectDtos
                .stream().map(this::dtoToResponse).toList())
            .writers(bookWriterDtos
                .stream().map(this::dtoToResponse).toList())
            .build();
    }

    public BookEntity requestToEntity(final BookPersistRequest request) {
        return BookEntity.builder()
            .id(request.getId())
            .title(request.getTitle().toUpperCase())
            .edition(request.getEdition())
            .publishYear(request.getPublishYear())
            .publishingCompany(request.getPublishingCompany().toUpperCase())
            .price(request.getPrice())
            .build();
    }

    private BookSubjectResponse dtoToResponse(final BookSubjectDto dto) {
        return BookSubjectResponse.builder()
            .id(dto.getId())
            .idBook(dto.getIdBook())
            .idSubject(dto.getIdSubject())
            .subjectDescription(dto.getSubjectDescription())
            .build();
    }

    private BookWriterResponse dtoToResponse(final BookWriterDto dto) {
        return BookWriterResponse.builder()
            .id(dto.getId())
            .idBook(dto.getIdBook())
            .idWriter(dto.getIdWriter())
            .writerName(dto.getWriterName())
            .build();
    }
}
