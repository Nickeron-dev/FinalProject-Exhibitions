package com.project.exhibitions;

import com.project.exhibitions.dto.UserDTO;
import com.project.exhibitions.entity.Exhibition;
import com.project.exhibitions.entity.ExhibitionState;
import com.project.exhibitions.entity.Ticket;
import com.project.exhibitions.services.ExhibitionService;
import com.project.exhibitions.services.TicketService;
import com.project.exhibitions.services.UserService;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootTest
class ExhibitionsApplicationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private ExhibitionService exhibitionService;

    @Autowired
    private TicketService ticketService;

    @Ignore
    @Test
    public void loadAndSaveUserTest() {
        userService.saveNewUser(UserDTO.builder()
                .email("valeria@gmail.com")
                .username("Valeria")
                .password("pass")
                .build());
        Assert.notNull(userService.findById(28), "User not found");
        Assert.notNull(userService.loadUserByUsername("Valeria"), "User by username was not found");
    }

    @Ignore
    @Test
    public void exhibitionStatisticsTest() {
        Assert.notNull(exhibitionService.statistics(), "Statistics not found");
    }

    @Ignore
    @Test
    public void exhibitionAllTest() {
        Assert.notNull(exhibitionService.allExhibitions(), "Exhibitions not found");
    }

    @Ignore
    @Test
    public void saveAndFindExhibition() {
        exhibitionService.saveNewExhibition(Exhibition.builder()
                .topic("Books")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .state(ExhibitionState.PLANNED)
                .startTimeEveryDay(LocalTime.now())
                .endTimeEveryDay(LocalTime.now().plusHours(8))
                .rooms(2)
                .price(500)
                .build());
        Assert.notNull(exhibitionService.findById(8), "Exhibition not found");
    }

    @Ignore
    @Test
    public void planExhibition() {
        exhibitionService.planExhibitionById(8);
        Assert.isTrue(ExhibitionState.PLANNED == exhibitionService.findById(8).orElseThrow(IllegalArgumentException::new).getState(),
                "States are not same");
    }

    @Ignore
    @Test
    public void cancelExhibition() {
        exhibitionService.cancelExhibitionById(8);
        Assert.isTrue(ExhibitionState.CANCELED == exhibitionService.findById(8).orElseThrow(IllegalArgumentException::new).getState(),
                "States are not same");
    }

    @Ignore
    @Test
    public void saveTicketTest() {
        Assert.notNull(ticketService.saveNewTicket(Ticket.builder()
                .exhibitionId(8)
                .exhibitionTopic("Books")
                .userId(28)
                .userEmail("valeria@gmail.com")
                .build()), "Ticket was not created");
    }

    @Ignore
    @Test
    public void countExhibitionsTickets() {
        Assert.isTrue(2 == ticketService.countByExhibitionId(8), "Found amount was not equal to given");
    }

}
