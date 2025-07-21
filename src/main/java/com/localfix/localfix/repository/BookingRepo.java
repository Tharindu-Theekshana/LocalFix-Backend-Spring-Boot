package com.localfix.localfix.repository;

import com.localfix.localfix.model.Booking;
import com.localfix.localfix.model.Profile;
import com.localfix.localfix.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingRepo extends JpaRepository<Booking, Integer> {


    List<Booking> findByProfileAndStatus(Profile profile, String status);

    List<Booking> findByCustomer(User customer);

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.profile.id = :profileId AND b.status = 'completed'")
    int countCompletedJobsByProfile(@Param("profileId") int profileId);

    List<Booking> findByProfile(Profile profile);
}
