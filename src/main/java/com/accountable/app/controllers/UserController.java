package com.accountable.app.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.accountable.app.config.SecurityService;
import com.accountable.app.entities.User;
import com.accountable.app.services.UserService;
import com.accountable.app.utils.ErrorMessage;
import com.accountable.app.utils.InfoMessage;
import com.accountable.app.utils.Message;
import com.accountable.app.utils.SuccessMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.Set;


@Controller
public class UserController {

    private final UserService userService;
    private final SecurityService securityService;

    @Autowired
    public UserController(UserService userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }

    @RequestMapping(value={"/login"}, method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        modelAndView.addObject("active", "login");
        return modelAndView;
    }


    @RequestMapping(value="/register", method = RequestMethod.GET)
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.addObject("active", "register");
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findByUsername(user.getUsername());
        if (userExists != null) {
            Set<Message> messages = new HashSet<Message>();
            Message msg1 = new ErrorMessage();
            msg1.setContent("User with given username already exists.");
            messages.add(msg1);
            modelAndView.addObject("active", "register");
            modelAndView.setViewName("register");
            modelAndView.addObject("messages", messages);
            return modelAndView;
        }
        if (bindingResult.hasErrors()) {
            Set<Message> messages = new HashSet<Message>();
            Message msg1 = new ErrorMessage();
            msg1.setContent("Error during registration, please try again.");
            modelAndView.addObject("active", "register");
            modelAndView.setViewName("register");
            modelAndView.addObject("messages", messages);

        } else {
            userService.saveUser(user);
            Set<Message> messages = new HashSet<Message>();
            Message msg1 = new SuccessMessage();
            msg1.setContent("User has been registered successfully");
            messages.add(msg1);

            Message msg2 = new InfoMessage();
            msg2.setContent("You haven't added any company yet. Add one and start having records of your work.");
            messages.add(msg2);

            modelAndView.addObject("messages", messages);
            modelAndView.addObject("user", user);
            securityService.autoLogin(user.getUsername(), user.getPassword(), request);
            modelAndView.addObject("active", "home");
            modelAndView.setViewName("home");
        }
        return modelAndView;
    }

}