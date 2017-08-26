package com.accountable.app.controllers;

import com.accountable.app.entities.Company;
import com.accountable.app.entities.Input;
import com.accountable.app.entities.User;
import com.accountable.app.services.CompanyService;
import com.accountable.app.services.InputService;
import com.accountable.app.services.UserService;
import com.accountable.app.utils.InfoMessage;
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
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Controller
public class InputsController {

    private final UserService userService;

    private final CompanyService companyService;

    private final InputService inputService;

    @Autowired
    public InputsController(UserService userService, CompanyService companyService, InputService inputService) {
        this.userService = userService;
        this.companyService = companyService;
        this.inputService = inputService;
    }

    @RequestMapping(value={"/input"}, method = RequestMethod.GET)
    public ModelAndView enterInput(Principal principal){
        ModelAndView modelAndView = new ModelAndView();
        Set<Company> companySet = companyService.getCompaniesForUsername(principal.getName());

        if(companySet.isEmpty()){
            Set<Message> messages = new HashSet<Message>();
            Message message = new InfoMessage();
            message.setContent("You haven'a added any companies. Add one to be able to save working hours");
            messages.add(message);

            modelAndView.setViewName("/company");
            modelAndView.addObject("messages", messages);
            modelAndView.addObject("active", "input");
            return modelAndView;
        }

        else {
            Input input = new Input();
            modelAndView.addObject("companies", companySet);
            modelAndView.addObject("input", input);
            modelAndView.addObject("active", "input");
            modelAndView.setViewName("input");
            return modelAndView;
        }
    }

    @RequestMapping(value = "/input", method = RequestMethod.POST)
    public ModelAndView createNewInput(@Valid Input input, BindingResult bindingResult, Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("active", "input");

        if(bindingResult.hasErrors()){
            modelAndView.setViewName("input");
            return modelAndView;
        }
        else {
            Company currentCompany = input.getCompany();
            String username = principal.getName();

            LocalDateTime dateTime = LocalDateTime.now();
            input.setInputDate(dateTime);

            currentCompany.setBalance(currentCompany.getBalance() + input.getWorkingTime());
            companyService.save(currentCompany);
            Set<Company> companies = companyService.getCompaniesForUsername(username);
            input.setUser(userService.findByUsername(username));
            inputService.saveInput(input);
            modelAndView.addObject("companies", companies);

            Set<Message> messages = new HashSet<Message>();
            Message message = new SuccessMessage();
            message.setContent(String.format("Successfully added %f hours to %s.", input.getWorkingTime(), currentCompany.getName()));

            messages.add(message);
            modelAndView.addObject("messages", messages);

            return modelAndView;
        }
    }

}
