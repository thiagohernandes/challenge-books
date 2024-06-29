package com.book.api.service.helper.impl;

import com.book.api.controller.http.response.BookResponse;
import com.book.api.controller.http.response.BookSubjectResponse;
import com.book.api.controller.http.response.BookWriterResponse;
import com.book.api.exception.GeneratePdfException;
import com.book.api.service.helper.PdfHelper;
import com.book.api.type.LogMessageType;
import com.book.api.util.ApiUtil;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PdfHelperImpl implements PdfHelper {

    private static final String CURRENCY = "R$ ";
    private final ApiUtil apiUtil;

    public String createPdfReport(List<BookResponse> bookResponseList) {
        apiUtil.logMessage("Gerando PDF de livros...", LogMessageType.INFO);

        Document document = new Document();
        ByteArrayOutputStream streamOutput = new ByteArrayOutputStream();
        
        try {
            PdfWriter.getInstance(document, streamOutput);
            document.open();
            apiUtil.logMessage("Montando estrutura...", LogMessageType.INFO);

            PdfPTable table = new PdfPTable(5);
            addTableHeader(table);
            addRows(table, bookResponseList);
            document.add(table);

            apiUtil.logMessage("PDF pronto!", LogMessageType.INFO);
            document.close();

            return Base64.getEncoder()
                .encodeToString(streamOutput.toByteArray());
        } catch (DocumentException e) {
            throw new GeneratePdfException("Não foi possível gerar o PDF de livros! "
                . concat(e.getMessage()));
        }
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("Título", "Editora", "Preço", "Assunto", "Escritores")
            .forEach(columnTitle -> {
                PdfPCell header = new PdfPCell();
                header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                header.setBorderWidth(2);
                header.setPhrase(new Phrase(columnTitle, FontFactory.getFont("Arial", 8)));
                table.addCell(header);
        });

        apiUtil.logMessage("Colunas adicionadas!", LogMessageType.INFO);
    }

    private void addRows(PdfPTable table, List<BookResponse> bookResponseList) {
        bookResponseList.forEach(bookResponse -> {
            table.addCell(makePhrase(bookResponse.getTitle()));
            table.addCell(makePhrase(bookResponse.getPublishingCompany()));
            table.addCell(makePhrase(CURRENCY.concat(bookResponse.getPrice().toString())));
            table.addCell(makePhrase(handleSubjects(bookResponse.getSubjects())));
            table.addCell(makePhrase(handleWriters(bookResponse.getWriters())));
        });

        apiUtil.logMessage("Linhas adicionadas!", LogMessageType.INFO);
    }

    private Phrase makePhrase(final String text) {
        return new Phrase(text,
            FontFactory.getFont("Arial", 5));
    }

    private String handleSubjects(List<BookSubjectResponse> bookSubjectResponseList) {
        return bookSubjectResponseList.stream().map(BookSubjectResponse::getSubjectDescription)
            .collect(Collectors.joining(", "));
    }

    private String handleWriters(List<BookWriterResponse> bookWriterResponseList) {
        return bookWriterResponseList.stream().map(BookWriterResponse::getWriterName)
            .collect(Collectors.joining(", "));
    }
}
