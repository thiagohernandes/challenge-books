package com.book.api.controller;

import com.book.api.controller.http.response.SubjectResponse;
import com.book.api.service.impl.SubjectServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/subject")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SubjectController {

    private final SubjectServiceImpl subjectService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono<List<SubjectResponse>> getAllSubjects() {
        return Mono.just(subjectService.getAllSubjects());
    }
}
