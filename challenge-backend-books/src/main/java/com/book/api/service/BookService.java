package com.book.api.service;

import com.book.api.controller.http.request.BookFilterRequest;
import com.book.api.controller.http.request.BookPersistRequest;
import com.book.api.controller.http.response.BookResponse;
import reactor.core.publisher.Mono;

import java.util.List;

public interface BookService {

    Mono<List<BookResponse>> getBooksByFilters(BookFilterRequest bookFilterRequest);

    void saveOrUpdateBook(BookPersistRequest bookPersistRequest);

    Integer deleteBook(Integer idBook);

    BookResponse getBookById(Integer id);

    Mono<String> generatePdfBooks(BookFilterRequest bookFilterRequest);
}
