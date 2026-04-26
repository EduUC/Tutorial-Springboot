package com.ccsw.Tutorial.client;

import com.ccsw.Tutorial.client.model.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Long> {
    /**
     * Busca un cliente por su nombre exacto
     * @param name nombre del cliente
     * @return El cliente encontrado o null si no existe
     */
    Client findByName(String name);
}
