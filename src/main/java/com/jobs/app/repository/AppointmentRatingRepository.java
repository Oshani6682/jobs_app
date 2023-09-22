package com.jobs.app.repository;

import com.jobs.app.domain.AppointmentRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRatingRepository extends JpaRepository<AppointmentRating, Integer> {

    AppointmentRating findByAppointmentId(Integer appointmentId);

}
