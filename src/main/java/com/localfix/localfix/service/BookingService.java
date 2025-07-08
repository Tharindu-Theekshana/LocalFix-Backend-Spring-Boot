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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public ResponseEntity<List<BookingDto>> getBookingsOfEachWorker(String token, int id) {
        if (!tokenBlackList.isTokenBlacklisted(token)) {

            try{

                String status = "pending";

                Profile profile = profileRepo.findById(id).orElseThrow(()-> new RuntimeException("no profile found"));
                List<Booking> bookings = bookingRepo.findByProfileAndStatus(profile,status);
                List<BookingDto> bookingDtos = bookings.stream().map(booking -> {

                    BookingDto bookingDto = new BookingDto();
                    bookingDto.setId(booking.getId());
                    bookingDto.setBookedDate(booking.getBookedDate());
                    bookingDto.setBookingDate(booking.getBookingDate());
                    bookingDto.setBookingTime(booking.getBookingTime());
                    bookingDto.setDescription(booking.getDescription());
                    bookingDto.setUrgency(booking.getUrgency());
                    bookingDto.setLocation(booking.getLocation());
                    bookingDto.setPhoneNumber(booking.getPhoneNumber());
                    bookingDto.setStatus(booking.getStatus());

                    return bookingDto;
                }).toList();

                return ResponseEntity.ok(bookingDtos);

            } catch (RuntimeException e) {
                throw new RuntimeException("cant get bookings : " + e.getMessage());
            }

        }else {
            throw  new RuntimeException("You must login to update profile status!");
        }
    }
}
