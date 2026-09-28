package com.example.granttrack_api.service;

import com.example.granttrack_api.model.ApplicationStatus;
import com.example.granttrack_api.model.Faculty;
import com.example.granttrack_api.model.GrantApplication;
import com.example.granttrack_api.model.GrantStage;
import com.example.granttrack_api.repository.FacultyRepository;
import com.example.granttrack_api.repository.GrantApplicationRepository;
import com.example.granttrack_api.repository.GrantStageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GrantApplicationService {

    private final GrantApplicationRepository grantApplicationRepository;
    private final FacultyRepository facultyRepository;
    private final GrantStageRepository grantStageRepository;

    public GrantApplicationService(
            GrantApplicationRepository grantApplicationRepository,
            FacultyRepository facultyRepository,
            GrantStageRepository grantStageRepository) {

        this.grantApplicationRepository = grantApplicationRepository;
        this.facultyRepository = facultyRepository;
        this.grantStageRepository = grantStageRepository;
    }

    // Get all grant applications
    public List<GrantApplication> getAllApplications() {

        return grantApplicationRepository.findAll();
    }

    // Get application by ID
    public Optional<GrantApplication> getApplicationById(Long id) {

        return grantApplicationRepository.findById(id);
    }

    // Get applications by faculty
    public List<GrantApplication> getApplicationsByFaculty(Long facultyId) {

        return grantApplicationRepository.findByFacultyId(facultyId);
    }

    // Get applications by status
    public List<GrantApplication> getApplicationsByStatus(
            ApplicationStatus status) {

        return grantApplicationRepository.findByStatus(status);
    }

    // Create new grant application
    public GrantApplication createApplication(
            GrantApplication application,
            Long facultyId) {

        Faculty faculty = facultyRepository.findById(facultyId)
                .orElseThrow(() ->
                        new RuntimeException("Faculty not found"));

        application.setFaculty(faculty);

        // New applications always start with SUBMITTED
        application.setStatus(ApplicationStatus.SUBMITTED);

        GrantApplication savedApplication =
                grantApplicationRepository.save(application);

        // Automatically create SUBMITTED stage
        GrantStage stage = new GrantStage();

        stage.setStageName(ApplicationStatus.SUBMITTED.name());

        stage.setRemarks(
                "Grant application submitted successfully."
        );

        stage.setApplication(savedApplication);

        grantStageRepository.save(stage);

        return savedApplication;
    }

    // Update grant application details
    public GrantApplication updateApplication(
            Long id,
            GrantApplication applicationDetails) {

        GrantApplication application =
                grantApplicationRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Grant application not found"));

        application.setTitle(
                applicationDetails.getTitle());

        application.setRequestedAmount(
                applicationDetails.getRequestedAmount());

        application.setAbstractText(
                applicationDetails.getAbstractText());

        application.setUtilizationDeadline(
                applicationDetails.getUtilizationDeadline());

        return grantApplicationRepository.save(application);
    }

    // Update application status
    // Automatically creates a Grant Stage history record
    public GrantApplication updateStatus(
            Long id,
            ApplicationStatus status) {

        GrantApplication application =
                grantApplicationRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Grant application not found"));

        // Update application status
        application.setStatus(status);

        GrantApplication savedApplication =
                grantApplicationRepository.save(application);

        // Create automatic stage history
        GrantStage stage = new GrantStage();

        stage.setStageName(status.name());

        stage.setRemarks(
                "Application status changed to "
                        + status.name()
        );

        stage.setApplication(savedApplication);

        grantStageRepository.save(stage);

        return savedApplication;
    }

    // Delete grant application
    public void deleteApplication(Long id) {

        if (!grantApplicationRepository.existsById(id)) {

            throw new RuntimeException(
                    "Grant application not found");
        }

        grantApplicationRepository.deleteById(id);
    }
}