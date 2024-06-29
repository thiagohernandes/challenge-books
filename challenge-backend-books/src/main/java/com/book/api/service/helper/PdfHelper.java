package com.book.api.service.helper;

import com.book.api.controller.http.response.BookResponse;

import java.util.List;

public interface PdfHelper {

    String createPdfReport(List<BookResponse> bookResponseList);
}
