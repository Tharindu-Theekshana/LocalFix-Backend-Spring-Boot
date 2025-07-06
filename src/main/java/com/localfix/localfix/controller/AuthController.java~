package com.localfix.localfix.controller;

import com.localfix.localfix.dto.AuthResponse;
import com.localfix.localfix.dto.UserDto;
import com.localfix.localfix.service.AuthSerice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthSerice authSerice;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody UserDto userDto){

        AuthResponse response = authSerice.register(userDto);
        return ResponseEntity.ok(response);
    }
}
