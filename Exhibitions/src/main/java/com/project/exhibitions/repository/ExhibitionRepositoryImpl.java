package com.project.exhibitions.repository;

import com.project.exhibitions.entity.Exhibition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExhibitionRepositoryImpl extends CrudRepository<Exhibition, Integer> {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE exhibitions SET state = 'CANCELED' WHERE (id = ?1)", nativeQuery = true)
    void cancelExhibitionById(@Param("id") Integer id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE exhibitions SET state = 'PLANNED' WHERE (id = ?1)", nativeQuery = true)
    void planExhibitionById(@Param("id") Integer id);

    Page<Exhibition> findAllByStartDateAfter(LocalDate startDate, Pageable pageable);

    List<Exhibition> findAll();

    Optional<Exhibition> findById(Integer id);

    @Override
    Exhibition save(Exhibition exhibition);
}
