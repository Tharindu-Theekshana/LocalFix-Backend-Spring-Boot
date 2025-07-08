package com.localfix.localfix.service;

import com.localfix.localfix.dto.BookingDto;
import com.localfix.localfix.dto.Response;
import com.localfix.localfix.model.Booking;
import com.localfix.localfix.model.Profile;
import com.localfix.localfix.model.User;
import com.localfix.localfix.repository.BookingRepo;
import com.localfix.localfix.repository.ProfileRepo;
import com.localfix.localfix.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingService {

    @Autowired
    TokenBlackList tokenBlackList;

    @Autowired
    BookingRepo bookingRepo;

    @Autowired
    ProfileRepo profileRepo;

    @Autowired
    UserRepo userRepo;


    public Response makeBooking(String token, BookingDto bookingDto) {
        if (!tokenBlackList.isTokenBlacklisted(token)) {

            try{
                Profile profile = profileRepo.findById(bookingDto.getProfileId()).orElseThrow(()-> new RuntimeException("no profile found!"));
                User customer = userRepo.findById(bookingDto.getCustomerId()).orElseThrow(()-> new RuntimeException("no user found!"));

                Booking booking = new Booking();
                booking.setBookingDate(bookingDto.getBookingDate());
                booking.setBookingTime(bookingDto.getBookingTime());
                booking.setDescription(bookingDto.getDescription());
                booking.setLocation(bookingDto.getLocation());
                booking.setPhoneNumber(bookingDto.getPhoneNumber());
                booking.setUrgency(bookingDto.getUrgency());
                booking.setProfile(profile);
                booking.setCustomer(customer);

                bookingRepo.save(booking);

                return new Response("booking made successfully!", true);

            }catch (Exception e){
                return new Response("Cant make booking : " + e.getMessage(), false);
            }

        }else {
            return new Response("You must login first!", false);
        }
    }
}
