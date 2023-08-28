package com.jobs.app.service;

import com.jobs.app.enums.UserRole;
import com.jobs.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List findAllUsers(UserRole userRole) {
        return userRepository.findAllUsersByUserRole(userRole);
    }

    public List findAllConsultants() {
        return new ArrayList();
    }

}
