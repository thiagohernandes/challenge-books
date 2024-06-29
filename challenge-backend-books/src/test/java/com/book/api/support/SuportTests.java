package com.book.api.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jeasy.random.EasyRandom;
import org.springframework.test.web.servlet.MockMvc;

public class SuportTests {

    public final EasyRandom generator = new EasyRandom();
    public final ObjectMapper objectMapper = new ObjectMapper();
    public MockMvc mockMvc;
}
