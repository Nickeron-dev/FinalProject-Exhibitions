package com.project.exhibitions.controller;

import com.project.exhibitions.containers.ISubstringIndexesForDatesAndTimes;
import com.project.exhibitions.dto.TicketDTO;
import com.project.exhibitions.entity.Exhibition;
import com.project.exhibitions.entity.Ticket;
import com.project.exhibitions.services.ExhibitionService;
import com.project.exhibitions.services.TicketService;
import com.project.exhibitions.services.UserService;
import com.project.exhibitions.view.ILocaleNames;
import com.project.exhibitions.view.ITextsPaths;
import com.project.exhibitions.view.View;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor

@Slf4j
@Controller
public class PageController {

    @Autowired
    private final UserService userService;

    @Autowired
    private final ExhibitionService exhibitionService;

    @Autowired
    private final TicketService ticketService;

    private final View view = new View();
    private final Configurator configurator = new Configurator();

    @GetMapping("/")
    public String main(Model model, Authentication authentication, Pageable pageable) {
        configurator.basicConfiguration(model, authentication, view);
        Page<Exhibition> pages = exhibitionService.allByPages(pageable);
        System.out.println(pages.getTotalPages());
        System.out.println(pageable.next().getPageNumber());
        System.out.println(pageable.withPage(2).toString());
        model.addAttribute("pagesNumber", pages.getTotalPages());
        model.addAttribute("content", pages.getContent());

        model.addAttribute("now", LocalDate.now().toString());
        model.addAttribute("filterByDate", view.getBundleText(ITextsPaths.FILTER_BY_DATE));
        model.addAttribute("submit", view.getBundleText(ITextsPaths.SUBMIT));
        model.addAttribute("listExhibitions", exhibitionService.allExhibitions());
        log.info("List of all exhibitions was given");
        configurator.configureExhibitionTable(model, view);
        return "home";
    }

    @PostMapping(value = "/", params = "nextPage")
    public RedirectView nextPage() {
        currentPage += 1;
        return new RedirectView("/");
    }

    @PostMapping(value = "/", params = "previousPage")
    public RedirectView nextPage() {
        currentPage -= 1;
        return new RedirectView("/");
    }

    @PostMapping(value = "/buy")
    public String buyTicket(HttpServletRequest request, Model model, Authentication authentication) {
        configurator.basicConfiguration(model, authentication, view);
        try {
            TicketDTO ticket = new TicketDTO(Integer
                    .parseInt(request.getUserPrincipal().toString().split("=|\\,")[2]),
                    Integer.parseInt(request.getParameterNames().nextElement()));
            ticketService.saveNewTicket(Ticket.builder()
                    .userEmail(userService.findById(ticket.getUserId()).orElseThrow(IllegalArgumentException::new).getEmail())
                    .userId(ticket.getUserId())
                    .exhibitionTopic(exhibitionService.findById(ticket.getExhibitionId()).orElseThrow(IllegalArgumentException::new).getTopic())
                    .exhibitionId(ticket.getExhibitionId())
                    .build());
            log.info("New Ticket was saved");
            model.addAttribute("isSuccessful", view.getBundleText(ITextsPaths.BUY_TICKET_SUCCESS));
        } catch(IllegalArgumentException exc) {
            model.addAttribute("isSuccessful", view.getBundleText(ITextsPaths.BUY_TICKET_ERROR));
            log.error("Error in saving new Ticket");
        }
        return "buyTicketResult";
    }

    @PostMapping("/cancel")
    public String cancelExhibition(HttpServletRequest request, Model model, Authentication authentication) {
        configurator.basicConfiguration(model, authentication, view);
        exhibitionService.cancelExhibitionById(Integer.parseInt(request.getParameterNames().nextElement()));
        log.info("Exhibition with id: " + request.getParameterNames().nextElement() + " was cancelled");
        model.addAttribute("isSuccessful", view.getBundleText(ITextsPaths.CANCEL_EXHIBITION));
        return "cancelExhibitionResult";
    }

    @PostMapping("/plan")
    public String planExhibition(HttpServletRequest request, Model model, Authentication authentication) {
        configurator.basicConfiguration(model, authentication, view);
        exhibitionService.planExhibitionById(Integer.parseInt(request.getParameterNames().nextElement()));
        log.info("Exhibition with id: " + request.getParameterNames().nextElement() + " was planned");
        model.addAttribute("isSuccessful", view.getBundleText(ITextsPaths.PLAN_EXHIBITION));
        return "planExhibitionResult";
    }

    @PostMapping("/")
    public String filterByDate(HttpServletRequest request, Model model, Authentication authentication) {
        configurator.basicConfiguration(model, authentication, view);

        model.addAttribute("filterByDate", view.getBundleText(ITextsPaths.FILTER_BY_DATE));
        model.addAttribute("submit", view.getBundleText(ITextsPaths.SUBMIT));

        configurator.configureExhibitionTable(model, view);
        try {
            LocalDate filterDate = LocalDate.of(Integer.parseInt(request.getParameter("filterDate").substring(ISubstringIndexesForDatesAndTimes.YEAR_BEGIN_INDEX, ISubstringIndexesForDatesAndTimes.YEAR_END_INDEX)), Integer.parseInt(request.getParameter("filterDate").substring(ISubstringIndexesForDatesAndTimes.MONTH_BEGIN_INDEX, ISubstringIndexesForDatesAndTimes.MONTH_END_INDEX)), Integer.parseInt(request.getParameter("filterDate").substring(ISubstringIndexesForDatesAndTimes.DAY_BEGIN_INDEX)));
            model.addAttribute("listExhibitions", exhibitionService.allExhibitions().stream().filter(element ->
                    (filterDate.isEqual(element.getStartDate())
                            || filterDate.isEqual(element.getEndDate())
                            || (filterDate.isAfter(element.getStartDate())
                            && filterDate.isBefore(element.getEndDate())) )).collect(Collectors.toList()));
            log.info("Exhibitions that cover date: " + filterDate + " were shown");
        } catch (StringIndexOutOfBoundsException exc) {
            model.addAttribute("notGivenFilter", "Filter argument was not given!");
            log.warn("Error showing exhibition with id: " + request.getParameterNames().nextElement());
        }

        return "home";
    }

