package com.book.api.service.impl;

import com.book.api.controller.http.response.WriterResponse;
import com.book.api.database.repository.WriterRepository;
import com.book.api.exception.DatabaseException;
import com.book.api.factory.WriterFactory;
import com.book.api.service.WriterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WriterServiceImpl implements WriterService {

    private final WriterRepository writerRepository;
    private final WriterFactory writerFactory;

    public List<WriterResponse> getAllWriters() {
        final var writers = Optional.of(writerRepository.findAll());

        return writers.map(writerFactory::entityToResponse)
            .orElseThrow(() -> new DatabaseException("Não foi retornar os autores!"));
    }
}
