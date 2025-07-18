package com.localfix.localfix.service;

import com.localfix.localfix.dto.AuthResponse;
import com.localfix.localfix.dto.UserDto;
import com.localfix.localfix.model.User;
import com.localfix.localfix.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthSerice {

    @Autowired
    UserRepo userRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JWTService jwtService;

    @Autowired
    TokenBlackList tokenBlackList;

    public AuthResponse register(UserDto userDto) {

        if(userRepo.existsByEmail(userDto.getEmail())){
            return new AuthResponse("User already exists", false,null,null,false,null);
        }

        if(!userDto.getPassword().equals(userDto.getConfirmPassword())){
            return new AuthResponse("Passwords do not match", false,null,null,false,null);
        }

        try{

            User user = new User();
            user.setEmail(userDto.getEmail());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.setRole(userDto.getRole());

            User registeredUser = userRepo.save(user);
            return new AuthResponse("User " + registeredUser.getEmail() + " registered successfully", true,null,null,false, null);

        }catch (Exception e){
            return new AuthResponse(("cant register user! : " + e.getMessage()), false,null,null,false,null);
        }
    }


    public AuthResponse login(UserDto userDto) {

        try{

            User user = userRepo.findByEmail(userDto.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found!"));

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword()));

            if(authentication.isAuthenticated()){

                String token = jwtService.generateToken(user.getEmail());
                return new AuthResponse("User " + user.getEmail() + " logged in successfully", true,token,user.getRole(),true, user.getId());
            }else{
                return new AuthResponse("User " + user.getEmail() + " logged in failed", false,null,null,false,null);
            }

        } catch (Exception e) {
            return new AuthResponse(("cant login user! : " + e.getMessage()), false,null,null,false,null);
        }
    }

    public AuthResponse logout(String token) {

        if(tokenBlackList.isTokenBlacklisted(token)){
            return new AuthResponse("User already logged out", false,null,null,false,null);
        }

        try{

            if (token != null) {
                tokenBlackList.blacklisToken(token);
                return new AuthResponse("User logged out successfully", true,null,null,false,null);
            }
            return new AuthResponse("Invalid token! ", false,null,null,false,null);

        } catch (Exception e) {
            return new AuthResponse(("cant logout user! : " + e.getMessage()), false,null,null,false,null);
        }
    }
}
