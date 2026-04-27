package com.ccsw.Tutorial.prestamo;

import com.ccsw.Tutorial.client.ClientRepository;
import com.ccsw.Tutorial.client.model.Client;
import com.ccsw.Tutorial.common.criteria.SearchCriteria;
import com.ccsw.Tutorial.game.GameRepository;
import com.ccsw.Tutorial.game.model.Game;
import com.ccsw.Tutorial.prestamo.model.Prestamo;
import com.ccsw.Tutorial.prestamo.model.PrestamoDto;
import com.ccsw.Tutorial.prestamo.model.PrestamoSearchDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PrestamoServiceImpl implements PrestamoService {

    @Autowired
    PrestamoRepository prestamoRepository;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    ClientRepository clientRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Prestamo> findPage(PrestamoSearchDto dto) {

        List<Specification<Prestamo>> specs = new ArrayList<>();

        if (dto.getTitle() != null && !dto.getTitle().isEmpty()) {
            specs.add(new PrestamoSpecification(new SearchCriteria("game.title", ":", dto.getTitle())));
        }

        if (dto.getClientName() != null && !dto.getClientName().isEmpty()) {
            specs.add(new PrestamoSpecification(new SearchCriteria("client.name", ":", dto.getClientName())));
        }
        if (dto.getSearchDate() != null) {
            specs.add((root, query, criteriaBuilder) -> criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("startDate"), dto.getSearchDate()), criteriaBuilder.greaterThanOrEqualTo(root.get("endDate"), dto.getSearchDate())));
        }

        Specification<Prestamo> spec = Specification.allOf(specs);

        return this.prestamoRepository.findAll(spec, dto.getPageable().getPageable());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Long id, PrestamoDto dto) {

        LocalDate start = dto.getStartDate();
        LocalDate end = dto.getEndDate();

        if (end.isBefore(start)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fecha de fin no puede ser anterior a la fecha de inicio.");
        }

        long daysBetween = ChronoUnit.DAYS.between(start, end);
        if (daysBetween > 14) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La duración máxima del préstamo es de 14 días.");
        }

        List<Prestamo> gamePrestado = this.prestamoRepository.findOverlappingByGame(dto.getGame().getId(), start, end, id);
        if (!gamePrestado.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El juego ya está prestado a otro cliente en esas fechas.");
        }

        List<Prestamo> clientPrestado = this.prestamoRepository.findOverlappingByClient(dto.getClient().getId(), start, end, id);
        if (clientPrestado.size() >= 2) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El cliente ya tiene 2 juegos prestados en esas fechas.");
        }

        Prestamo prestamo;

        if (id == null) {
            prestamo = new Prestamo();
        } else {
            prestamo = this.prestamoRepository.findById(id).orElse(null);
        }

        Game game = this.gameRepository.findById(dto.getGame().getId()).orElse(null);
        Client client = this.clientRepository.findById(dto.getClient().getId()).orElse(null);

        prestamo.setGame(game);
        prestamo.setClient(client);
        prestamo.setStartDate(dto.getStartDate());
        prestamo.setEndDate(dto.getEndDate());

        this.prestamoRepository.save(prestamo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) throws Exception {
        if (this.prestamoRepository.findById(id).orElse(null) == null) {
            throw new Exception("Not exists");
        }
        this.prestamoRepository.deleteById(id);
    }

}