package com.book.api.database.repository;

import com.book.api.database.entity.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Integer> {

    Page<BookEntity> findByTitleLikeAndPublishingCompanyLike(
        String title,
        String publishingCompany,
        Pageable page);
}
