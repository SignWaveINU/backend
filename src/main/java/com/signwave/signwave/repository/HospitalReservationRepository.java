package com.signwave.signwave.repository;

import com.signwave.signwave.entity.HospitalReservation;
import com.signwave.signwave.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HospitalReservationRepository extends JpaRepository<HospitalReservation, Long> {
    List<HospitalReservation> findByMember(Member member);
}
