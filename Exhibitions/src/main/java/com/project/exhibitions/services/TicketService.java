package com.project.exhibitions.services;

import com.project.exhibitions.entity.Ticket;
import com.project.exhibitions.repository.TicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Illia Koshkin
 */
@AllArgsConstructor

@Service
public class TicketService {
    private final TicketRepository ticketRepository;

    /**
     * This method creates new record of a new Ticket in database
     * @param ticket Ticket object
     * @return Same Ticket object
     */
    public Ticket saveNewTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    /**
     * Thie method counts all tickets to an Exhibition by id
     * @param exhibitionId Id of desired Exhibition
     * @return Amount of tickets in type long
     */
    public long countByExhibitionId(Integer exhibitionId) {
        return ticketRepository.findAll().stream().filter(element -> element.getExhibitionId() == exhibitionId).count();
    }
}
