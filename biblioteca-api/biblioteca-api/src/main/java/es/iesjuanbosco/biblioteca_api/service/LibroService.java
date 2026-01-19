package es.iesjuanbosco.biblioteca_api.service;


import es.iesjuanbosco.biblioteca_api.dto.request.LibroFormDTO;
import es.iesjuanbosco.biblioteca_api.dto.response.LibroDetalleDTO;
import es.iesjuanbosco.biblioteca_api.dto.response.LibroListaDTO;
import es.iesjuanbosco.biblioteca_api.entity.Libro;
import es.iesjuanbosco.biblioteca_api.mapper.LibroMapper;
import es.iesjuanbosco.biblioteca_api.repository.LibroRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
 * Service para la lógica de negocio de Libros
 *
 * @Service: Le dice a Spring que esta es una capa de servicio
 * @RequiredArgsConstructor: Lombok crea automáticamente un constructor
 *                           con todas las dependencias "final"
 * @Slf4j: Lombok crea automáticamente un logger (para imprimir mensajes)
 * @Transactional(readOnly = true): Todas las operaciones son de solo lectura
 *                                   (optimización para consultas)
 */

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)

public class LibroService {
/* ========== DEPENDENCIAS ==========
// Spring las inyecta automáticamente gracias a @RequiredArgsConstructor
*/
private final LibroRepository libroRepository; //Para acceder a la BBDD
private final LibroMapper libroMapper;//Para convertir entity a DTO
    // ========== LISTAR TODOS ==========
    /*
     * Obtiene todos los libros (vista resumida)
     *
     * 1. Llama al repository para obtener List<Libro>
     * 2. Usa el mapper para convertir a List<LibroListaDTO>
     * 3. Devuelve la lista de DTOs
     */

    public List<LibroListaDTO> listarTodos(){
        log.info("Listando todos los libros");  // Mensaje en consola

        // 1. Obtener todas las entidades de la BD
        List<Libro> libros = libroRepository.findAll();

        // 2. Convertir lista de entidades a lista de DTOs
        return libroMapper.toListaDTOList(libros);  // ✨ Magia de MapStruct
    }
    // ========== BUSCAR POR ID ==========
    /**
     * Busca un libro específico por su ID
     *
     * @param id El ID del libro a buscar
     * @return LibroDetalleDTO con toda la información
     * @throws RuntimeException si no se encuentra el libro
     */

    public LibroDetalleDTO buscarPorId(Long id) {
        log.info("Buscando libro con ID: {}", id);

        // 1. Buscar en la BD
        // orElseThrow() lanza una excepción si no existe
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Libro no encontrado con ID: " + id
                ));

        // 2. Convertir Entity a DTO
        return libroMapper.toDetalleDTO(libro);  // ✨ Magia de MapStruct
    }

// ========== CREAR ==========
/*
     * Crea un nuevo libro
     *
     * @param dto Datos del libro a crear
     * @return LibroDetalleDTO del libro creado
     * @throws IllegalArgumentException si el ISBN ya existe
*/
    @Transactional  // ← Esta operación ESCRIBE en la BD
    public LibroDetalleDTO crear(LibroFormDTO dto) {
        log.info("Creando nuevo libro: {}", dto.getTitulo());

        // 1. VALIDACIÓN: Verificar que no exista el ISBN
        if (libroRepository.existsByIsbn(dto.getIsbn())) {
            throw new IllegalArgumentException(
                    "Ya existe un libro con el ISBN: " + dto.getIsbn()
            );
        }

        // 2. Convertir DTO a Entity
        Libro libro = libroMapper.toEntity(dto);

        // 3. Guardar en la BD
        Libro guardado = libroRepository.save(libro);

        // 4. Convertir Entity guardada a DTO y devolverla
        return libroMapper.toDetalleDTO(guardado);
    }

    // ========== ACTUALIZAR ==========
    /*
     * Actualiza un libro existente
     *
     * @param id El ID del libro a actualizar
     * @param dto Nuevos datos del libro
     * @return LibroDetalleDTO del libro actualizado
     */
    @Transactional  // ← Esta operación ESCRIBE en la BD
    public LibroDetalleDTO actualizar(Long id, LibroFormDTO dto) {
        log.info("Actualizando libro ID: {}", id);

        // 1. Verificar que el libro existe
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Libro no encontrado con ID: " + id
                ));

        // 2. VALIDACIÓN: Si cambió el ISBN, verificar que no esté duplicado
        if (!libro.getIsbn().equals(dto.getIsbn())
                && libroRepository.existsByIsbn(dto.getIsbn())) {
            throw new IllegalArgumentException(
                    "Ya existe otro libro con el ISBN: " + dto.getIsbn()
            );
        }

        // 3. Actualizar los campos del libro (mantiene el ID original)
        libroMapper.updateEntityFromDTO(dto, libro);  // ✨ Magia de MapStruct

        // 4. Guardar cambios
        Libro actualizado = libroRepository.save(libro);

        // 5. Devolver DTO actualizado
        return libroMapper.toDetalleDTO(actualizado);
    }

    // ========== ELIMINAR ==========
    /*
     * Elimina un libro
     *
     * @param id El ID del libro a eliminar
     */
    @Transactional  // ← Esta operación ESCRIBE en la BD
    public void eliminar(Long id) {
        log.info("Eliminando libro ID: {}", id);

        // 1. Verificar que existe
        if (!libroRepository.existsById(id)) {
            throw new RuntimeException(
                    "Libro no encontrado con ID: " + id
            );
        }

        // 2. Eliminar
        libroRepository.deleteById(id);
    }
}







