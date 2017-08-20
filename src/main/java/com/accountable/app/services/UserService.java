package com.accountable.app.services;

import com.accountable.app.entities.User;
import com.accountable.app.entities.Role;
import com.accountable.app.repositories.UserRepository;
import com.accountable.app.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;


@Service("userService")
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User findByUsername(String userName) {
        return userRepository.findByUsername(userName);
    }

    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEnabled(1);
        Role role = roleRepository.findByRole("ADMIN");
        user.setRole(new HashSet<Role>(Collections.singletonList(role)));
        userRepository.save(user);
    }

}
