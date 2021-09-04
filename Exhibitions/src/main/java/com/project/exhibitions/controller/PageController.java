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

/**
 * @author Illia Koshkin
 */
@AllArgsConstructor

@Slf4j
@Controller
public class PageController {
    private final UserService userService;

    private final ExhibitionService exhibitionService;

    private final TicketService ticketService;

    private final Configurator configurator = new Configurator();

    /**
     * This method shows main home page
     * @param model It is a target where to put attributes
     * @param authentication To check the role of the user
     * @param pageable To get current page of exhibitions
     * @return filled home page
     */
    @GetMapping("/")
    public String main(Model model, Authentication authentication, Pageable pageable) {
        configurator.basicConfiguration(model, authentication, View.view);
        Page<Exhibition> pages = exhibitionService.findStartingStartDate(LocalDate.now(), pageable.withPage(configurator.getCurrentPage()));
        model.addAttribute("pagesNumber", pages.getTotalPages());
        model.addAttribute("content", pages.getContent());
        model.addAttribute("noElementsFound", View.view.getBundleText(ITextsPaths.NO_ELEMENTS_FOUND));

        model.addAttribute("now", LocalDate.now().toString());
        model.addAttribute("filterByDate", View.view.getBundleText(ITextsPaths.FILTER_BY_DATE));
        model.addAttribute("submit", View.view.getBundleText(ITextsPaths.SUBMIT));
        log.info("List of all exhibitions was given");
        configurator.configureExhibitionTable(model, View.view);
        return "home";
    }

    /**
     * This method changes page
     * @param request To get which page was pressed
     * @return Redirects to home page
     */
    @PostMapping(value = "/", params = "id")
    public RedirectView changePage(HttpServletRequest request) {
        log.info("Page was changed");
        configurator.setCurrentPage(Integer.parseInt(request.getParameter("id")) - 1);
        return new RedirectView("/");
    }

