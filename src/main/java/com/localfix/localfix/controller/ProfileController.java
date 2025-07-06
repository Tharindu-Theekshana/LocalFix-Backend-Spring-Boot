package com.localfix.localfix.controller;

import com.localfix.localfix.dto.ProfileDto;
import com.localfix.localfix.dto.ProfileResponse;
import com.localfix.localfix.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/profile")
public class ProfileController {

    @Autowired
    ProfileService profileService;

    @PostMapping("/createProfile")
    public ResponseEntity<ProfileResponse> createProfile(@RequestHeader("Authorization") String token,@RequestPart("profileImage")MultipartFile profileImage, @RequestPart("profile") ProfileDto profileDto, @RequestPart("images")List<MultipartFile> images){

        ProfileResponse response = profileService.createProfile(token,profileImage,profileDto, images);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/updateProfileStatus/{id}")
    public ResponseEntity<ProfileResponse> updateProfileStatus(@RequestHeader("Authorization") String token,@PathVariable int id, @RequestParam String status){

        ProfileResponse response = profileService.updateProfileStatus(token,id,status);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAllProfiles")
    public ResponseEntity<List<ProfileDto>> getAllProfiles(@RequestHeader("Authorization") String token){
        return profileService.getAllProfiles(token);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<ProfileDto> getById(@PathVariable int id){
        return profileService.getProfileById(id);
    }


}
