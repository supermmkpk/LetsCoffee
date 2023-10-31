package com.graduate2.project.repository;

import com.graduate2.project.domain.UserFavorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFavoriteRepository extends JpaRepository<UserFavorite, Long> {
}
