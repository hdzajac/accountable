package com.accountable.app.controllers;

import com.accountable.app.entities.Company;
import com.accountable.app.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Set;

@Controller
public class HomeController {

    private final CompanyService companyService;

    @Autowired
    public HomeController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @RequestMapping(value = {"/home"}, method = RequestMethod.GET)
    public ModelAndView enterInput(Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("active", "home");
        Set<Company> companySet = companyService.getCompaniesForUsername(principal.getName());
        modelAndView.addObject("companySet", companySet);
        modelAndView.setViewName("home");
        return modelAndView;
    }

}
