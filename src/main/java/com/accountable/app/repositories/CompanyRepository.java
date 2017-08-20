package com.accountable.app.repositories;


import com.accountable.app.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface CompanyRepository extends JpaRepository<Company, Long>{

    Company findByName(String name);
    Company findById(Integer id);

    @Query(value = "SELECT * from company as c inner join users as u on c.user_id=u.id where u.username like :user", nativeQuery = true)
    Set<Company> findAllByUsername(@Param("user") String username);

}
