package es.iesjuanbosco.biblioteca_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class LibroListaDTO {
    private Long id;
    private String titulo;
    private String autor;
    private Boolean disponible;
}
