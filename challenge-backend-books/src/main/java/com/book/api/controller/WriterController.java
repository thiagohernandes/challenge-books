package com.book.api.controller;

import com.book.api.controller.http.response.WriterResponse;
import com.book.api.service.impl.WriterServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/writer")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class WriterController {

    private final WriterServiceImpl writerService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono<List<WriterResponse>> getAllWriters() {
        return Mono.just(writerService.getAllWriters());
    }
}
