package com.jobs.app.service;

import com.jobs.app.domain.Consultant;
import com.jobs.app.domain.Country;
import com.jobs.app.domain.Sector;
import com.jobs.app.domain.User;
import com.jobs.app.dto.ConsultantDTO;
import com.jobs.app.dto.CreateConsultantDTO;
import com.jobs.app.dto.CreateUserDTO;
import com.jobs.app.dto.UserDTO;
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

import java.util.ArrayList;
import java.util.List;
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
        LOGGER.info("Save Consultant: {} - Successful", consultantDTO.getUserName());

        return new ConsultantDTO(consultant);
    }

    private User saveUser(CreateUserDTO userDTO, UserRole userRole) {
        LOGGER.info("Save new user - {}: {}", userRole.toString(), userDTO.getUserName());
        User user = new User(userDTO);
        user.userName = userDTO.getUserName();
        user.password = userDTO.getPassword();
        user.userRole = userRole;

        user = userRepository.save(user);
        LOGGER.info("Save new user - {}: {} - Successful", userRole.toString(), user.userName);
        return user;
    }

}
