package es.iesjuanbosco.biblioteca_api.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder


public class LibroFormDTO {

        //VAlidaciones: reglas que debe de cumplir el dato

        @NotBlank(message ="El titulo es obligatorio")
        @Size(min=3, max=200, message="El titulo debe tener entre 3 y 200 caracteres")
        private String titulo;

        @NotBlank(message="El autor es obligatorio")
        @Size(min=3, max=100, message = "El autor debe de tener entre 3 y 100 caracteres")
        private String autor;

        @NotBlank(message="El isbn es obligatorio")
        @Pattern(regexp= "\\d{13}", message="El isbn debe de contener 13 dígitos")
        private String isbn;

        @NotNull(message="El número de páginas es obligatorio")
        @Min(value=1, message="Debe de tener al menos 1 página")
        private int numPaginas;

        @NotNull(message = "La fecha de publicación es obligatoria")
        @PastOrPresent(message = "La fecha no puede ser futura")
        private LocalDate fechaPublicacion;

        private Boolean disponible = true;

}
