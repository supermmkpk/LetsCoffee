package com.graduate2.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.graduate2.project.domain.Cafename;

public interface CafenameRepository extends JpaRepository<Cafename, Long> {
}
