package com.book.api.service;

import com.book.api.database.entity.WriterEntity;
import com.book.api.database.repository.WriterRepository;
import com.book.api.exception.DatabaseException;
import com.book.api.factory.WriterFactory;
import com.book.api.service.impl.WriterServiceImpl;
import com.book.api.support.SuportTests;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WriterServiceTest extends SuportTests {

    @Mock
    private WriterRepository writerRepository;
    @Mock
    private WriterFactory writerFactory;
    @InjectMocks
    private WriterServiceImpl writerService;

    @Test
    void shouldGetAllSubjects() {
        when(writerRepository.findAll())
            .thenReturn(List.of(mock(WriterEntity.class)));

        final var writers = writerService.getAllWriters();
        assertNotNull(writers);
    }

    @Test
    void shouldExceptionWhenGeallSubjects() {
        when(writerRepository.findAll())
            .thenThrow(DatabaseException.class);

        assertThrows(DatabaseException.class,
            () -> writerService.getAllWriters());
    }
}
