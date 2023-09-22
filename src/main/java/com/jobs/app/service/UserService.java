package com.jobs.app.service;

import com.jobs.app.domain.Consultant;
import com.jobs.app.domain.Country;
import com.jobs.app.domain.Sector;
import com.jobs.app.domain.User;
import com.jobs.app.dto.*;
import com.jobs.app.enums.ErrorCodes;
import com.jobs.app.enums.UserRole;
import com.jobs.app.exception.JobApiException;
import com.jobs.app.repository.ConsultantRepository;
import com.jobs.app.repository.CountryRepository;
import com.jobs.app.repository.SectorRepository;
import com.jobs.app.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConsultantRepository consultantRepository;

    @Autowired
    private SectorRepository sectorRepository;

    @Autowired
    private CountryRepository countryRepository;

    public List<UserDTO> findAllUsers(UserRole userRole) {
        LOGGER.info("Find all users of type: {}", userRole.toString());
        return userRepository.findAllUsersByUserRole(userRole);
    }

    public UserDTO saveJobSeeker(CreateUserDTO createUserDTO) {
        return new UserDTO(saveUser(createUserDTO, UserRole.JOB_SEEKER));
    }

    public List<ConsultantDTO> findAllConsultants() {
        LOGGER.info("Find all users of type: consultant");
        return consultantRepository.findAllConsultants();
    }

    public ConsultantDTO saveConsultant(CreateConsultantDTO consultantDTO) {
        Optional<Sector> sector = sectorRepository.findById(consultantDTO.getSector());
        if (!sector.isPresent()) {
            throw new JobApiException(
                "Sector id '" + consultantDTO.getSector() + "' not found",
                ErrorCodes.SECTOR_INVALID,
                HttpStatus.BAD_REQUEST
            );
        }

        Optional<Country> country = countryRepository.findById(consultantDTO.getCountry());
        if (!country.isPresent()) {
            throw new JobApiException(
                "Country id '" + consultantDTO.getSector() + "' not found",
                ErrorCodes.COUNTRY_INVALID,
                HttpStatus.BAD_REQUEST
            );
        }

        User user = saveUser(consultantDTO, UserRole.CONSULTANT);
        Consultant consultant = new Consultant();
        consultant.id = user.id;
        consultant.user = user;
        consultant.sector = sector.get();
        consultant.country = country.get();

        consultant = consultantRepository.save(consultant);
        LOGGER.info("Save Consultant: {} - Successful", consultantDTO.getUsername());

        return new ConsultantDTO(consultant);
    }

    public User saveUser(CreateUserDTO userDTO, UserRole userRole) {
        LOGGER.info("Save new user - {}: {}", userRole, userDTO.getUsername());
        User user = new User(userDTO);
        user.userName = userDTO.getUsername();
        user.password = userDTO.getPassword();
        user.userRole = userRole;

        user = userRepository.save(user);
        LOGGER.info("Save new user - {}: {} - Successful", userRole, user.userName);
        return user;
    }

    public void removeUser(Integer removeUser) {
        LOGGER.info("Remove user: {}", removeUser);

        User user = userRepository.findById(removeUser).get();
        user.isActive = false;
        userRepository.save(user);

        LOGGER.info("Remove user: {} - Successful", removeUser);
    }

    public ConsultantDTO findConsultantById(Integer id, HttpStatus statusOnNotFound) {
        LOGGER.info("Find user - Consultant: {}", id);

        ConsultantDTO consultant = consultantRepository.findConsultantById(id);
        if (Objects.isNull(consultant)) {
            throw new JobApiException(
                "Consultant not found",
                ErrorCodes.CONSULTANT_NOT_FOUND,
                statusOnNotFound
            );
        }

        LOGGER.info("Find user - Consultant: {}", id);
        return consultant;
    }

    public UserDTO findUser(LoginDTO loginDTO) {
        LOGGER.info("Find user by username - Started");

        UserDTO userDTO = userRepository.findUserByUsernameAndPassword(
            loginDTO.getUsername(), loginDTO.getPassword()
        );

        if (Objects.isNull(userDTO)) {
            throw new JobApiException(
                "Invalid user credentials", ErrorCodes.INVALID_USER_CREDENTIALS, HttpStatus.BAD_REQUEST
            );
        }

        LOGGER.info("Find user by username: {} - Successful", userDTO.getFirstName());
        return userDTO;
    }

}
