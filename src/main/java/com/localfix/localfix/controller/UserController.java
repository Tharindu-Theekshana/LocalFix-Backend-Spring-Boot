package com.localfix.localfix.controller;

import com.localfix.localfix.dto.ProfileDto;
import com.localfix.localfix.dto.ProfileResponse;
import com.localfix.localfix.dto.Response;
import com.localfix.localfix.dto.UserDto;
import com.localfix.localfix.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/getAllCustomers")
    public ResponseEntity<List<UserDto>> getAllCustomers(@RequestHeader("Authorization") String token){
        return userService.getAllCustomers(token);
    }

    @GetMapping("/getAllWorkers")
    public ResponseEntity<List<UserDto>> getAllWorkers(@RequestHeader("Authorization") String token){
        return userService.getAllWorkers(token);
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<Response> deleteUser(@RequestHeader("Authorization") String token, @PathVariable int id){

        Response response = userService.deleteUser(token,id);
        return ResponseEntity.ok(response);
    }
}
