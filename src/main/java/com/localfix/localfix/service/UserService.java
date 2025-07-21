package com.localfix.localfix.service;

import com.localfix.localfix.dto.ProfileResponse;
import com.localfix.localfix.dto.Response;
import com.localfix.localfix.dto.UserDto;
import com.localfix.localfix.model.Booking;
import com.localfix.localfix.model.Profile;
import com.localfix.localfix.model.Review;
import com.localfix.localfix.model.User;
import com.localfix.localfix.repository.BookingRepo;
import com.localfix.localfix.repository.ProfileRepo;
import com.localfix.localfix.repository.ReviewRepo;
import com.localfix.localfix.repository.UserRepo;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    TokenBlackList tokenBlackList;

    @Autowired
    UserRepo userRepo;

    @Autowired
    ProfileRepo profileRepo;

    @Autowired
    ReviewRepo reviewRepo;

    @Autowired
    BookingRepo bookingRepo;

    @Autowired
    private EntityManager entityManager;


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

                    Profile profile = profileRepo.findById(user.getId()).orElseThrow(()-> new RuntimeException("Profile not found!"));
                    userDto.setName(profile.getName());
                    userDto.setLocation(profile.getLocation());
                    userDto.setServiceCategory(profile.getServiceCategory());

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

    @Transactional
    public Response deleteUser(String token,int id) {

        if (!tokenBlackList.isTokenBlacklisted(token)) {

            try {
                if (!userRepo.existsById(id)) {
                    return new Response("User not found!", false);
                }

                entityManager.createNativeQuery("DELETE FROM bookings WHERE customer_id = ?1")
                        .setParameter(1, id)
                        .executeUpdate();

                entityManager.createNativeQuery("DELETE FROM reviews WHERE customer_id = ?1")
                        .setParameter(1, id)
                        .executeUpdate();

                entityManager.createNativeQuery("DELETE FROM profiles WHERE worker_id = ?1")
                        .setParameter(1, id)
                        .executeUpdate();

                entityManager.createNativeQuery("DELETE FROM users WHERE id = ?1")
                        .setParameter(1, id)
                        .executeUpdate();

                return new Response("User deleted successfully", true);

            } catch (Exception e) {
                return new Response("cant delete user : " + e.getMessage(), false);
            }
        }else {
                return new Response("You must login first!",false);
            }

    }


}
