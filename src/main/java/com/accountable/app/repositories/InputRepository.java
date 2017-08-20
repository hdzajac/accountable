package com.accountable.app.repositories;

import com.accountable.app.entities.Input;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InputRepository extends JpaRepository<Input, Long> {

    Input findById(int id);

}
