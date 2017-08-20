package com.accountable.test.controllers;

import com.accountable.app.controllers.UserController;
import com.accountable.app.entities.User;
import com.accountable.app.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {


    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    private User user;



    @Before
    public void setup(){
        user = new User();
        user.setEnabled(1);
        user.setUsername("name");
        user.setPassword("123");
        user.setId((long) 1);
    }

    @Test
    public void testRegisteringKnownUser() throws Exception {

        given(this.userService.findByUsername("name"))
                .willReturn(user);

        this.mvc.perform(MockMvcRequestBuilders.post("/register")
                .param("username", user.getUsername())
                .param("password", user.getPassword())
                .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("register"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("messages"));
    }




}
