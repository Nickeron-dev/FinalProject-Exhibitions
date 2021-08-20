package com.project.exhibitions.services;

import com.project.exhibitions.entity.Ticket;
import com.project.exhibitions.repository.TicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor

@Service
public class TicketService {
    private TicketRepository ticketRepository;

    public Ticket saveNewTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public long countByExhibitionId(Integer exhibitionId) {
        return ticketRepository.findAll().stream().filter(element -> element.getExhibitionId() == exhibitionId).count();
    }
}
