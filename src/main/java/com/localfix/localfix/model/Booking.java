package com.localfix.localfix.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @CreationTimestamp
    @NotNull(message = "booked_date is required")
    @Column(name = "booked_date")
    private LocalDate bookedDate;

    @NotNull(message = "booking date is required")
    @Column(name = "booking_date")
    private LocalDate bookingDate;

    @NotNull(message = "booking time is required")
    @Column(name = "booking_time")
    private LocalTime bookingTime;

    @NotBlank(message = "description is required")
    private String description;

    @NotBlank(message = "location is required")
    private String location;

    @NotBlank(message = "urgency is required")
    private String urgency;

    @NotBlank(message = "status is required")
    private String status = "pending";

    @Positive(message = "telephone number is required")
    @Column(name = "phone_number")
    private int phoneNumber;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;


}
