package com.project.exhibitions.services;

import com.project.exhibitions.dto.TicketDTO;
import com.project.exhibitions.entity.Ticket;
import com.project.exhibitions.entity.User;
import com.project.exhibitions.repository.ExhibitionRepository;
import com.project.exhibitions.repository.TicketRepository;
import com.project.exhibitions.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {
    private TicketRepository ticketRepository;
    @Autowired
    private ExhibitionService exhibitionService;
    @Autowired
    private UserService userService;

    public Ticket saveNewTicket(TicketDTO ticket) {
        System.out.println("UserId: " + ticket.getUserId() + " " + ticket.getExhibitionId());
        String userEmail = userService.findById(ticket.getUserId()).get().getEmail();
        String exhibitionTopic = exhibitionService.findById(ticket.getExhibitionId()).get().getTopic();
        System.out.println(Ticket.builder()
                .exhibitionId(ticket.getExhibitionId())
                .userEmail(userEmail)
                .userId(ticket.getUserId())
                .exhibitionTopic(exhibitionTopic)
                .build().toString());
        return ticketRepository.save(Ticket.builder()
                .exhibitionId(ticket.getExhibitionId())
                .userEmail(userEmail)
                .userId(ticket.getUserId())
                .exhibitionTopic(exhibitionTopic)
                .build());
    }
}
