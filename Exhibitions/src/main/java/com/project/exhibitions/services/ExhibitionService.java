package com.project.exhibitions.services;

import com.project.exhibitions.entity.Exhibition;
import com.project.exhibitions.repository.ExhibitionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor

@Service
public class ExhibitionService {
    private ExhibitionRepository exhibitionRepository;

    public List<Exhibition> allExhibitions() {
        return exhibitionRepository.findAll();
    }

    public void saveNewExhibition(Exhibition exhibition) {
        exhibitionRepository.save(exhibition);
    }

    public Optional<Exhibition> findById(Integer id) {
        return exhibitionRepository.findById(id);
    }
}
