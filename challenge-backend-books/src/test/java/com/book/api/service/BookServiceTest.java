package com.book.api.service;

import com.book.api.controller.http.request.BookFilterRequest;
import com.book.api.controller.http.request.BookPersistRequest;
import com.book.api.controller.http.response.BookResponse;
import com.book.api.database.entity.BookEntity;
import com.book.api.database.entity.BookSubjectEntity;
import com.book.api.database.entity.BookWriterEntity;
import com.book.api.database.repository.BookRepository;
import com.book.api.database.repository.BookSubjectRepository;
import com.book.api.database.repository.BookWriterRepository;
import com.book.api.domain.BookSubjectDto;
import com.book.api.domain.BookWriterDto;
import com.book.api.exception.DatabaseException;
import com.book.api.exception.ValidationException;
import com.book.api.factory.BookFactory;
import com.book.api.service.helper.impl.PdfHelperImpl;
import com.book.api.service.impl.BookServiceImpl;
import com.book.api.support.SuportTests;
import com.book.api.util.ApiUtil;
import com.book.api.validation.BookValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest extends SuportTests {

    private final BookPersistRequest bookPersistRequest = generator.nextObject(BookPersistRequest.class);
    private final BookFilterRequest bookFilterRequest = BookFilterRequest
        .builder()
        .page(0)
        .size(10)
        .publishingCompany("Editora")
        .title("Título")
        .build();
    private Page<BookEntity> bookEntityPage;
    private Pageable pageable;
    private List<BookSubjectDto> bookSubjectDtoList = new ArrayList<>();
    private List<BookWriterDto> bookWriterDtoList = new ArrayList<>();
    private BookEntity bookEntity;

    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookSubjectRepository bookSubjectRepository;
    @Mock
    private BookWriterRepository bookWriterRepository;
    @Mock
    private BookFactory bookFactory;
    @Mock
    private ApiUtil apiUtil;
    @Mock
    private BookValidation bookValidation;
    @Mock
    private PdfHelperImpl pdfHelper;
    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    void init() {
        bookSubjectDtoList.add(mock(BookSubjectDto.class));
        bookSubjectDtoList.add(mock(BookSubjectDto.class));
        bookWriterDtoList.add(mock(BookWriterDto.class));
        bookWriterDtoList.add(mock(BookWriterDto.class));

        bookEntity = BookEntity.builder()
            .id(bookPersistRequest.getId())
            .price(bookPersistRequest.getPrice())
            .publishYear(bookPersistRequest.getPublishYear())
            .edition(bookPersistRequest.getEdition())
            .title(bookPersistRequest.getTitle())
            .publishingCompany(bookPersistRequest.getPublishingCompany())
            .build();

        pageable = PageRequest.of(bookFilterRequest.getPage(), bookFilterRequest.getSize());
        bookEntityPage = new PageImpl<>(List.of(mock(BookEntity.class)));
    }

    @Test
    void shouldGetBooksByFilter() {
        when(bookRepository.findByTitleLikeAndPublishingCompanyLike("%"
                + bookFilterRequest.getTitle().toUpperCase() + "%",
            "%"
            + bookFilterRequest.getPublishingCompany().toUpperCase() + "%", pageable))
            .thenReturn(bookEntityPage);

        final var booksResponse = bookService.getBooksByFilters(bookFilterRequest);
        assertNotNull(booksResponse);
    }

    @Test
    void shouldExceptionSizeInvalidData() {
        bookFilterRequest.setSize(100);

        assertThrows(ValidationException.class,
            () -> bookService.getBooksByFilters(bookFilterRequest));
    }

    @Test
    void shouldExceptionGetSubjectDataBase() {
        when(bookRepository.findByTitleLikeAndPublishingCompanyLike("%"
                + bookFilterRequest.getTitle().toUpperCase() + "%",
            "%"
                + bookFilterRequest.getPublishingCompany().toUpperCase() + "%", pageable))
            .thenReturn(bookEntityPage);

        when(bookSubjectRepository.getBookSubjectsByIdBook(any()))
            .thenThrow(DatabaseException.class);

        assertThrows(DatabaseException.class,
            () -> bookService.getBooksByFilters(bookFilterRequest));
    }

    @Test
    void shouldExceptionGetWritersDataBase() {
        when(bookRepository.findByTitleLikeAndPublishingCompanyLike("%"
                + bookFilterRequest.getTitle().toUpperCase() + "%",
            "%"
                + bookFilterRequest.getPublishingCompany().toUpperCase() + "%", pageable))
            .thenReturn(bookEntityPage);

        when(bookSubjectRepository.getBookSubjectsByIdBook(any()))
            .thenReturn(bookSubjectDtoList);

        when(bookWriterRepository.getBookWritersByIdBook(any()))
            .thenThrow(DatabaseException.class);

        assertThrows(DatabaseException.class,
            () -> bookService.getBooksByFilters(bookFilterRequest));
    }

    @Test
    void shouldSaveBook() {
        when(bookFactory.requestToEntity(bookPersistRequest))
            .thenReturn(bookEntity);

        when(bookRepository.save(bookEntity))
            .thenReturn(bookEntity);

        when(bookSubjectRepository.save(any()))
            .thenReturn(mock(BookSubjectEntity.class));

        when(bookWriterRepository.save(any()))
            .thenReturn(mock(BookWriterEntity.class));

        bookService.saveOrUpdateBook(bookPersistRequest);

        assertNotNull(bookPersistRequest);
    }

    @Test
    void shouldExceptionSubjectSaveBook() {
        when(bookFactory.requestToEntity(bookPersistRequest))
            .thenReturn(bookEntity);

        when(bookRepository.save(bookEntity))
            .thenReturn(bookEntity);

        when(bookSubjectRepository.save(any()))
            .thenThrow(DatabaseException.class);

        assertThrows(DatabaseException.class,
            () -> bookService.saveOrUpdateBook(bookPersistRequest));
    }

    @Test
    void shouldExceptionWriterSaveBook() {
        when(bookFactory.requestToEntity(bookPersistRequest))
            .thenReturn(bookEntity);

        when(bookRepository.save(bookEntity))
            .thenReturn(bookEntity);

        when(bookSubjectRepository.save(any()))
            .thenReturn(mock(BookSubjectEntity.class));

        when(bookWriterRepository.save(any()))
            .thenThrow(DatabaseException.class);

        assertThrows(DatabaseException.class,
            () -> bookService.saveOrUpdateBook(bookPersistRequest));
    }

    @Test
    void shouldDeleteBook() {
        when(bookWriterRepository.deleteByIdBook(any()))
            .thenReturn(1);

        when(bookSubjectRepository.deleteByIdBook(any()))
            .thenReturn(1);

        var deleted = bookService.deleteBook(1);
        assertNotNull(deleted);
    }

    @Test
    void shouldGeneratePdfBooks() {
        when(bookRepository.findByTitleLikeAndPublishingCompanyLike("%"
                + bookFilterRequest.getTitle().toUpperCase() + "%",
            "%"
                + bookFilterRequest.getPublishingCompany().toUpperCase() + "%", pageable))
            .thenReturn(bookEntityPage);

        when(pdfHelper
            .createPdfReport(any())).thenReturn("ANY DATA");

        final var booksResponse = bookService.generatePdfBooks(bookFilterRequest);
        assertNotNull(booksResponse);
    }

    @Test
    void shouldGeBookById() {
        final var id = 1;

        when(bookRepository.findById(id))
            .thenReturn(Optional.of(bookEntity));

        when(bookSubjectRepository.getBookSubjectsByIdBook(bookEntity.getId()))
            .thenReturn(bookSubjectDtoList);

        when(bookWriterRepository.getBookWritersByIdBook(bookEntity.getId()))
            .thenReturn(bookWriterDtoList);

        assertNotNull(bookService.getBookById(id));
    }

    @Test
    void shouldGetExceptionOnGetBookById() {
        final var id = bookEntity.getId();
        BookResponse bookResponse = BookResponse.builder()
            .id(bookEntity.getId())
            .title(bookEntity.getTitle())
            .edition(bookEntity.getEdition())
            .publishYear(bookEntity.getPublishYear())
            .publishingCompany(bookEntity.getPublishingCompany())
            .price(bookEntity.getPrice())
            .build();

        when(bookRepository.findById(id))
            .thenReturn(Optional.of(bookEntity));

        when(bookSubjectRepository.getBookSubjectsByIdBook(id))
            .thenReturn(bookSubjectDtoList);

        when(bookWriterRepository.getBookWritersByIdBook(id))
            .thenReturn(bookWriterDtoList);

        when(bookFactory.entityToResponse(bookEntity, bookSubjectDtoList,
            bookWriterDtoList)).thenReturn(bookResponse);

        assertNotNull(bookService.getBookById(id));
    }
}
