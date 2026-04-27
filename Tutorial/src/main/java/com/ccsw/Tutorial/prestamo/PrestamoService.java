package com.ccsw.Tutorial.prestamo;

import com.ccsw.Tutorial.prestamo.model.Prestamo;
import com.ccsw.Tutorial.prestamo.model.PrestamoDto;
import com.ccsw.Tutorial.prestamo.model.PrestamoSearchDto;
import org.springframework.data.domain.Page;

public interface PrestamoService {
    /**
     * Método para recuperar un listado paginado y filtrado de {@link Prestamo}
     *
     * @param dto dto de búsqueda
     * @return {@link Page} de {@link Prestamo}
     */
    Page<Prestamo> findPage(PrestamoSearchDto dto);

    /**
     * Guarda o actualiza un préstamo
     * @param id PK de la entidad (null para crear)
     * @param dto Datos del préstamo
     */
    void save(Long id, PrestamoDto dto);

    /**
     * Método para borrar un {@link Prestamo}
     *
     * @param id PK de la entidad
     */
    void delete(Long id) throws Exception;
}
