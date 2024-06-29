package com.book.api.controller;

import com.book.api.controller.http.request.BookPersistRequest;
import com.book.api.service.impl.BookServiceImpl;
import com.book.api.support.SuportTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class BookControllerTest extends SuportTests {

    @Mock
    private BookServiceImpl bookService;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders
            .standaloneSetup(bookController).build();
    }

    @Test
    void shouldGetBooks() throws Exception {
        mockMvc.perform(get("/book"))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    void shouldSaveBook() throws Exception {
        BookPersistRequest request = generator.nextObject(BookPersistRequest.class);

        mockMvc.perform(post("/book")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated());
    }

    @Test
    void shouldDeleteBook() throws Exception {
        mockMvc.perform(delete("/book/1"))
            .andExpect(status().isOk())
            .andDo(print());
    }
}
