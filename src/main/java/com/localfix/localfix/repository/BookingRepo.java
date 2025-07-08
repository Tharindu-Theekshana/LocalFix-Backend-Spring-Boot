package com.localfix.localfix.repository;

import com.localfix.localfix.model.Booking;
import com.localfix.localfix.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepo extends JpaRepository<Booking, Integer> {


    List<Booking> findByProfileAndStatus(Profile profile, String status);
}
