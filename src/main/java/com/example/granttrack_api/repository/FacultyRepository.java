package com.example.granttrack_api.repository;

import com.example.granttrack_api.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
}
