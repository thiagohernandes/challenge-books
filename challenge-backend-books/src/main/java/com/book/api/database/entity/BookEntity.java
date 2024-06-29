package com.book.api.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "LIVRO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "TITULO")
    private String title;

    @Column(name = "EDITORA")
    private String publishingCompany;

    @Column(name = "EDICAO")
    private Integer edition;

    @Column(name = "ANO_PUBLICACAO")
    private String publishYear;

    @Column(name = "PRECO")
    private BigDecimal price;
}
