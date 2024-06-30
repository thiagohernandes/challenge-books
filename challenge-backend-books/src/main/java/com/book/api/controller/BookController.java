package com.book.api.controller;

import com.book.api.controller.http.request.BookFilterRequest;
import com.book.api.controller.http.request.BookPersistRequest;
import com.book.api.controller.http.response.BookResponse;
import com.book.api.service.impl.BookServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookServiceImpl bookService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono<List<BookResponse>> getBooksByFilter(@RequestParam(required = false) String title,
                                                     @RequestParam(required = false) String publishingCompany,
                                                     @RequestParam(defaultValue = "0") Integer page,
                                                     @RequestParam(defaultValue = "10") Integer size) {
        final var bookFilter = BookFilterRequest.builder()
            .title(title)
            .publishingCompany(publishingCompany)
            .page(page)
            .size(size)
            .build();

        return bookService.getBooksByFilters(bookFilter);
    }

    @PostMapping
    public ResponseEntity<Void> saveOrUpdateBook(@RequestBody
                                                 @Valid BookPersistRequest saveRequest) {
        bookService.saveOrUpdateBook(saveRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{idBook}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Integer> deleteBook(@PathVariable Integer idBook) {
        return ResponseEntity.ok(bookService.deleteBook(idBook));
    }

    @GetMapping("/pdf")
    @ResponseStatus(HttpStatus.OK)
    public Mono<String> getPDFBooksByFilter(@RequestParam(required = false) String title,
                                                      @RequestParam(required = false) String publishingCompany,
                                                      @RequestParam(defaultValue = "0") Integer page,
                                                      @RequestParam(defaultValue = "10") Integer size) {
        final var bookFilter = BookFilterRequest.builder()
            .title(title)
            .publishingCompany(publishingCompany)
            .page(page)
            .size(size)
            .build();

        return bookService.generatePdfBooks(bookFilter);
    }
}
