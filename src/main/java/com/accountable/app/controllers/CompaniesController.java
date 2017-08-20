package com.accountable.app.controllers;

import com.accountable.app.entities.Company;
import com.accountable.app.entities.User;
import com.accountable.app.services.CompanyService;
import com.accountable.app.services.UserService;
import com.accountable.app.utils.ErrorMessage;
import com.accountable.app.utils.Message;
import com.accountable.app.utils.SuccessMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Controller
public class CompaniesController {

    private final CompanyService companyService;

    private final UserService userService;

    @Autowired
    public CompaniesController(CompanyService companyService, UserService userService) {
        this.companyService = companyService;
        this.userService = userService;
    }

    @RequestMapping(value = {"/company"}, method = RequestMethod.GET)
    public ModelAndView enterInput(Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("active", "company");
        Set<Company> companySet = companyService.getCompaniesForUsername(principal.getName());
        if (companySet.isEmpty()) {
            modelAndView.addObject("first", true);
        } else {
            modelAndView.addObject("companySet", companySet);
        }
        modelAndView.setViewName("company");
        return modelAndView;
    }

    @RequestMapping(value = "/company", method = RequestMethod.POST)
    public ModelAndView createCompany(Principal principal, @Valid final Company company, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("active", "company");

        Set<Company> companies = companyService.getCompaniesForUsername(principal.getName());

        for(Company c: companies){
            if(c.getName().toLowerCase().equals(company.getName().toLowerCase())) {
                Set<Message> messages = new HashSet<Message>();
                Message message = new ErrorMessage();
                message.setContent("Company already exists.");
                modelAndView.setViewName("/company");
                messages.add(message);
                modelAndView.addObject("messages", messages);
                return modelAndView;
            }
        }

        if(bindingResult.hasErrors()){
            Set<Message> messages = new HashSet<Message>();
            Message message = new ErrorMessage();
            message.setContent("Error while receiving form, please try again.");
            modelAndView.setViewName("/company");
            messages.add(message);
            modelAndView.addObject("messages", messages);
            return modelAndView;
        }
        else{
            User user = userService.findByUsername(principal.getName());
            company.setUser(user);
            companyService.save(company);
            companies.add(company);
            modelAndView.addObject("companySet", companies);
            modelAndView.setViewName("company");

            Set<Message> messages = new HashSet<Message>();
            Message message = new SuccessMessage();
            message.setContent(String.format("Company: %s was added successfully!", company.getName()));
            messages.add(message);
            modelAndView.addObject("messages", messages);

            return modelAndView;
        }

    }
}