    @GetMapping("/home")
    public void home(Model model, Authentication authentication, Pageable pageable) {
        main(model, authentication, pageable);
    }

    @PostMapping(value="/change-language", params="ukr")
    public RedirectView ukr() {
        log.info("Language bundle was changed to: " + ILocaleNames.UKR_LANGUAGE, view.changeLocale(Optional.of(new Locale(ILocaleNames.UKR_LANGUAGE, ILocaleNames.UKR_COUNTRY))));
        return new RedirectView("/");
    }

    @PostMapping(value="/change-language", params="eng")
    public RedirectView eng() {
        log.info("Language bundle was changed to: " + ILocaleNames.DEFAULT_LANGUAGE, view.changeLocale(Optional.of(new Locale(ILocaleNames.DEFAULT_LANGUAGE))));
        return new RedirectView("/");
    }

    @GetMapping("/registration")
    public String registration(Model model, Authentication authentication) {
        configurator.basicConfiguration(model, authentication, view);
        model.addAttribute("registration", view.getBundleText(ITextsPaths.REGISTER));
        model.addAttribute("submit", view.getBundleText(ITextsPaths.SUBMIT));
        model.addAttribute("email", view.getBundleText(ITextsPaths.EMAIL));
        model.addAttribute("username", view.getBundleText(ITextsPaths.USERNAME));
        model.addAttribute("password", view.getBundleText(ITextsPaths.PASSWORD));
        return "registration";
    }

    @GetMapping("/addExhibition")
    public String addExhibition(Model model, Authentication authentication) {
        configurator.basicConfiguration(model, authentication, view);
        model.addAttribute("now", LocalDate.now().toString());
        model.addAttribute("inputTopic", view.getBundleText(ITextsPaths.EXHIBITION_ADDING_TOPIC));
        model.addAttribute("inputStartDate", view.getBundleText(ITextsPaths.EXHIBITION_ADDING_START_DATE));
        model.addAttribute("inputEndDate", view.getBundleText(ITextsPaths.EXHIBITION_ADDING_END_DATE));
        model.addAttribute("inputRoomsNumber", view.getBundleText(ITextsPaths.EXHIBITION_ADDING_ROOMS_NUMBER));
        model.addAttribute("inputStartTime", view.getBundleText(ITextsPaths.EXHIBITION_ADDING_START_TIME));
        model.addAttribute("inputEndTime", view.getBundleText(ITextsPaths.EXHIBITION_ADDING_END_TIME));
        model.addAttribute("inputPrice", view.getBundleText(ITextsPaths.EXHIBITION_ADDING_PRICE));
        model.addAttribute("submit", view.getBundleText(ITextsPaths.SUBMIT));
        return "addExhibition";
    }

    @GetMapping("/statistics")
    public String statistics(Model model, Authentication authentication) {
        configurator.basicConfiguration(model, authentication, view);
        model.addAttribute("statistics", exhibitionService.statistics());
        log.info("List with statistics was given");
        model.addAttribute("topic", view.getBundleText(ITextsPaths.TOPIC));
        model.addAttribute("startDate", view.getBundleText(ITextsPaths.START_DATE));
        model.addAttribute("endDate", view.getBundleText(ITextsPaths.END_DATE));
        model.addAttribute("price", view.getBundleText(ITextsPaths.PRICE));
        model.addAttribute("state", view.getBundleText(ITextsPaths.STATE));
        model.addAttribute("visitors", view.getBundleText(ITextsPaths.VISITORS));
        return "statistics";
    }

    @GetMapping("/login")
    public String loginForm(Model model, Authentication authentication) {
        configurator.basicConfiguration(model, authentication, view);
        model.addAttribute("login", view.getBundleText(ITextsPaths.LOGIN));
        model.addAttribute("submit", view.getBundleText(ITextsPaths.SUBMIT));
        model.addAttribute("email", view.getBundleText(ITextsPaths.EMAIL));
        model.addAttribute("username", view.getBundleText(ITextsPaths.USERNAME));
        model.addAttribute("password", view.getBundleText(ITextsPaths.PASSWORD));
        return "login";
    }

    @GetMapping("/logout")
    public RedirectView logout(HttpServletRequest request, Model model, Authentication authentication, Pageable pageable) {
        authentication.setAuthenticated(false);
        log.info("User logged out.");
        main(model, authentication, pageable);
        return new RedirectView(request.getHeader("Referer").substring(21));
    }

    @GetMapping("/error")
    public String error(Model model, Authentication authentication) {
        configurator.basicConfiguration(model, authentication, view);
        log.warn("Error page was shown");
        model.addAttribute("errorMessage", view.getBundleText(ITextsPaths.ERROR_PAGE_MESSAGE));
        return "error";
    }

}
