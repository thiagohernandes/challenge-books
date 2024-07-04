package com.book.api.service;

import com.book.api.controller.http.response.WriterResponse;

import java.util.List;

public interface WriterService {

    List<WriterResponse> getAllWriters();
}
