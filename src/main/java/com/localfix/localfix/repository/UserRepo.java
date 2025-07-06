package com.localfix.localfix.repository;

import com.localfix.localfix.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);


    Optional<User> findByEmail(String email);
}
