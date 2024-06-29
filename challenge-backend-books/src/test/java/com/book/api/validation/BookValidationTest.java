package com.book.api.validation;

import com.book.api.controller.http.request.BookPersistRequest;
import com.book.api.exception.ValidationException;
import com.book.api.support.SuportTests;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;

@ExtendWith(MockitoExtension.class)
class BookValidationTest extends SuportTests {

    private final BookPersistRequest bookPersistRequest = generator.nextObject(BookPersistRequest.class);

    @Mock
    private BookValidation bookValidation;

    @Test
    void shouldPassValidation() {
        doCallRealMethod().when(bookValidation).validateBookSave(any());

        bookValidation.validateBookSave(bookPersistRequest);
    }

    @Test
    void shouldNotPassValidation() {
        doCallRealMethod().when(bookValidation).validateBookSave(null);

        assertThrows(ValidationException.class,
            () -> bookValidation.validateBookSave(null));
    }

    @Test
    void shouldNotPassEditionFieldValidation() {
        bookPersistRequest.setEdition(null);
        doCallRealMethod().when(bookValidation).validateBookSave(bookPersistRequest);

        assertThrows(ValidationException.class,
            () -> bookValidation.validateBookSave(bookPersistRequest));
    }

    @Test
    void shouldNotPassPriceFieldValidation() {
        bookPersistRequest.setPrice(null);
        doCallRealMethod().when(bookValidation).validateBookSave(bookPersistRequest);

        assertThrows(ValidationException.class,
            () -> bookValidation.validateBookSave(bookPersistRequest));
    }

    @Test
    void shouldNotPassTitleFieldValidation() {
        bookPersistRequest.setTitle(null);
        doCallRealMethod().when(bookValidation).validateBookSave(bookPersistRequest);

        assertThrows(ValidationException.class,
            () -> bookValidation.validateBookSave(bookPersistRequest));
    }

    @Test
    void shouldNotPassPublishingCompanyFieldValidation() {
        bookPersistRequest.setPublishingCompany(null);
        doCallRealMethod().when(bookValidation).validateBookSave(bookPersistRequest);

        assertThrows(ValidationException.class,
            () -> bookValidation.validateBookSave(bookPersistRequest));
    }

    @Test
    void shouldNotPassPublishYearFieldValidation() {
        bookPersistRequest.setPublishYear(null);
        doCallRealMethod().when(bookValidation).validateBookSave(bookPersistRequest);

        assertThrows(ValidationException.class,
            () -> bookValidation.validateBookSave(bookPersistRequest));
    }

    @Test
    void shouldNotPassPublishYearInvalidFieldValidation() {
        bookPersistRequest.setPublishYear("20");
        doCallRealMethod().when(bookValidation).validateBookSave(bookPersistRequest);

        assertThrows(ValidationException.class,
            () -> bookValidation.validateBookSave(bookPersistRequest));
    }

    @Test
    void shouldNotPassSubjectsFieldValidation() {
        bookPersistRequest.setSubjects(new Integer[] {});
        doCallRealMethod().when(bookValidation).validateBookSave(bookPersistRequest);

        assertThrows(ValidationException.class,
            () -> bookValidation.validateBookSave(bookPersistRequest));
    }

    @Test
    void shouldNotPassWritersFieldValidation() {
        bookPersistRequest.setWriters(new Integer[] {});
        doCallRealMethod().when(bookValidation).validateBookSave(bookPersistRequest);

        assertThrows(ValidationException.class,
            () -> bookValidation.validateBookSave(bookPersistRequest));
    }
}
