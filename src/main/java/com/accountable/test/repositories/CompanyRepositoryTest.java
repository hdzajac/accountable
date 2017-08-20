package com.accountable.test.repositories;


import com.accountable.app.entities.Company;
import com.accountable.app.entities.User;
import com.accountable.app.repositories.CompanyRepository;
import com.accountable.app.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class CompanyRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CompanyRepository companyRepository;

    private User user;
    private Set<Long> ids;


    @Before
    public void setup(){
        ids = new HashSet<Long>();

        user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setEnabled(1);
        entityManager.persist(user);

        User user2 = new User();
        user2.setUsername("username2");
        user2.setPassword("password2");
        user2.setEnabled(1);
        entityManager.persist(user2);

        for (int i = 0; i < 10; i ++){

            Company company = new Company();
            company.setName("company" + i);

            if(i%2 == 0) {
                company.setUser(user);
                user.addCompany(company);
                entityManager.persist(company);
                ids.add(companyRepository.findByName("company" + i).getId());
            }
            else {
                company.setUser(user2);
                user2.addCompany(company);
                entityManager.persist(company);
            }
        }

    }


    @Test
    public void testFindAllForUser() {

        Set<Company> fetched = companyRepository.findAllByUsername("username");
        Set<Long> fetchedIds = fetched.stream().map(Company::getId).collect(Collectors.toCollection(HashSet::new));


        assertEquals(fetched.size(),5);
        assertTrue(ids.containsAll(fetchedIds));

    }
}
