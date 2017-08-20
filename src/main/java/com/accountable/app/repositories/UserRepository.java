package com.accountable.app.repositories;


import com.accountable.app.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long>{

    User findByUsername(String username);

}
