package com.project.exhibitions.repository;

import com.project.exhibitions.entity.Exhibition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author Illia Koshkin
 */
@Repository
public interface ExhibitionRepositoryImpl extends CrudRepository<Exhibition, Integer> {
    /**
     * This method cancels exhibition by id
     * @param id Of the Exhibition
     */
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE exhibitions SET state = 'CANCELED' WHERE (id = ?1)", nativeQuery = true)
    void cancelExhibitionById(@Param("id") Integer id);

    /**
     * This method plans exhibition by id
     * @param id Of the Exhibition
     */
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE exhibitions SET state = 'PLANNED' WHERE (id = ?1)", nativeQuery = true)
    void planExhibitionById(@Param("id") Integer id);

    /**
     * This method finds all exhibitions after given date
     * @param startDate Date when Exhibition start
     * @param pageable Exhibitions in pages
     * @return Page with found exhibitions
     */
    Page<Exhibition> findAllByStartDateAfter(LocalDate startDate, Pageable pageable);

    /**
     * This method finds all exhibitions
     * @return util.List of Exhibition
     */
    List<Exhibition> findAll();

    /**
     * This method find Exhibition by id
     * @param id Id of the Exhibition
     * @return Optional of the Exhibition
     */
    Optional<Exhibition> findById(Integer id);

    /**
     * This method creates new record of a new Exhibition in database
     * @param exhibition Exhibition object
     * @return Same Exhibition
     */
    @Override
    Exhibition save(Exhibition exhibition);
}
