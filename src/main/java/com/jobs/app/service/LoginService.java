package com.jobs.app.service;

import com.jobs.app.dto.LoginDTO;
import com.jobs.app.dto.UserDTO;
import com.jobs.app.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private Logger LOGGER = LoggerFactory.getLogger(LoginService.class);

    @Autowired
    private UserRepository userRepository;

    public UserDTO login(LoginDTO loginDTO) {
        LOGGER.info("User login - Started");
        return userRepository.findUserByUsernameAndPassword(
            loginDTO.getUsername(), loginDTO.getPassword()
        );
    }

}
