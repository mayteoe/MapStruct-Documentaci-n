package es.iesjuanbosco.biblioteca_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name="LIBROS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(nullable=false, length = 100)
    private String autor;

    @Column(unique = true, nullable = false,length = 13)
    private String isbn;

    @Column(name="num_paginas")
    private Integer numPaginas;

    @Column(name="fecha_publicacion")
    private LocalDate fechaPublicacion;

    @Column(nullable = false)
    private Boolean disponible=true;



}
