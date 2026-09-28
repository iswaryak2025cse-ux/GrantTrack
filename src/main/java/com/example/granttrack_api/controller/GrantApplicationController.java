package com.example.granttrack_api.controller;

import com.example.granttrack_api.model.ApplicationStatus;
import com.example.granttrack_api.model.GrantApplication;
import com.example.granttrack_api.service.GrantApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grant-applications")
@CrossOrigin(origins = "*")
public class GrantApplicationController {

    private final GrantApplicationService grantApplicationService;

    public GrantApplicationController(
            GrantApplicationService grantApplicationService) {

        this.grantApplicationService = grantApplicationService;
    }

    // Get all applications
    @GetMapping
    public List<GrantApplication> getAllApplications() {

        return grantApplicationService.getAllApplications();
    }

    // Get application by ID
    @GetMapping("/{id}")
    public ResponseEntity<GrantApplication> getApplicationById(
            @PathVariable Long id) {

        return grantApplicationService.getApplicationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get applications of a particular faculty
    @GetMapping("/faculty/{facultyId}")
    public List<GrantApplication> getApplicationsByFaculty(
            @PathVariable Long facultyId) {

        return grantApplicationService
                .getApplicationsByFaculty(facultyId);
    }

    // Get applications by status
    @GetMapping("/status/{status}")
    public List<GrantApplication> getApplicationsByStatus(
            @PathVariable ApplicationStatus status) {

        return grantApplicationService
                .getApplicationsByStatus(status);
    }

    // Create grant application
    @PostMapping
    public GrantApplication createApplication(
            @RequestParam Long facultyId,
            @RequestBody GrantApplication application) {

        return grantApplicationService
                .createApplication(application, facultyId);
    }

    // Update application
    @PutMapping("/{id}")
    public GrantApplication updateApplication(
            @PathVariable Long id,
            @RequestBody GrantApplication application) {

        return grantApplicationService
                .updateApplication(id, application);
    }

    // Change application status
    @PutMapping("/{id}/status")
    public GrantApplication updateStatus(
            @PathVariable Long id,
            @RequestParam ApplicationStatus status) {

        return grantApplicationService
                .updateStatus(id, status);
    }

    // Delete application
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(
            @PathVariable Long id) {

        grantApplicationService.deleteApplication(id);

        return ResponseEntity.noContent().build();
    }
}