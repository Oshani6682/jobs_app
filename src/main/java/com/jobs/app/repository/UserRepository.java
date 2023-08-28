package com.jobs.app.repository;

import com.jobs.app.domain.User;
import com.jobs.app.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findAllUsersByUserRole(UserRole userRole);

}
