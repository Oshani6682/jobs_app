package com.jobs.app.repository;

import com.jobs.app.domain.User;
import com.jobs.app.dto.UserDTO;
import com.jobs.app.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "select new com.jobs.app.dto.UserDTO(user) from User user where user.userRole=:userRole and user.isActive=true")
    List<UserDTO> findAllUsersByUserRole(UserRole userRole);

    @Query(value = "select new com.jobs.app.dto.UserDTO(user, true) from User user where user.userName=:username " +
        "and user.password=:password")
    UserDTO findUserByUsernameAndPassword(String username, String password);

    @Query(value = "select new com.jobs.app.dto.UserDTO(user, true) from User user where user.userName=:username")
    UserDTO findUserByUsername(String username);

}
