package com.localfix.localfix.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto {

    private int id;
    private String name;
    private String serviceCategory;
    private String location;
    private double price;
    private String bio;
    private String description;
    private long phoneNumber;
    private int experience;
    private int workerId;
    private String profileImage;
    private List<String> images;
    private String status;
    private int completedJobsCount;
    private double averageRating;
    private List<Integer> existingImages;
}
