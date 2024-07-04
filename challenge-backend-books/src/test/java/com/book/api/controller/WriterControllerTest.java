package com.book.api.controller;

import com.book.api.service.impl.WriterServiceImpl;
import com.book.api.support.SuportTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class WriterControllerTest extends SuportTests {

    private static final String PATH = "/writer";

    @Mock
    private WriterServiceImpl writerService;

    @InjectMocks
    private WriterController writerController;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders
            .standaloneSetup(writerController).build();
    }

    @Test
    void shouldGetWriters() throws Exception {
        mockMvc.perform(get(PATH))
            .andExpect(status().isOk())
            .andDo(print());
    }
}
