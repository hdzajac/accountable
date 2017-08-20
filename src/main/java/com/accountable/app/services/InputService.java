package com.accountable.app.services;

import com.accountable.app.entities.Input;
import com.accountable.app.repositories.InputRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("inputService")
public class InputService {

    private final InputRepository inputRepository;

    @Autowired
    public InputService(InputRepository inputRepository) {
        this.inputRepository = inputRepository;
    }

    public void saveInput(Input input) {
        inputRepository.save(input);
    }
}
