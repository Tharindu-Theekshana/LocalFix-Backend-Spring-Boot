package com.localfix.localfix.controller;

import com.localfix.localfix.dto.BookingDto;
import com.localfix.localfix.dto.Response;
import com.localfix.localfix.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    //get pending/approved/cancelled/declined bookings of each worker
    @GetMapping("/getBookingsOfEachWorker/{id}")
    public ResponseEntity<List<BookingDto>> getBookingsOfEachWorker(@RequestHeader("Authorization") String token,@PathVariable int id,@RequestParam String status){
        return bookingService.getBookingsOfEachWorker(token,id,status);
    }

    @GetMapping("/getAllBookingsOfEachWorker/{id}")
    public ResponseEntity<List<BookingDto>> getAllBookingsOfEachWorker(@RequestHeader("Authorization") String token,@PathVariable int id){
        return bookingService.getAllBookingsOfEachWorker(token,id);
    }


    //approved/decline/cancelled or completed , work for both customer and worker
    @PutMapping("/updateBookingStatus/{id}")
    public ResponseEntity<Response> updateBookingStatus(@RequestHeader("Authorization") String token,@PathVariable int id,@RequestParam String status){
        Response response = bookingService.updateBookingStatus(token,id,status);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getBookingsOfEachCustomer/{id}")
    public ResponseEntity<List<BookingDto>> getBookingsOfEachCustomer(@RequestHeader("Authorization") String token,@PathVariable int id){
        return bookingService.getBookingsOfEachCustomer(token,id);
    }

    @PutMapping("/editBooking")
    public ResponseEntity<Response> editBooking(@RequestHeader("Authorization") String token,@RequestBody BookingDto bookingDto){

        Response response = bookingService.editBooking(token,bookingDto);
        return ResponseEntity.ok(response);
    }

}
