package com.localfix.localfix.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {

    private int id;
    private LocalDate bookedDate;
    private LocalDate bookingDate;
    private LocalTime bookingTime;
    private String description;
    private String location;
    private String urgency;
    private long phoneNumber;
    private String status;
    private int customerId;
    private int profileId;
}
