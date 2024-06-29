package com.book.api.database.repository;

import com.book.api.database.entity.BookWriterEntity;
import com.book.api.domain.BookSubjectDto;
import com.book.api.domain.BookWriterDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookWriterRepository extends JpaRepository<BookWriterEntity, Integer> {

    @Query(value = " SELECT new com.book.api.domain.BookWriterDto(" +
        " bw.id," +
        " bw.idBook," +
        " bw.idWriter," +
        " we.name" +
        " ) " +
        " FROM BookWriterEntity bw " +
        " INNER JOIN WriterEntity we ON we.id = bw.idWriter " +
        " WHERE bw.idBook = :idBook" +
        " ORDER BY we.name ")
    List<BookWriterDto> getBookWritersByIdBook(Integer idBook);

    Integer deleteByIdBook(Integer idBook);
}
