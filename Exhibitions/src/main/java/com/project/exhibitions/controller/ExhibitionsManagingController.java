package com.project.exhibitions.controller;

import com.project.exhibitions.containers.ISubstringIndexesForDatesAndTimes;
import com.project.exhibitions.entity.Exhibition;
import com.project.exhibitions.entity.ExhibitionState;
import com.project.exhibitions.services.ExhibitionService;
import com.project.exhibitions.view.ITextsPaths;
import com.project.exhibitions.view.View;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Local;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor

@Slf4j
@RestController
@RequestMapping("/")
public class ExhibitionsManagingController {

    private final ExhibitionService exhibitionService;
    private final View view = new View();
    @PostMapping("/addExhibition")
    public ModelAndView addExhibition(HttpServletRequest request, Model model, Authentication authentication) {
        Configurator config = new Configurator();
        config.basicConfiguration(model, authentication, view);

        try {
            LocalDate startDate = LocalDate.of(Integer.parseInt(request.getParameter("startDate")
                            .substring(ISubstringIndexesForDatesAndTimes.YEAR_BEGIN_INDEX,
                                    ISubstringIndexesForDatesAndTimes.YEAR_END_INDEX)),
                    Integer.parseInt(request.getParameter("startDate")
                            .substring(ISubstringIndexesForDatesAndTimes.MONTH_BEGIN_INDEX,
                                    ISubstringIndexesForDatesAndTimes.MONTH_END_INDEX)),
                    Integer.parseInt(request.getParameter("startDate")
                            .substring(ISubstringIndexesForDatesAndTimes.DAY_BEGIN_INDEX)));
            LocalDate endDate = LocalDate.of(Integer.parseInt(request.getParameter("endDate")
                                    .substring(ISubstringIndexesForDatesAndTimes.YEAR_BEGIN_INDEX,
                                            ISubstringIndexesForDatesAndTimes.YEAR_END_INDEX)),
                            Integer.parseInt(request.getParameter("endDate")
                                    .substring(ISubstringIndexesForDatesAndTimes.MONTH_BEGIN_INDEX,
                                            ISubstringIndexesForDatesAndTimes.MONTH_END_INDEX)),
                            Integer.parseInt(request.getParameter("endDate")
                                    .substring(ISubstringIndexesForDatesAndTimes.DAY_BEGIN_INDEX)));
            if (startDate.isAfter(endDate)) {
                log.warn("Start date is after end date");
                throw new IllegalArgumentException("Start date is after end date");
            }
            exhibitionService.saveNewExhibition(Exhibition.builder()
                    .topic(request.getParameter("topic"))
                    .startDate(startDate)
                    .endDate(endDate)
                    .startTimeEveryDay(LocalTime.of(Integer.parseInt(request.getParameter("startTime")
                            .substring(ISubstringIndexesForDatesAndTimes.HOUR_BEGIN_INDEX,
                                    ISubstringIndexesForDatesAndTimes.HOUR_END_INDEX)),
                            Integer.parseInt(request.getParameter("startTime")
                                    .substring(ISubstringIndexesForDatesAndTimes.MINUTE_BEGIN_INDEX))))
                    .endTimeEveryDay(LocalTime.of(Integer.parseInt(request.getParameter("endTime")
                            .substring(ISubstringIndexesForDatesAndTimes.HOUR_BEGIN_INDEX,
                                    ISubstringIndexesForDatesAndTimes.HOUR_END_INDEX)),
                            Integer.parseInt(request.getParameter("endTime")
                                    .substring(ISubstringIndexesForDatesAndTimes.MINUTE_BEGIN_INDEX))))
                    .rooms(Integer.parseInt(request.getParameter("rooms")))
                    .price(Integer.parseInt(request.getParameter("price")))
                    .state(ExhibitionState.PLANNED)
                    .build());
            log.info("New exhibition was saved");
            model.addAttribute("result", View.view.getBundleText(ITextsPaths.EXHIBITION_ADDING_SUCCESS));
        } catch (Exception exc) {
            model.addAttribute("result", View.view.getBundleText(ITextsPaths.EXHIBITION_ADDING_ERROR));
            log.warn("Error saving new exhibition");
        }
        return new ModelAndView("addExhibitionResult");
    }
}
