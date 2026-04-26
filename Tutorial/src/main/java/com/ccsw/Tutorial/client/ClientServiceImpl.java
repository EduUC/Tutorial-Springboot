package com.ccsw.Tutorial.client;

import com.ccsw.Tutorial.client.model.Client;
import com.ccsw.Tutorial.client.model.ClientDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Client get(Long id) {

        return this.clientRepository.findById(id).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Client> findAll() {

        return (List<Client>) this.clientRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Long id, ClientDto dto) {

        Client clientWithSameName = this.clientRepository.findByName(dto.getName());

        if (clientWithSameName != null && !clientWithSameName.getId().equals(id)) {
            throw new RuntimeException("El nombre ya existe");
        }

        Client Client;

        if (id == null) {
            Client = new Client();
        } else {
            Client = this.get(id);
        }

        Client.setName(dto.getName());

        this.clientRepository.save(Client);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) throws Exception {

        if (this.get(id) == null) {
            throw new Exception("Not exists");
        }

        this.clientRepository.deleteById(id);
    }

}
