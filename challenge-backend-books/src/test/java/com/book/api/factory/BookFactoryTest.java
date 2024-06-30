package com.book.api.factory;

import com.book.api.controller.http.request.BookPersistRequest;
import com.book.api.database.entity.BookEntity;
import com.book.api.domain.BookSubjectDto;
import com.book.api.domain.BookWriterDto;
import com.book.api.support.SuportTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class BookFactoryTest extends SuportTests {

    private final BookPersistRequest bookPersistRequest = generator.nextObject(BookPersistRequest.class);
    private final BookEntity bookEntity = BookEntity.builder()
        .id(1)
        .publishingCompany("Editora")
        .title("Title")
        .price(new BigDecimal("50.0"))
        .edition(1)
        .publishYear("2024")
        .build();
    private final List<BookSubjectDto> bookSubjectDtoList = new ArrayList<>();
    private final List<BookWriterDto> bookWriterDtoList = new ArrayList<>();

    @InjectMocks
    private BookFactory bookFactory;

    @BeforeEach
    void init() {
        bookSubjectDtoList.add(mock(BookSubjectDto.class));
        bookWriterDtoList.add(mock(BookWriterDto.class));
    }

    @Test
    void shoulEntityToResponse() {
        var converted = bookFactory
            .entityToResponse(bookEntity, bookSubjectDtoList, bookWriterDtoList);

        assertNotNull(converted);
    }

    @Test
    void shouldRequestToEntity() {
        var converted = bookFactory
            .requestToEntity(bookPersistRequest);

        assertNotNull(converted);
    }
}
