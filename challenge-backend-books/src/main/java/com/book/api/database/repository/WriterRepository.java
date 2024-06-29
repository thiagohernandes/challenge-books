package com.book.api.database.repository;

import com.book.api.database.entity.WriterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WriterRepository extends JpaRepository<WriterEntity, Integer> {
}
