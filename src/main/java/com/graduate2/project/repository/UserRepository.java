package com.graduate2.project.repository;

import com.graduate2.project.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmailAndProvider(String email, String provider);
}
