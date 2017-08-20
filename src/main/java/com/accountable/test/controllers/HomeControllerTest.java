package com.accountable.test.controllers;


import com.accountable.app.controllers.HomeController;
import com.accountable.app.entities.Company;
import com.accountable.app.entities.User;
import com.accountable.app.services.CompanyService;
import com.accountable.app.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@RunWith(SpringRunner.class)
@WebMvcTest(HomeController.class)
@WithMockUser(username="name",roles={"ADMIN"})
public class HomeControllerTest {


    @Autowired
    private MockMvc mvc;

    @MockBean
    private CompanyService companyService;

    @MockBean
    private UserService userService;

    private Set<Company> companySet;

    @Before
    public void setup(){

        User user = new User();
        user.setEnabled(1);
        user.setUsername("name");
        user.setPassword("123");
        user.setId((long) 1);

        companySet = new HashSet<>();
        Company company = new Company();
        company.setId((long) 1);
        company.setUser(user);
        company.setBalance((float) 1.0);
    }

    @Test
    public void testUserSummaryPage() throws Exception {

        given(this.companyService.getCompaniesForUsername("name"))
                .willReturn(companySet);

        this.mvc.perform(MockMvcRequestBuilders.get("/home")
                .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }




}

