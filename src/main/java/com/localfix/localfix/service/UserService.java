package com.localfix.localfix.service;

import com.localfix.localfix.dto.ProfileResponse;
import com.localfix.localfix.dto.Response;
import com.localfix.localfix.dto.UserDto;
import com.localfix.localfix.model.User;
import com.localfix.localfix.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    TokenBlackList tokenBlackList;

    @Autowired
    UserRepo userRepo;


    public ResponseEntity<List<UserDto>> getAllCustomers(String token) {
        if (!tokenBlackList.isTokenBlacklisted(token)) {

            try{
                String role = "customer";

                List<User> users = userRepo.findByRole(role);
                List<UserDto> userDtos = users.stream().map(user -> {

                    UserDto userDto = new UserDto();
                    userDto.setId(user.getId());
                    userDto.setEmail(user.getEmail());
                    userDto.setRole(user.getRole());

                    return  userDto;
                }).toList();

                return ResponseEntity.ok(userDtos);

            } catch (RuntimeException e) {
                throw new RuntimeException("cant get all customers : " + e.getMessage());
            }

        }else {
            throw  new RuntimeException("You must login first!");
        }

    }

    public ResponseEntity<List<UserDto>> getAllWorkers(String token) {
        if (!tokenBlackList.isTokenBlacklisted(token)) {

            try{
                String role = "worker";

                List<User> users = userRepo.findByRole(role);
                List<UserDto> userDtos = users.stream().map(user -> {

                    UserDto userDto = new UserDto();
                    userDto.setId(user.getId());
                    userDto.setEmail(user.getEmail());
                    userDto.setRole(user.getRole());

                    return  userDto;
                }).toList();

                return ResponseEntity.ok(userDtos);

            } catch (RuntimeException e) {
                throw new RuntimeException("cant get all workers : " + e.getMessage());
            }

        }else {
            throw  new RuntimeException("You must login first!");
        }

    }


    public Response deleteUser(String token, int id) {

        if (!tokenBlackList.isTokenBlacklisted(token)) {

            try{
                User user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found!"));
                userRepo.delete(user);
                return new Response("User deleted successfully", true);

            }catch (Exception e){
                return new Response("cant delete user : " + e.getMessage(), false);
            }

        }else {
           return new Response("You must login first!", false);
        }
    }
}
