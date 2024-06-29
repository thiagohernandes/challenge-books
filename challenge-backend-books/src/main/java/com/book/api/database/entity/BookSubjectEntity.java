package com.book.api.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "LIVRO_ASSUNTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookSubjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "ID_LIVRO")
    private Integer idBook;

    @Column(name = "ID_ASSUNTO")
    private Integer idSubject;
}
