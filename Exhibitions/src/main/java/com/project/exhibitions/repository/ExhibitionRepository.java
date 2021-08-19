package com.project.exhibitions.repository;

import com.project.exhibitions.entity.Exhibition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface ExhibitionRepository extends JpaRepository<Exhibition, Integer> {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE exhibitions SET state = 'CANCELED' WHERE (id = ?1)", nativeQuery = true)
    void cancelExhibitionById(@Param("id") Integer id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE exhibitions SET state = 'PLANNED' WHERE (id = ?1)", nativeQuery = true)
    void planExhibitionById(@Param("id") Integer id);
}
