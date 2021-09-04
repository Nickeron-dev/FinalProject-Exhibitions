package com.project.exhibitions.controller;

import com.project.exhibitions.entity.Role;
import com.project.exhibitions.view.ITextsPaths;
import com.project.exhibitions.view.View;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

/**
 * @author Illia Koshkin
 */
public class Configurator {

    private Integer currentPage = 0;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * This method adds all required attributes to Model to fill header with all allowed references
     * @param model It is a target where to put attributes
     * @param authentication To check the role of the user
     * @param view to get localized text from bundle
     * @return The result of the operation
     */
    public boolean basicConfiguration(Model model, Authentication authentication, View view) {
        model.addAttribute("home", view.getBundleText(ITextsPaths.HOME));
        model.addAttribute("register", view.getBundleText(ITextsPaths.REGISTER));
        model.addAttribute("login", view.getBundleText(ITextsPaths.LOGIN));
        model.addAttribute("logout", view.getBundleText(ITextsPaths.LOGOUT_HREF));
        try {
            model.addAttribute("isAuthorized", authentication.isAuthenticated());
        } catch(NullPointerException exc) {
            model.addAttribute("isAuthorized", false);
        }
        model.addAttribute("addExhibition", view.getBundleText(ITextsPaths.ADD_EXHIBITION_HREF));
        model.addAttribute("statistics", view.getBundleText(ITextsPaths.STATISTICS_HREF));
        try {
            model.addAttribute("isAdmin", authentication.getAuthorities().contains(Role.ADMIN));
        } catch(NullPointerException exc) {
            model.addAttribute("isAdmin", false);
        }
        return true;
    }

    /**
     * This method adds all required column names to exhibition table
     * @param model Target where attributes will be set
     * @param view Source of the localized texts
     * @return result of the operation
     */
    public boolean configureExhibitionTable(Model model, View view) {
        model.addAttribute("topic", view.getBundleText(ITextsPaths.TOPIC));
        model.addAttribute("startDate", view.getBundleText(ITextsPaths.START_DATE));
        model.addAttribute("endDate", view.getBundleText(ITextsPaths.END_DATE));
        model.addAttribute("startTime", view.getBundleText(ITextsPaths.START_TIME));
        model.addAttribute("endTime", view.getBundleText(ITextsPaths.END_TIME));
        model.addAttribute("price", view.getBundleText(ITextsPaths.PRICE));
        model.addAttribute("rooms", view.getBundleText(ITextsPaths.ROOMS));
        model.addAttribute("state", view.getBundleText(ITextsPaths.STATE));
        model.addAttribute("buyTicket", view.getBundleText(ITextsPaths.BUY_A_TICKET));
        model.addAttribute("buy", view.getBundleText(ITextsPaths.BUY));
        model.addAttribute("cancel", view.getBundleText(ITextsPaths.CANCEL));
        model.addAttribute("plan", view.getBundleText(ITextsPaths.PLAN));
        return true;
    }

}
