package com.project.exhibitions.controller;

import com.project.exhibitions.containers.ISubstringIndexesForDatesAndTimes;
import com.project.exhibitions.entity.Exhibition;
import com.project.exhibitions.entity.ExhibitionState;
import com.project.exhibitions.services.ExhibitionService;
import com.project.exhibitions.view.ITextsPaths;
import com.project.exhibitions.view.View;
import lombok.AllArgsConstructor;
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
            exhibitionService.saveNewExhibition(Exhibition.builder()
                    .topic(request.getParameter("topic"))
                    .startDate(LocalDate.of(Integer.parseInt(request.getParameter("startDate").substring(ISubstringIndexesForDatesAndTimes.YEAR_BEGIN_INDEX, ISubstringIndexesForDatesAndTimes.YEAR_END_INDEX)), Integer.parseInt(request.getParameter("startDate").substring(ISubstringIndexesForDatesAndTimes.MONTH_BEGIN_INDEX, ISubstringIndexesForDatesAndTimes.MONTH_END_INDEX)), Integer.parseInt(request.getParameter("startDate").substring(ISubstringIndexesForDatesAndTimes.DAY_BEGIN_INDEX))))
                    .endDate(LocalDate.of(Integer.parseInt(request.getParameter("endDate").substring(ISubstringIndexesForDatesAndTimes.YEAR_BEGIN_INDEX, ISubstringIndexesForDatesAndTimes.YEAR_END_INDEX)), Integer.parseInt(request.getParameter("endDate").substring(ISubstringIndexesForDatesAndTimes.MONTH_BEGIN_INDEX, ISubstringIndexesForDatesAndTimes.MONTH_END_INDEX)), Integer.parseInt(request.getParameter("startDate").substring(ISubstringIndexesForDatesAndTimes.DAY_BEGIN_INDEX))))
                    .startTimeEveryDay(LocalTime.of(Integer.parseInt(request.getParameter("startTime").substring(ISubstringIndexesForDatesAndTimes.HOUR_BEGIN_INDEX, ISubstringIndexesForDatesAndTimes.HOUR_END_INDEX)), Integer.parseInt(request.getParameter("startTime").substring(ISubstringIndexesForDatesAndTimes.MINUTE_BEGIN_INDEX))))
                    .endTimeEveryDay(LocalTime.of(Integer.parseInt(request.getParameter("endTime").substring(ISubstringIndexesForDatesAndTimes.HOUR_BEGIN_INDEX, ISubstringIndexesForDatesAndTimes.HOUR_END_INDEX)), Integer.parseInt(request.getParameter("endTime").substring(ISubstringIndexesForDatesAndTimes.MINUTE_BEGIN_INDEX))))
                    .rooms(Integer.parseInt(request.getParameter("rooms")))
                    .price(Integer.parseInt(request.getParameter("price")))
                    .state(ExhibitionState.PLANNED)
                    .build());
            model.addAttribute("result", View.view.getBundleText(ITextsPaths.EXHIBITION_ADDING_SUCCESS));
        } catch (Exception exc) {
            model.addAttribute("result", View.view.getBundleText(ITextsPaths.EXHIBITION_ADDING_ERROR));
        }
        return new ModelAndView("addExhibitionResult");
    }
}
