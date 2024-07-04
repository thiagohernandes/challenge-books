package com.book.api.service;

import com.book.api.controller.http.request.BookFilterRequest;
import com.book.api.controller.http.request.BookPersistRequest;
import com.book.api.controller.http.response.BookResponse;
import com.book.api.database.entity.BookEntity;
import com.book.api.database.entity.BookSubjectEntity;
import com.book.api.database.entity.BookWriterEntity;
import com.book.api.database.entity.SubjectEntity;
import com.book.api.database.repository.BookRepository;
import com.book.api.database.repository.BookSubjectRepository;
import com.book.api.database.repository.BookWriterRepository;
import com.book.api.database.repository.SubjectRepository;
import com.book.api.domain.BookSubjectDto;
import com.book.api.domain.BookWriterDto;
import com.book.api.exception.DatabaseException;
import com.book.api.exception.ValidationException;
import com.book.api.factory.BookFactory;
import com.book.api.factory.SubjectFactory;
import com.book.api.service.helper.impl.PdfHelperImpl;
import com.book.api.service.impl.BookServiceImpl;
import com.book.api.service.impl.SubjectServiceImpl;
import com.book.api.support.SuportTests;
import com.book.api.util.ApiUtil;
import com.book.api.validation.BookValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubjectServiceTest extends SuportTests {

    @Mock
    private SubjectRepository subjectRepository;
    @Mock
    private SubjectFactory subjectFactory;
    @InjectMocks
    private SubjectServiceImpl subjectService;

    @Test
    void shouldGetAllSubjects() {
        when(subjectRepository.findAll())
            .thenReturn(List.of(mock(SubjectEntity.class)));

        final var subjects = subjectService.getAllSubjects();
        assertNotNull(subjects);
    }

    @Test
    void shouldExceptionWhenGeallSubjects() {
        when(subjectRepository.findAll())
            .thenThrow(DatabaseException.class);

        assertThrows(DatabaseException.class,
            () -> subjectService.getAllSubjects());
    }
}
