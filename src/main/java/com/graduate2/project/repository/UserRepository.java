package com.graduate2.project.repository;

import com.graduate2.project.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmailAndProvider(String email, String provider); //이메일을 통해 생성된 사용자인지 체크함.
}
