package es.iesjuanbosco.biblioteca_api.mapper;

import es.iesjuanbosco.biblioteca_api.dto.request.LibroFormDTO;
import es.iesjuanbosco.biblioteca_api.dto.response.LibroDetalleDTO;
import es.iesjuanbosco.biblioteca_api.dto.response.LibroListaDTO;
import es.iesjuanbosco.biblioteca_api.entity.Libro;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;


//***Esta es la antoación MáS IMPORTANTE
@Mapper(componentModel="spring") //Le dice a spring que esto es un Mapper

public interface LibroMapper {
 /**
  * Convierte un Libro a LibroListaDTO
  * MapStruct copia automáticamente los campos con el mismo nombre
  **/

    LibroListaDTO toListaDTO(Libro libro);


    /**
     * Convierte una lista de Libro a lista de LibroListaDTO
     * MapStruct hace el bucle automáticamente
     */
    List<LibroListaDTO> toListaDTOList(List<Libro> libros);

    /*=================Para Detalles==========
    * Convierte un Libro a LibroDetalleDTO
    * @Mapping le dice a MapStruct cómo convertir los campos específicos
    * fechapublicación es LocalDate en Libro
    * pero String en LibroDetalleDTO
    * Le decimos que use el formato "dd/mm/yyyy"
     */

    @Mapping(source="fechaPublicacion", target="fechaPublicacion", dateFormat ="dd/MM/yyyy")

    LibroDetalleDTO toDetalleDTO(Libro libro);

    /*===========PARA CREAR*===============
    * Convierte LibroFormDTO a Libro
    * para crear nuevos libros
    * Utilizando @Mapping con ignore =true le indicamos
    * que no mapee ese campo
    * Queremos que el id lo genere automáticamente la bbdd
     */

    @Mapping(target = "id", ignore = true)
    Libro toEntity(LibroFormDTO dto);

    // ========== PARA ACTUALIZAR ==========
    /*
     * Actualiza un Libro existente con datos de LibroFormDTO
     *
     * @MappingTarget: Este parámetro es el objeto que se va a MODIFICAR
     * void: No devuelve nada, modifica el objeto directamente
     */

    @Mapping(target = "id", ignore=true)
    void updateEntityFromDTO(LibroFormDTO libroFormDTO, @MappingTarget Libro entity);


}
