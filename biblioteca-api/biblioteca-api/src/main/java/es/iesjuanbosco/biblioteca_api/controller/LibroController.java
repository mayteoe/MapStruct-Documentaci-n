package es.iesjuanbosco.biblioteca_api.controller;

/*
 * Controller REST para gestionar libros
 *
 * @RestController: Combina @Controller + @ResponseBody
 *                  Todas las respuestas se convierten automáticamente a JSON
 * @RequestMapping: Prefijo de todas las rutas (/api/libros)
 * @RequiredArgsConstructor: Lombok inyecta las dependencias automáticamente
 */

import es.iesjuanbosco.biblioteca_api.dto.request.LibroFormDTO;
import es.iesjuanbosco.biblioteca_api.dto.response.LibroDetalleDTO;
import es.iesjuanbosco.biblioteca_api.dto.response.LibroListaDTO;
import es.iesjuanbosco.biblioteca_api.service.LibroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.List;

@RestController
@RequestMapping("api/libros") //Todas las rutas comienzas por api/libros
@RequiredArgsConstructor

public class LibroController {

    // ========== DEPENDENCIAS ==========
    // Solo necesitamos el Service (NO el Repository ni el Mapper)
    private final LibroService libroService;

    // ========== GET /api/libros ==========
    /*
     *==== Listar todos los libros====
     *
     * Ejemplo de petición:
     * GET http://localhost:8080/api/libros
     *
     * Respuesta: JSON con lista de libros
     */
    @GetMapping
    public ResponseEntity<List<LibroListaDTO>> listarTodos() {
        List<LibroListaDTO> libros = libroService.listarTodos();
        return ResponseEntity.ok(libros);  // Devuelve código 200 OK
    }

//===GET /api/libros/{id}
/*
 * Obtener un libro por su ID
 *
 * Ejemplo de petición:
 * GET http://localhost:8080/api/libros/1
 *
 * @param id El ID del libro (viene en la URL)
*/
    @GetMapping("/{id}")

    public ResponseEntity<LibroDetalleDTO> obtenerPorId(@PathVariable Long id){
        LibroDetalleDTO libroDTO=libroService.buscarPorId(id);
        return ResponseEntity.ok(libroDTO);
    }

// ========== POST /api/libros ==========
/*
 * Crear un nuevo libro
 *
 * Ejemplo de petición:
 * POST http://localhost:8080/api/libros
 * Body (JSON):
 * {
 *   "titulo": "Clean Code",
 *   "autor": "Robert C. Martin",
 *   "isbn": "9780132350884",
 *   "numPaginas": 464,
 *   "fechaPublicacion": "2008-08-01",
 *   "disponible": true
 * }
 *
 * @param dto Datos del libro (viene en el body de la petición)
 * @Valid: Activa las validaciones del DTO
 */

 @PostMapping

public ResponseEntity<LibroDetalleDTO> crear(@RequestBody @Valid  LibroFormDTO dto){

     LibroDetalleDTO creado = libroService.crear(dto);
     return ResponseEntity
             .status(HttpStatus.CREATED)
             .body(creado);

 }
    // ========== PUT /api/libros/{id} ==========
    /*
     * Actualizar un libro existente
     *
     * Ejemplo de petición:
     * PUT http://localhost:8080/api/libros/1
     * Body (JSON): (igual que POST)
     *
     * @param id El ID del libro a actualizar
     * @param dto Nuevos datos del libro
     */
   @PostMapping("/{id}")
    public ResponseEntity<LibroDetalleDTO> actualizar( @PathVariable Long id,
                                                       @RequestBody @Valid LibroFormDTO dto ){
    LibroDetalleDTO actualizado= libroService.actualizar(id,dto);
    return ResponseEntity.ok(actualizado);
   }
// ========== DELETE /api/libros/{id} ==========
    /*
     * Eliminar un libro
     *
     * Ejemplo de petición:
     * DELETE http://localhost:8080/api/libros/1
     *
     * @param id El ID del libro a eliminar
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
         libroService.eliminar(id);
         return ResponseEntity.noContent().build(); // Devuelve código 204 NO CONTENT

    }

}
