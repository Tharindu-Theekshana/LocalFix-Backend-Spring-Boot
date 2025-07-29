package com.localfix.localfix.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {


    private String message;
    private boolean success;
    private String token;
    private String role;
    private boolean isLoggedIn;
    private Integer userId;
    private String email;
}
