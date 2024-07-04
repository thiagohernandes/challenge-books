package com.book.api.service.impl;

import com.book.api.controller.http.response.SubjectResponse;
import com.book.api.database.repository.SubjectRepository;
import com.book.api.exception.DatabaseException;
import com.book.api.factory.SubjectFactory;
import com.book.api.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final SubjectFactory subjectFactory;

    public List<SubjectResponse> getAllSubjects() {
        final var subjects = Optional.of(subjectRepository.findAll());

        return subjects.map(subjectFactory::entityToResponse)
            .orElseThrow(() -> new DatabaseException("Não foi retornar os assuntos!"));
    }
}
