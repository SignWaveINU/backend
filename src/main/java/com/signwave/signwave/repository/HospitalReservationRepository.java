package com.signwave.signwave.repository;

import com.signwave.signwave.entity.HospitalReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalReservationRepository extends JpaRepository<HospitalReservation, Long> {
}
