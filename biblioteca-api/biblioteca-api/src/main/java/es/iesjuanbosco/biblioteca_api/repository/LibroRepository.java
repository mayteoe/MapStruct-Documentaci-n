package es.iesjuanbosco.biblioteca_api.repository;

import es.iesjuanbosco.biblioteca_api.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository para la entidad Libro
 *
 * @Repository: Le dice a Spring que esta interfaz maneja datos
 *
 * JpaRepository<Libro, Long>:
 * - Libro: La entidad que manejamos
 * - Long: El tipo del ID
 *
 * Spring genera AUTOMÁTICAMENTE estos métodos sin que escribas código:
 * - findAll() → Buscar todos los libros
 * - findById(id) → Buscar un libro por ID
 * - save(libro) → Guardar o actualizar un libro
 * - deleteById(id) → Eliminar un libro
 * - existsById(id) → Verificar si existe un libro
 * - count() → Contar cuántos libros hay
 * - y muchos más...
 */
@Repository
public interface LibroRepository extends JpaRepository<Libro, Long>{
// Aquí puedes añadir métodos personalizados si los necesitas
    // Por ejemplo:

    /**
     * Spring genera automáticamente la consulta SQL:
     * SELECT * FROM libros WHERE isbn = ?
     */
    boolean existsByIsbn(String isbn);


}
