package com.jobs.app.repository;

import com.jobs.app.domain.Appointment;
import com.jobs.app.dto.AppointmentDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    @Query("select new com.jobs.app.dto.AppointmentDTO(appointment) from Appointment appointment")
    List<AppointmentDTO> findAllAppointments();

    @Query("select new com.jobs.app.dto.AppointmentDTO(appointment) from Appointment appointment" +
            " where appointment.jobSeeker.id=:jobSeeker")
    List<AppointmentDTO> findAllAppointmentsOfJobSeeker(int jobSeeker);

    @Query("select new com.jobs.app.dto.AppointmentDTO(appointment) from Appointment appointment" +
            " where appointment.consultant.id=:consultant")
    List<AppointmentDTO> findAllAppointmentsOfConsultant(int consultant);

    @Query("select new com.jobs.app.dto.AppointmentDTO(appointment) from Appointment appointment" +
        " where appointment.consultant.id=:consultant and appointment.appointmentDate=:date")
    List<AppointmentDTO> findAllAppointmentsOfConsultantAndDate(Integer consultant, BigInteger date);

}
