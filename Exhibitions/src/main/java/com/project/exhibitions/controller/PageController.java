package com.project.exhibitions.controller;

import com.project.exhibitions.dto.TicketDTO;
import com.project.exhibitions.entity.Role;
import com.project.exhibitions.entity.Ticket;
import com.project.exhibitions.services.ExhibitionService;
import com.project.exhibitions.services.TicketService;
import com.project.exhibitions.services.UserService;
import com.project.exhibitions.view.ILocaleNames;
import com.project.exhibitions.view.ITextsPaths;
import com.project.exhibitions.view.View;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Optional;

@AllArgsConstructor

@Controller
public class PageController {

    @Autowired
    private UserService userService;

    @Autowired
    private ExhibitionService exhibitionService;

    @Autowired
    private TicketService ticketService;

    private final View view = new View();

    @GetMapping("/")
    public String main(Model model, Authentication authentication) {
        model.addAttribute("register", view.getBundleText(ITextsPaths.REGISTER_BUTTON));
        model.addAttribute("login", view.getBundleText(ITextsPaths.LOGIN_BUTTON));
        model.addAttribute("listExhibitions", exhibitionService.allExhibitions());
        try {
            model.addAttribute("isAdmin", authentication.getAuthorities().contains(Role.ADMIN));
        } catch(NullPointerException exc) {

        }
        model.addAttribute("topic", view.getBundleText(ITextsPaths.TOPIC));
        model.addAttribute("startDate", view.getBundleText(ITextsPaths.START_DATE));
        model.addAttribute("endDate", view.getBundleText(ITextsPaths.END_DATE));
        model.addAttribute("startTime", view.getBundleText(ITextsPaths.START_TIME));
        model.addAttribute("endTime", view.getBundleText(ITextsPaths.END_TIME));
        model.addAttribute("price", view.getBundleText(ITextsPaths.PRICE));
        model.addAttribute("rooms", view.getBundleText(ITextsPaths.ROOMS));
        model.addAttribute("state", view.getBundleText(ITextsPaths.STATE));
        model.addAttribute("buyTicket", view.getBundleText(ITextsPaths.BUY_A_TICKET));
        model.addAttribute("cancel", view.getBundleText(ITextsPaths.CANCEL));
        model.addAttribute("plan", view.getBundleText(ITextsPaths.PLAN));
        return "home";
    }

    @PostMapping(value = "/buy")
    public String buyTicket(HttpServletRequest request, Model model) {
        try {
            TicketDTO ticket = new TicketDTO(Integer
                    .parseInt(request.getUserPrincipal().toString().split("=|\\,")[2]),
                    Integer.parseInt(request.getParameterNames().nextElement()));
            ticketService.saveNewTicket(Ticket.builder()
                    .userEmail(userService.findById(ticket.getUserId()).get().getEmail())
                    .userId(ticket.getUserId())
                    .exhibitionTopic(exhibitionService.findById(ticket.getExhibitionId()).get().getTopic())
                    .exhibitionId(ticket.getExhibitionId())
                    .build());
            model.addAttribute("isSuccessful", "successful!");
        } catch(Exception exc) {
            model.addAttribute("isSuccessful", "Error!");
        }
        return "buyTicketResult";
    }

    @PostMapping("/cancel")
    public String cancelExhibition(HttpServletRequest request, Model model) {
        exhibitionService.cancelExhibitionById(Integer.parseInt(request.getParameterNames().nextElement()));

        return "cancelExhibitionResult";
    }

    @PostMapping("/plan")
    public String planExhibition(HttpServletRequest request, Model model) {
        exhibitionService.planExhibitionById(Integer.parseInt(request.getParameterNames().nextElement()));

        return "planExhibitionResult";
    }

    @GetMapping("/home")
    public void home(Model model, Authentication authentication) {
        main(model, authentication);
    }

    @PostMapping(value="/change-language", params="ukr")
    public RedirectView ukr() {
        view.changeLocale(Optional.of(new Locale(ILocaleNames.UKR_LANGUAGE, ILocaleNames.UKR_COUNTRY)));
        return new RedirectView("/");
    }

    @PostMapping(value="/change-language", params="eng")
    public RedirectView eng() {
        view.changeLocale(Optional.of(new Locale(ILocaleNames.DEFAULT_LANGUAGE)));
        return new RedirectView("/");
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @GetMapping("/addExhibition")
    public String addExhibition() {
        return "addExhibition";
    }

}
