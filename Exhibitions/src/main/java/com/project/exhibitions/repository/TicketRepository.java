package com.project.exhibitions.repository;

import com.project.exhibitions.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Illia Koshkin
 */
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

}
