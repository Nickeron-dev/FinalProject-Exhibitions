package com.project.exhibitions.controller;

import com.project.exhibitions.entity.Exhibition;
import com.project.exhibitions.services.ExhibitionService;
import com.project.exhibitions.view.View;
import lombok.AllArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.Year;

@AllArgsConstructor

@RestController
@RequestMapping("/")
public class ExhibitionsManagingController {
    @Autowired
    private ExhibitionService exhibitionService;
    @Autowired
    private View view;

    @PostMapping("/addExhibition")
    public ModelAndView addExhibition(HttpServletRequest request, Model model) {
        try {
            LocalDate.of(2232, 3, 23);

            exhibitionService.saveNewExhibition(Exhibition.builder()
                    .topic(request.getParameter("topic"))
                    .startDate(LocalDate.of(Integer.parseInt(request.getParameter("startDate")), Integer.parseInt(request.getParameter("startDate")), Integer.parseInt(request.getParameter("startDate"))))
                    .endDate(LocalDate.of(Integer.parseInt(request.getParameter("startDate")), Integer.parseInt(request.getParameter("startDate")), Integer.parseInt(request.getParameter("startDate"))))
                    .startTimeEveryDay(LocalTime.of(Integer.parseInt(request.getParameter("startTimeEverDay")), Integer.parseInt(request.getParameter("endTimeEverDay"))))
                    .rooms(Integer.parseInt(request.getParameter("rooms")))
                    .price(Integer.parseInt(request.getParameter("price")))
                    .build());
            model.addAttribute("result", view.getBundleText("addition.exhibition.success"));
        } catch (Exception exc) {
            model.addAttribute("result", view.getBundleText("addition.exhibition.error"));
        }
        return new ModelAndView("addExhibitionResult");
    }
}
