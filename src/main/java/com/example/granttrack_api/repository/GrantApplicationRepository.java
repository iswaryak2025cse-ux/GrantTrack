package com.example.granttrack_api.repository;

import com.example.granttrack_api.model.GrantApplication;
import com.example.granttrack_api.model.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GrantApplicationRepository
        extends JpaRepository<GrantApplication, Long> {

    List<GrantApplication> findByFacultyId(Long facultyId);

    List<GrantApplication> findByStatus(ApplicationStatus status);
}