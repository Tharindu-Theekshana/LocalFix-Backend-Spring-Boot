package com.localfix.localfix.repository;

import com.localfix.localfix.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {
}
