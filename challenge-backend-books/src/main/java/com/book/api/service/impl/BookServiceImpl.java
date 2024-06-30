package com.book.api.service.impl;

import com.book.api.constants.ApiConstants;
import com.book.api.controller.http.request.BookFilterRequest;
import com.book.api.controller.http.request.BookPersistRequest;
import com.book.api.controller.http.response.BookResponse;
import com.book.api.database.entity.BookSubjectEntity;
import com.book.api.database.entity.BookWriterEntity;
import com.book.api.database.repository.BookRepository;
import com.book.api.database.repository.BookSubjectRepository;
import com.book.api.database.repository.BookWriterRepository;
import com.book.api.exception.DatabaseException;
import com.book.api.exception.ValidationException;
import com.book.api.factory.BookFactory;
import com.book.api.service.BookService;
import com.book.api.service.helper.impl.PdfHelperImpl;
import com.book.api.type.LogMessageType;
import com.book.api.util.ApiUtil;
import com.book.api.validation.BookValidation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private static final Integer FILTER_LIMIT_REQUEST = 50;
    private final BookRepository bookRepository;
    private final BookSubjectRepository bookSubjectRepository;
    private final BookWriterRepository bookWriterRepository;
    private final BookFactory bookFactory;
    private final ApiUtil apiUtil;
    private final BookValidation bookValidation;
    private final PdfHelperImpl pdfHelper;

    public Mono<List<BookResponse>> getBooksByFilters(final BookFilterRequest bookFilterRequest) {
        if (bookFilterRequest.getSize() > FILTER_LIMIT_REQUEST) {
            throw new ValidationException("A quantidade de registros não pode exceder 50");
        }

        return Mono.just(buildListBookResponse(bookFilterRequest));
    }

    @Transactional(rollbackFor = DatabaseException.class)
    public void saveOrUpdateBook(final BookPersistRequest bookPersistRequest) {
        apiUtil.logMessage("Validando dados para persistência...", LogMessageType.INFO);

        bookValidation.validateBookSave(bookPersistRequest);

        apiUtil.logMessage("Salvando livro...", LogMessageType.INFO);

        bookPersistRequest.setTitle(bookPersistRequest
            .getTitle().toUpperCase());
        bookPersistRequest.setPublishingCompany(bookPersistRequest
            .getPublishingCompany().toUpperCase());

        final var savedBook = Optional.of(bookRepository
                .save(bookFactory
                    .requestToEntity(bookPersistRequest)))
            .orElseThrow(() -> new DatabaseException("Não foi possível salvar o livro!"));

        final var idBook = savedBook.getId();
        bookWriterRepository.deleteByIdBook(idBook);
        bookSubjectRepository.deleteByIdBook(idBook);

        Arrays.asList(bookPersistRequest.getSubjects())
            .forEach(subject -> {
                var subjectEntity = Optional.of(bookSubjectRepository
                        .save(BookSubjectEntity.builder()
                            .idBook(idBook)
                            .idSubject(subject)
                            .build()))
                    .orElseThrow(() ->
                        new DatabaseException("Não foi possível salvar os assuntos do livro!"));

                apiUtil.logMessage("Assunto salvo: "
                    .concat(subjectEntity.toString()), LogMessageType.INFO);
            });

        Arrays.asList(bookPersistRequest.getWriters())
            .forEach(writer -> {
                var writerEntity = Optional.of(bookWriterRepository
                        .save(BookWriterEntity.builder()
                            .idBook(idBook)
                            .idWriter(writer)
                            .build()))
                    .orElseThrow(() ->
                        new DatabaseException("Não foi possível salvar os autores do livro!"));

                apiUtil.logMessage("Autor salvo: "
                    .concat(writerEntity.toString()), LogMessageType.INFO);
            });

        apiUtil.logMessage("Livro " + savedBook + " salvo com sucesso!", LogMessageType.INFO);
    }

    @Transactional(rollbackFor = DatabaseException.class)
    public Integer deleteBook(final Integer idBook) {
        apiUtil.logMessage("Apagando dados de livro...", LogMessageType.INFO);

        var deletedWriter = Optional.ofNullable(bookWriterRepository.deleteByIdBook(idBook))
            .orElseThrow(() -> new DatabaseException("Não foi possível apagar os autores do livro!"));

        apiUtil.logMessage(deletedWriter + " Autore(s) apagado(s) com sucesso!" + deletedWriter, LogMessageType.INFO);

        var deletedSubject = Optional.ofNullable(bookSubjectRepository.deleteByIdBook(idBook))
            .orElseThrow(() -> new DatabaseException("Não foi possível apagar os assuntos do livro!"));

        apiUtil.logMessage(deletedSubject + "Assunto(s) apagado(s) com sucesso!", LogMessageType.INFO);

        bookRepository.deleteById(idBook);

        apiUtil.logMessage("Livro apagado com sucesso!", LogMessageType.INFO);

        return idBook;
    }

    public Mono<String> generatePdfBooks(final BookFilterRequest bookFilterRequest) {
        return Mono.just(pdfHelper
            .createPdfReport(buildListBookResponse(bookFilterRequest)));
    }

    private List<BookResponse> buildListBookResponse(final BookFilterRequest bookFilterRequest) {
        apiUtil.logMessage("Consultando livros...", LogMessageType.INFO);

        final List<BookResponse> bookResponse = new ArrayList<>();
        final Pageable pageable = PageRequest.of(bookFilterRequest.getPage(), bookFilterRequest.getSize());

        final var bookSearch = Optional.ofNullable(bookRepository
            .findByTitleLikeAndPublishingCompanyLike(
                StringUtils.isAllBlank(bookFilterRequest.getTitle())
                    ? ApiConstants.SEARCH_LIKE : ApiConstants.SEARCH_LIKE
                    .concat(bookFilterRequest.getTitle().toUpperCase())
                    .concat(ApiConstants.SEARCH_LIKE),
                StringUtils.isAllBlank(bookFilterRequest.getPublishingCompany())
                    ? ApiConstants.SEARCH_LIKE : ApiConstants.SEARCH_LIKE
                    .concat(bookFilterRequest.getPublishingCompany().toUpperCase())
                    .concat(ApiConstants.SEARCH_LIKE),
                pageable)).orElseThrow(() -> new DatabaseException("Não foi possível retornar a busca de livros!"));

        bookSearch.getContent().forEach(search -> {
            final var bookSubjectDtos = Optional.ofNullable(bookSubjectRepository
                    .getBookSubjectsByIdBook(search.getId()))
                .orElseThrow(() -> new DatabaseException("Não foi possível retornar a busca de assuntos dos livros!"));

            final var bookWriterDtos = Optional.ofNullable(bookWriterRepository
                    .getBookWritersByIdBook(search.getId()))
                .orElseThrow(() -> new DatabaseException("Não foi possível retornar a busca de autores dos livros!"));

            bookResponse
                .add(bookFactory
                    .entityToResponse(search, bookSubjectDtos, bookWriterDtos));
        });

        apiUtil.logMessage("Consulta de livros finalizada!", LogMessageType.INFO);

        return bookResponse;
    }
}
