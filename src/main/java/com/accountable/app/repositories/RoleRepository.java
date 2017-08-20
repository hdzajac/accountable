package com.accountable.app.repositories;

import com.accountable.app.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role, Long>{

    Role findByRole(String role);
}
