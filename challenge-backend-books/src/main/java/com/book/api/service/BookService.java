package com.book.api.service;

import com.book.api.controller.http.request.BookFilterRequest;
import com.book.api.controller.http.request.BookPersistRequest;
import com.book.api.controller.http.response.BookResponse;
import reactor.core.publisher.Mono;

import java.util.List;

public interface BookService {

    Mono<List<BookResponse>> getBooksByFilters(final BookFilterRequest bookFilterRequest);
    void saveOrUpdateBook(final BookPersistRequest bookPersistRequest);
    Integer deleteBook(final Integer idBook);
    String generatePdfBooks(final BookFilterRequest bookFilterRequest);

}
