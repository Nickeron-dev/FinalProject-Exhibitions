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

@AllArgsConstructor

@Service
public class ExhibitionService {
    private ExhibitionRepositoryImpl exhibitionRepository;
    private TicketService ticketService;

    public List<Exhibition> allExhibitions() {
        return exhibitionRepository.findAll();
    }

    public Exhibition saveNewExhibition(Exhibition exhibition) {
        return exhibitionRepository.save(exhibition);
    }

    public Optional<Exhibition> findById(Integer id) {
        return exhibitionRepository.findById(id);
    }

    public Page<Exhibition> findStartingStartDate(LocalDate date, Pageable pageable) {
        return exhibitionRepository.findAllByStartDateAfter(date, pageable);
    }

    public void cancelExhibitionById(@Param("id") Integer id) {
        exhibitionRepository.cancelExhibitionById(id);
    }

    public void planExhibitionById(@Param("id") Integer id) {
        exhibitionRepository.planExhibitionById(id);
    }

    public List<ExhibitionWithVisitorsDTO> statistics(List<Exhibition> exhibitions) {
        List<ExhibitionWithVisitorsDTO> statistics = new ArrayList<>(exhibitions.size());
        exhibitions.stream().forEach(element -> {
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
