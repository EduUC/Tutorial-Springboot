package com.ccsw.Tutorial.prestamo;

import com.ccsw.Tutorial.prestamo.model.Prestamo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PrestamoRepository extends CrudRepository<Prestamo, Long>, JpaSpecificationExecutor<Prestamo> {

    /**
     * Método para recuperar un listado paginado y filtrado de {@link Prestamo}
     *
     * @param pageable pageable
     * @return {@link Page} de {@link Prestamo}
     */
    Page<Prestamo> findAll(Pageable pageable);

    /**
     * Método que comprueba si un juego ya está prestado en un rango de fechas concreto
     *
     * @param gameId ID del juego
     * @param startDate fecha de inicio
     * @param endDate fecha de fin
     * @param id ID del préstamo (opcional para exclusión)
     * @return {@link List} de {@link Prestamo}
     */
    @Query("SELECT p FROM Prestamo p WHERE p.game.id = :gameId AND p.startDate <= :endDate AND p.endDate >= :startDate AND (:id IS NULL OR p.id <> :id)")
    List<Prestamo> findOverlappingByGame(@Param("gameId") Long gameId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("id") Long id);

    /**
     * Método que recupera los préstamos de un cliente que se solapan con un rango de fechas
     *
     * @param clientId ID del cliente
     * @param startDate fecha de inicio
     * @param endDate fecha de fin
     * @param id ID del préstamo (opcional para exclusión)
     * @return {@link List} de {@link Prestamo}
     */
    @Query("SELECT p FROM Prestamo p WHERE p.client.id = :clientId AND p.startDate <= :endDate AND p.endDate >= :startDate AND (:id IS NULL OR p.id <> :id)")
    List<Prestamo> findOverlappingByClient(@Param("clientId") Long clientId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("id") Long id);
}