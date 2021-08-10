package com.project.exhibitions.repository;

import com.project.exhibitions.entity.Exhibition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExhibitionRepository extends JpaRepository<Exhibition, Integer> {

}
