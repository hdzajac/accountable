package com.accountable.test.security;


import com.accountable.app.controllers.HomeController;
import com.accountable.app.services.CompanyService;
import com.accountable.app.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@RunWith(SpringRunner.class)
@WebMvcTest(HomeController.class)
public class WrongCredentialsRequestTest {


    @Autowired
    private MockMvc mvc;

    @MockBean
    private CompanyService companyService;

    @MockBean
    private UserService userService;


    @Test
    public void testLackOfPermission() throws Exception {

        this.mvc.perform(MockMvcRequestBuilders.get("/home")
                .with(user("username").password("password").roles("ADMIN"))
                .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }




}

