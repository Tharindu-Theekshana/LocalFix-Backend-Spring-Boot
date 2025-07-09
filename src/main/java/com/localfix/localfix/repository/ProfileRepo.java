package com.localfix.localfix.repository;

import com.localfix.localfix.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfileRepo extends JpaRepository<Profile, Integer> {
    List<Profile> findByStatus(String status);

    List<Profile> findByStatusAndServiceCategory(String status, String category);

    List<Profile> findByStatusAndServiceCategoryAndLocation(String status, String category, String location);

    List<Profile> findByStatusAndLocation(String status, String location);


    Profile findByWorkerId(int id);
}
