package com.project.exhibitions.services;

import com.project.exhibitions.dto.ExhibitionWithVisitorsDTO;
import com.project.exhibitions.entity.Exhibition;
import com.project.exhibitions.repository.ExhibitionRepositoryImpl;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Illia Koshkin
 */
@AllArgsConstructor

@Service
public class ExhibitionService {
    private final ExhibitionRepositoryImpl exhibitionRepository;
    private final TicketService ticketService;

    /**
     * This method finds all exhibitions
     * @return util.List of Exhibition
     */
    public List<Exhibition> allExhibitions() {
        return exhibitionRepository.findAll();
    }

    /**
     * This method creates new record of a new Exhibition in database
     * @param exhibition Exhibition object
     * @return Same Exhibition
     */
    public Exhibition saveNewExhibition(Exhibition exhibition) {
        return exhibitionRepository.save(exhibition);
    }

    /**
     * This method find Exhibition by id
     * @param id Id of the Exhibition
     * @return Optional of the Exhibition
     */
    public Optional<Exhibition> findById(Integer id) {
        return exhibitionRepository.findById(id);
    }

    /**
     * This method finds all exhibitions after given date
     * @param date Date when Exhibition start
     * @param pageable Exhibitions in pages
     * @return Page with found exhibitions
     */
    public Page<Exhibition> findStartingStartDate(LocalDate date, Pageable pageable) {
        return exhibitionRepository.findAllByStartDateAfter(date, pageable);
    }

    /**
     * This method cancels exhibition by id
     * @param id Of the Exhibition
     */
    public void cancelExhibitionById(@Param("id") Integer id) {
        exhibitionRepository.cancelExhibitionById(id);
    }

    /**
     * This method plans exhibition by id
     * @param id Of the Exhibition
     */
    public void planExhibitionById(@Param("id") Integer id) {
        exhibitionRepository.planExhibitionById(id);
    }

    /**
     * This method fills statistic page
     * @param exhibitions List of all exhibitions
     * @return util.List of ExhibitionWithVisitorDTO
     */
    public List<ExhibitionWithVisitorsDTO> statistics(List<Exhibition> exhibitions) {
        List<ExhibitionWithVisitorsDTO> statistics = new ArrayList<>(exhibitions.size());
        exhibitions.forEach(element -> {
            statistics.add(ExhibitionWithVisitorsDTO.builder()
                    .id(element.getId())
                    .startDate(element.getStartDate())
                    .endDate(element.getEndDate())
                    .topic(element.getTopic())
                    .state(element.getState())
                    .price(element.getPrice())
                    .visitors((int) ticketService.countByExhibitionId(element.getId()))
                    .build());
        });
        return statistics;
    }
}
