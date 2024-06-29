package com.book.api.validation;

import com.book.api.controller.http.request.BookPersistRequest;
import com.book.api.exception.ValidationException;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class BookValidation {

    public void validateBookSave(final BookPersistRequest bookPersistRequest) {
        List<String> message = new ArrayList<>();

        if (Objects.isNull(bookPersistRequest)) {
            throw new ValidationException("É necessário enviar os dados!");
        }

        if (StringUtils.isAllBlank(bookPersistRequest.getTitle())) {
            message.add("O título é obrigatório!");
        }

        if (StringUtils.isAllBlank(bookPersistRequest.getPublishingCompany())) {
            message.add("A editora é obrigatória!");
        }

        if (ObjectUtils.isEmpty(bookPersistRequest.getEdition())) {
            message.add("A edição é obrigatória!");
        }

        if (StringUtils.isAllBlank(bookPersistRequest.getPublishYear())) {
            message.add("O ano de publicação obrigatório!");
        }

        if (!StringUtils.isAllBlank(bookPersistRequest.getPublishYear())
            && bookPersistRequest.getPublishYear().length() < 4) {
           message.add("O ano deve ter o formato de 4 dígitos!");
        }

        if (ObjectUtils.isEmpty(bookPersistRequest.getPrice())) {
            message.add("O valor é obrigatório!");
        }

        if (ObjectUtils.isEmpty(bookPersistRequest.getSubjects())) {
            message.add("É necessário ao menos um assunto!");
        }

        if (ObjectUtils.isEmpty(bookPersistRequest.getWriters())) {
            message.add("É necessário ao menos um autor!");
        }

        if (!message.isEmpty()) {
            throw new ValidationException(message.toString());
        }
    }
}