    /**
     * This method processes buying operation
     * @param request to get which user pressed the button
     * @param model It is a target where to put attributes
     * @param authentication To check the role of the user
     * @return Buying result page
     */
    @PostMapping(value = "/buy")
    public String buyTicket(HttpServletRequest request, Model model, Authentication authentication) {
        configurator.basicConfiguration(model, authentication, View.view);
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
            model.addAttribute("isSuccessful", View.view.getBundleText(ITextsPaths.BUY_TICKET_SUCCESS));
        } catch(IllegalArgumentException exc) {
            model.addAttribute("isSuccessful", View.view.getBundleText(ITextsPaths.BUY_TICKET_ERROR));
            log.error("Error in saving new Ticket");
        }
        return "buyTicketResult";
    }

    /**
     * This method cancels an Exhibition
     * @param request To get Exhibition id
     * @param model It is a target where to put attributes
     * @param authentication To check the role of the user
     * @return Cancel result page
     */
    @PostMapping("/cancel")
    public String cancelExhibition(HttpServletRequest request, Model model, Authentication authentication) {
        configurator.basicConfiguration(model, authentication, View.view);
        exhibitionService.cancelExhibitionById(Integer.parseInt(request.getParameterNames().nextElement()));
        log.info("Exhibition with id: " + request.getParameterNames().nextElement() + " was cancelled");
        model.addAttribute("isSuccessful", View.view.getBundleText(ITextsPaths.CANCEL_EXHIBITION));
        return "cancelExhibitionResult";
    }

    /**
     * This method plans an Exhibition
     * @param request To get Exhibition id
     * @param model It is a target where to put attributes
     * @param authentication To check the role of the user
     * @return Plan result page
     */
    @PostMapping("/plan")
    public String planExhibition(HttpServletRequest request, Model model, Authentication authentication) {
        configurator.basicConfiguration(model, authentication, View.view);
        exhibitionService.planExhibitionById(Integer.parseInt(request.getParameterNames().nextElement()));
        log.info("Exhibition with id: " + request.getParameterNames().nextElement() + " was planned");
        model.addAttribute("isSuccessful", View.view.getBundleText(ITextsPaths.PLAN_EXHIBITION));
        return "planExhibitionResult";
    }

    /**
     * This method filters exhibitions by date
     * @param request To get inputted date
     * @param model It is a target where to put attributes
     * @param authentication To check the role of the user
     * @return Filtered exhibitions
     */
    @PostMapping("/")
    public String filterByDate(HttpServletRequest request, Model model, Authentication authentication) {
        configurator.basicConfiguration(model, authentication, View.view);

        model.addAttribute("filterByDate", View.view.getBundleText(ITextsPaths.FILTER_BY_DATE));
        model.addAttribute("submit", View.view.getBundleText(ITextsPaths.SUBMIT));

        configurator.configureExhibitionTable(model, View.view);
        try {
            LocalDate filterDate = LocalDate.of(Integer.parseInt(request.getParameter("filterDate").substring(ISubstringIndexesForDatesAndTimes.YEAR_BEGIN_INDEX, ISubstringIndexesForDatesAndTimes.YEAR_END_INDEX)), Integer.parseInt(request.getParameter("filterDate").substring(ISubstringIndexesForDatesAndTimes.MONTH_BEGIN_INDEX, ISubstringIndexesForDatesAndTimes.MONTH_END_INDEX)), Integer.parseInt(request.getParameter("filterDate").substring(ISubstringIndexesForDatesAndTimes.DAY_BEGIN_INDEX)));
            model.addAttribute("content", exhibitionService.allExhibitions().stream().filter(element ->
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

    /**
     * This method opens home page but with another url(for better user experience)
     * @param model It is a target where to put attributes
     * @param authentication To check the role of the user
     * @param pageable To get current page of exhibitions
     */
    @GetMapping("/home")
    public void home(Model model, Authentication authentication, Pageable pageable) {
        main(model, authentication, pageable);
    }

    /**
     * This method changes language to Ukrainian of all texts
     * @return Redirect to main home page
     */
    @PostMapping(value="/change-language", params="ukr")
    public RedirectView ukr() {
        log.info("Language bundle was changed to: " + ILocaleNames.UKR_LANGUAGE, View.view.changeLocale(Optional.of(new Locale(ILocaleNames.UKR_LANGUAGE, ILocaleNames.UKR_COUNTRY))));
        return new RedirectView("/");
    }

    /**
     * This method changes language to English of all texts
     * @return Redirect to main home page
     */
    @PostMapping(value="/change-language", params="eng")
    public RedirectView eng() {
        log.info("Language bundle was changed to: " + ILocaleNames.DEFAULT_LANGUAGE, View.view.changeLocale(Optional.of(new Locale(ILocaleNames.DEFAULT_LANGUAGE))));
        return new RedirectView("/");
    }

    /**
     * This method shows registration page
     * @param model It is a target where to put attributes
     * @param authentication To check the role of the user
     * @return String registration page
     */
    @GetMapping("/registration")
    public String registration(Model model, Authentication authentication) {
        configurator.basicConfiguration(model, authentication, View.view);
        model.addAttribute("registration", View.view.getBundleText(ITextsPaths.REGISTER));
        model.addAttribute("submit", View.view.getBundleText(ITextsPaths.SUBMIT));
        model.addAttribute("email", View.view.getBundleText(ITextsPaths.EMAIL));
        model.addAttribute("username", View.view.getBundleText(ITextsPaths.USERNAME));
        model.addAttribute("password", View.view.getBundleText(ITextsPaths.PASSWORD));
        return "registration";
    }

    /**
     * This method shows addExhibition page
     * @param model It is a target where to put attributes
     * @param authentication To check the role of the user
     * @return String addExhibition page
     */
    @GetMapping("/addExhibition")
    public String addExhibition(Model model, Authentication authentication) {
        configurator.basicConfiguration(model, authentication, View.view);
        model.addAttribute("now", LocalDate.now().toString());
        model.addAttribute("inputTopic", View.view.getBundleText(ITextsPaths.EXHIBITION_ADDING_TOPIC));
        model.addAttribute("inputStartDate", View.view.getBundleText(ITextsPaths.EXHIBITION_ADDING_START_DATE));
        model.addAttribute("inputEndDate", View.view.getBundleText(ITextsPaths.EXHIBITION_ADDING_END_DATE));
        model.addAttribute("inputRoomsNumber", View.view.getBundleText(ITextsPaths.EXHIBITION_ADDING_ROOMS_NUMBER));
        model.addAttribute("inputStartTime", View.view.getBundleText(ITextsPaths.EXHIBITION_ADDING_START_TIME));
        model.addAttribute("inputEndTime", View.view.getBundleText(ITextsPaths.EXHIBITION_ADDING_END_TIME));
        model.addAttribute("inputPrice", View.view.getBundleText(ITextsPaths.EXHIBITION_ADDING_PRICE));
        model.addAttribute("submit", View.view.getBundleText(ITextsPaths.SUBMIT));
        return "addExhibition";
    }

    /**
     * This method shows statistics of visiting exhibitions
     * @param model It is a target where to put attributes
     * @param authentication To check the role of the user
     * @return String statistics page
     */
    @GetMapping("/statistics")
    public String statistics(Model model, Authentication authentication) {
        configurator.basicConfiguration(model, authentication, View.view);
        model.addAttribute("statistics", exhibitionService.statistics(exhibitionService.allExhibitions()));
        log.info("List with statistics was given");
        model.addAttribute("topic", View.view.getBundleText(ITextsPaths.TOPIC));
        model.addAttribute("startDate", View.view.getBundleText(ITextsPaths.START_DATE));
        model.addAttribute("endDate", View.view.getBundleText(ITextsPaths.END_DATE));
        model.addAttribute("price", View.view.getBundleText(ITextsPaths.PRICE));
        model.addAttribute("state", View.view.getBundleText(ITextsPaths.STATE));
        model.addAttribute("visitors", View.view.getBundleText(ITextsPaths.VISITORS));
        return "statistics";
    }

    /**
     * This method fills login form
     * @param model It is a target where to put attributes
     * @param authentication To check the role of the user
     * @return String login page
     */
    @GetMapping("/login")
    public String loginForm(Model model, Authentication authentication) {
        configurator.basicConfiguration(model, authentication, View.view);
        model.addAttribute("login", View.view.getBundleText(ITextsPaths.LOGIN));
        model.addAttribute("submit", View.view.getBundleText(ITextsPaths.SUBMIT));
        model.addAttribute("email", View.view.getBundleText(ITextsPaths.EMAIL));
        model.addAttribute("username", View.view.getBundleText(ITextsPaths.USERNAME));
        model.addAttribute("password", View.view.getBundleText(ITextsPaths.PASSWORD));
        return "login";
    }

    /**
     * This method performs logging out
     * @param model It is a target where to put attributes
     * @param authentication To check the role of the user
     * @param pageable To get current page
     * @return Redirect to home page
     */
    @GetMapping("/logout")
    public RedirectView logout(Model model, Authentication authentication, Pageable pageable) {
        authentication.setAuthenticated(false);
        log.info("User logged out.");
        main(model, authentication, pageable);
        return new RedirectView("/");
    }

    /**
     * This method shows error page
     * @param model It is a target where to put attributes
     * @param authentication To check the role of the user
     * @return String error page
     */
    @GetMapping("/error")
    public String error(Model model, Authentication authentication) {
        configurator.basicConfiguration(model, authentication, View.view);
        log.warn("Error page was shown");
        model.addAttribute("errorMessage", View.view.getBundleText(ITextsPaths.ERROR_PAGE_MESSAGE));
        return "error";
    }

}
