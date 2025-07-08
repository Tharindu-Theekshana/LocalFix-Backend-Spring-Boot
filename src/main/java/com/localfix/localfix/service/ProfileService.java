package com.localfix.localfix.service;

import com.localfix.localfix.dto.ProfileDto;
import com.localfix.localfix.dto.ProfileResponse;
import com.localfix.localfix.model.Image;
import com.localfix.localfix.model.Profile;
import com.localfix.localfix.model.User;
import com.localfix.localfix.repository.ImageRepo;
import com.localfix.localfix.repository.ProfileRepo;
import com.localfix.localfix.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    ProfileRepo profileRepo;

    @Autowired
    TokenBlackList tokenBlackList;

    @Autowired
    ImageRepo imageRepo;

    public ProfileResponse createProfile(String token,MultipartFile profileImage,ProfileDto profileDto, List<MultipartFile> images) {

        if (!tokenBlackList.isTokenBlacklisted(token)) {
            try {
                User worker = userRepo.findById(profileDto.getWorkerId()).orElseThrow(() -> new RuntimeException("Worker not found!"));

                Profile profile = new Profile();
                profile.setName(profileDto.getName());
                profile.setBio(profileDto.getBio());
                profile.setDescription(profileDto.getDescription());
                profile.setLocation(profileDto.getLocation());
                profile.setExperience(profileDto.getExperience());
                profile.setPhoneNumber(profileDto.getPhoneNumber());
                profile.setServiceCategory(profileDto.getServiceCategory());
                profile.setPrice(profileDto.getPrice());
                profile.setWorker(worker);

                if(profileImage != null && !profileImage.isEmpty()){
                    profile.setImage(profileImage.getBytes());
                }

                Profile savedProfile = profileRepo.save(profile);

                List<Image> imageList = new ArrayList<>();
                if (images != null && !images.isEmpty()) {
                    for (MultipartFile file : images) {
                        if (!file.isEmpty()) {
                            Image img = new Image();
                            img.setData(file.getBytes());
                            img.setContentype(file.getContentType());
                            img.setProfile(savedProfile);
                            imageList.add(img);
                        }

                    }
                    imageRepo.saveAll(imageList);
                }
                return new ProfileResponse("Profile Created Successfully!",true);
            } catch (Exception e) {
                return new ProfileResponse("Can't create profile : " + e.getMessage(), false);
            }
        } else {
            return new ProfileResponse("You must login to create profile!", false);
        }

    }

    public ProfileResponse updateProfileStatus(String token, int id, String status) {
        if (!tokenBlackList.isTokenBlacklisted(token)) {
            try {

                Profile profile = profileRepo.findById(id).orElseThrow(() -> new RuntimeException("no profile found!"));

                profile.setStatus(status);
                profileRepo.save(profile);

                return new ProfileResponse("profile status updated successfully!", true);

            } catch (Exception e) {
                return new ProfileResponse("Cant update status : " + e.getMessage(), false);
            }
        }else {
            return new ProfileResponse("You must login to update profile status!", false);
        }
    }

    public ResponseEntity<List<ProfileDto>> getAllProfiles(String token) {

        if (!tokenBlackList.isTokenBlacklisted(token)) {

            try{

                List<Profile> profiles = profileRepo.findAll();
                List<ProfileDto> profileDtos = profiles.stream().map(profile -> {

                    ProfileDto profileDto = new ProfileDto();
                    profileDto.setId(profile.getId());
                    profileDto.setName(profile.getName());
                    profileDto.setBio(profile.getBio());
                    profileDto.setLocation(profile.getLocation());
                    profileDto.setExperience(profile.getExperience());
                    profileDto.setDescription(profile.getDescription());
                    profileDto.setPrice(profile.getPrice());
                    profileDto.setServiceCategory(profile.getServiceCategory());
                    profileDto.setPhoneNumber(profile.getPhoneNumber());
                    profileDto.setStatus(profile.getStatus());
                    profileDto.setWorkerId(profile.getWorker().getId());

                    if(profile.getImage() != null){
                        String profileImage = Base64.getEncoder().encodeToString(profile.getImage());
                        profileDto.setProfileImage(profileImage);
                    }

                    return profileDto;

                }).toList();

                return ResponseEntity.ok(profileDtos);

            } catch (RuntimeException e) {
                throw new RuntimeException("Cant get all profiles : " + e.getMessage());
            }

        }else {
            throw  new RuntimeException("You must login to update profile status!");
        }
    }

    public ResponseEntity<ProfileDto> getProfileById(int id) {

        try{
            Profile profile = profileRepo.findById(id).orElseThrow(()-> new RuntimeException("no profile!"));

            ProfileDto profileDto = new ProfileDto();
            profileDto.setId(profile.getId());
            profileDto.setName(profile.getName());
            profileDto.setBio(profile.getBio());
            profileDto.setLocation(profile.getLocation());
            profileDto.setExperience(profile.getExperience());
            profileDto.setDescription(profile.getDescription());
            profileDto.setPrice(profile.getPrice());
            profileDto.setServiceCategory(profile.getServiceCategory());
            profileDto.setPhoneNumber(profile.getPhoneNumber());
            profileDto.setStatus(profile.getStatus());
            profileDto.setWorkerId(profile.getWorker().getId());

            if(profile.getImage() != null){
                String profileImage = Base64.getEncoder().encodeToString(profile.getImage());
                profileDto.setProfileImage(profileImage);
            }

            List<Image> images = imageRepo.findByProfileId(profile.getId());
            List<String> imageBase64List = images.stream().map(img -> "data:" + img.getContentype() + ";base64," + Base64.getEncoder().encodeToString(img.getData())).toList();
            profileDto.setImages(imageBase64List);

            return ResponseEntity.ok(profileDto);

        } catch (RuntimeException e) {
            throw new RuntimeException("cant get profile by id " + e.getMessage());
        }
    }

    public ResponseEntity<List<ProfileDto>> getProfilesByStatus(String token,String status) {

        if (!tokenBlackList.isTokenBlacklisted(token)) {
            try{

                List<Profile> profiles = profileRepo.findByStatus(status);
                List<ProfileDto> profileDtos = profiles.stream().map(profile -> {

                    ProfileDto profileDto = new ProfileDto();
                    profileDto.setId(profile.getId());
                    profileDto.setName(profile.getName());
                    profileDto.setBio(profile.getBio());
                    profileDto.setLocation(profile.getLocation());
                    profileDto.setExperience(profile.getExperience());
                    profileDto.setDescription(profile.getDescription());
                    profileDto.setPrice(profile.getPrice());
                    profileDto.setServiceCategory(profile.getServiceCategory());
                    profileDto.setPhoneNumber(profile.getPhoneNumber());
                    profileDto.setStatus(profile.getStatus());
                    profileDto.setWorkerId(profile.getWorker().getId());

                    if(profile.getImage() != null){
                        String profileImage = Base64.getEncoder().encodeToString(profile.getImage());
                        profileDto.setProfileImage(profileImage);
                    }

                    return profileDto;
                }).toList();

                return ResponseEntity.ok(profileDtos);


            } catch (RuntimeException e) {
                throw new RuntimeException("cant get pending events : " + e.getMessage());
            }

        }else {
            throw  new RuntimeException("You must login first!");
        }
    }


    public ResponseEntity<List<ProfileDto>> getProfilesByCategory(String category) {

        try{

            String status = "approved";

            List<Profile> profiles = profileRepo.findByStatusAndServiceCategory(status, category);
            List<ProfileDto> profileDtos = profiles.stream().map(profile -> {

                ProfileDto profileDto = new ProfileDto();
                profileDto.setId(profile.getId());
                profileDto.setName(profile.getName());
                profileDto.setBio(profile.getBio());
                profileDto.setLocation(profile.getLocation());
                profileDto.setExperience(profile.getExperience());
                profileDto.setDescription(profile.getDescription());
                profileDto.setPrice(profile.getPrice());
                profileDto.setServiceCategory(profile.getServiceCategory());
                profileDto.setPhoneNumber(profile.getPhoneNumber());
                profileDto.setWorkerId(profile.getWorker().getId());

                if(profile.getImage() != null){
                    String profileImage = Base64.getEncoder().encodeToString(profile.getImage());
                    profileDto.setProfileImage(profileImage);
                }

                return profileDto;
            }).toList();

            return ResponseEntity.ok(profileDtos);

        } catch (RuntimeException e) {
            throw new RuntimeException("cant get profiles by category : " + e.getMessage());
        }
    }

    public ProfileResponse updateProfile(String token, ProfileDto profileDto, int id,List<MultipartFile> images,MultipartFile profileImage) {
        if (!tokenBlackList.isTokenBlacklisted(token)) {

            try{

                Profile profile = profileRepo.findById(id).orElseThrow(()-> new RuntimeException("no profile found!"));

                profile.setName(profileDto.getName());
                profile.setBio(profileDto.getBio());
                profile.setLocation(profileDto.getLocation());
                profile.setExperience(profileDto.getExperience());
                profile.setDescription(profileDto.getDescription());
                profile.setPrice(profileDto.getPrice());
                profile.setServiceCategory(profileDto.getServiceCategory());
                profile.setPhoneNumber(profileDto.getPhoneNumber());

                if(profileImage != null && !profileImage.isEmpty()){
                    profile.setImage(profileImage.getBytes());
                }

                Profile savedProfile = profileRepo.save(profile);

                List<Image> imageList = new ArrayList<>();
                if (images != null && !images.isEmpty()) {
                    for (MultipartFile file : images) {
                        if (!file.isEmpty()) {
                            Image img = new Image();
                            img.setData(file.getBytes());
                            img.setContentype(file.getContentType());
                            img.setProfile(savedProfile);
                            imageList.add(img);
                        }

                    }
                    imageRepo.saveAll(imageList);
                }

                return new ProfileResponse("Profile updated successfully!",true);


            }catch (Exception e){
                return new ProfileResponse("cant update profile! : " + e.getMessage(), false);
            }

        }else {
            return new ProfileResponse("You must login to update profile status!", false);
        }
    }

    public ResponseEntity<List<ProfileDto>> searchProfile(String category, String location) {
        try{
            String status = "approved";
            List<Profile> profiles;

            if(location == null || location.trim().isEmpty()){
                 profiles = profileRepo.findByStatusAndServiceCategory(status,category);
            } else if (category == null) {
                 profiles = profileRepo.findByStatusAndLocation(status,location);
            }else {
                 profiles = profileRepo.findByStatusAndServiceCategoryAndLocation(status, category, location);

            }
            List<ProfileDto> profileDtos = profiles.stream().map(profile -> {

                ProfileDto profileDto = new ProfileDto();
                profileDto.setId(profile.getId());
                profileDto.setName(profile.getName());
                profileDto.setBio(profile.getBio());
                profileDto.setLocation(profile.getLocation());
                profileDto.setExperience(profile.getExperience());
                profileDto.setDescription(profile.getDescription());
                profileDto.setPrice(profile.getPrice());
                profileDto.setServiceCategory(profile.getServiceCategory());
                profileDto.setPhoneNumber(profile.getPhoneNumber());
                profileDto.setWorkerId(profile.getWorker().getId());

                if(profile.getImage() != null){
                    String profileImage = Base64.getEncoder().encodeToString(profile.getImage());
                    profileDto.setProfileImage(profileImage);
                }

                return profileDto;
            }).toList();

            return ResponseEntity.ok(profileDtos);

        } catch (RuntimeException e) {
            throw new RuntimeException("can search profile : " + e.getMessage());
        }
    }
}
