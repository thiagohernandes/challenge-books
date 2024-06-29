package com.book.api.database.repository;

import com.book.api.database.entity.BookSubjectEntity;
import com.book.api.domain.BookSubjectDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookSubjectRepository extends JpaRepository<BookSubjectEntity, Integer> {

    @Query(value = " SELECT new com.book.api.domain.BookSubjectDto(" +
        " be.id," +
        " be.idBook," +
        " be.idSubject," +
        " se.description" +
        " ) " +
        " FROM BookSubjectEntity be " +
        " INNER JOIN SubjectEntity se ON se.id = be.idSubject " +
        " WHERE be.idBook = :idBook" +
        " ORDER BY se.description ")
    List<BookSubjectDto> getBookSubjectsByIdBook(Integer idBook);

    Integer deleteByIdBook(Integer idBook);
}
