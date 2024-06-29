package com.book.api.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "LIVRO_AUTOR")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookWriterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "ID_LIVRO")
    private Integer idBook;

    @Column(name = "ID_AUTOR")
    private Integer idWriter;
}
