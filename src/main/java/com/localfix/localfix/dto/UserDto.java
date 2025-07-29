package com.localfix.localfix.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private int id;
    private String email;
    private String password;
    private String confirmPassword;
    private String role;
    private String name;
    private String serviceCategory;
    private String location;
    private String profileImage;
    private double averageRating;
}
