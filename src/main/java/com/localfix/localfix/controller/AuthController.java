package com.localfix.localfix.controller;

import com.localfix.localfix.dto.AuthResponse;
import com.localfix.localfix.dto.UserDto;
import com.localfix.localfix.service.AuthSerice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody UserDto userDto){

        AuthResponse response = authSerice.login(userDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<AuthResponse> logout(@RequestHeader("Authorization") String token){
        AuthResponse response = authSerice.logout(token);
        return ResponseEntity.ok(response);
    }
}
