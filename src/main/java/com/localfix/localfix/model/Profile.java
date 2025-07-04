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
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "profiles")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Service_category is required")
    @Column(name = "service_category")
    private String serviceCategory;

    @NotBlank(message = "location is required")
    private String location;

    @Positive(message = "price is required")
    private double price;

    @NotBlank(message = "Bio is required")
    private String bio;

    @NotBlank(message = "Description is required")
    private String description;

    @CreationTimestamp
    @NotNull(message = "created_date is required")
    @Column(name = "created_date")
    private LocalDate createdDate;

    @Positive(message = "phone_number is required")
    @Column(name = "phone_number")
    private int phoneNumber;

    @NotBlank(message = "status is required")
    private String status = "pending";

    @Positive(message = "Experience is required")
    private int experience;

    @Lob
    @Column(nullable = false, columnDefinition = "LONGBLOB")
    private byte[] image;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "worker_id", nullable = false)
    private User worker;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Image> images;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Booking> bookings;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Review> reviews;
}
