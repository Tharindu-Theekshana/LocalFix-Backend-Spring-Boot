package com.localfix.localfix.controller;

import com.localfix.localfix.dto.BookingDto;
import com.localfix.localfix.dto.Response;
import com.localfix.localfix.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    @Autowired
    BookingService bookingService;

    @PostMapping("/makeBooking")
    public ResponseEntity<Response> makeBooking(@RequestHeader("Authorization") String token, @RequestBody BookingDto bookingDto){

        Response response = bookingService.makeBooking(token,bookingDto);
        return ResponseEntity.ok(response);
    }
}
