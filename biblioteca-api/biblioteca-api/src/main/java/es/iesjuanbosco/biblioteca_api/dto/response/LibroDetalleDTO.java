package es.iesjuanbosco.biblioteca_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class LibroDetalleDTO {
    private Long id;
    private String titulo;
    private String autor;
    private String isbn;
    private Integer numPaginas;
    private String fechaPublicacion;  // ← Fíjate: es String, no LocalDate
    private Boolean disponible;
}
