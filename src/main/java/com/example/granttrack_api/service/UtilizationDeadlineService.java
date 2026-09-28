package com.example.granttrack_api.service;

import com.example.granttrack_api.model.GrantApplication;
import com.example.granttrack_api.repository.GrantApplicationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UtilizationDeadlineService {

    private final GrantApplicationRepository grantApplicationRepository;

    public UtilizationDeadlineService(
            GrantApplicationRepository grantApplicationRepository) {

        this.grantApplicationRepository = grantApplicationRepository;
    }

    public List<GrantApplication> getUpcomingDeadlines() {

        LocalDate today = LocalDate.now();

        return grantApplicationRepository.findAll()
                .stream()
                .filter(application ->
                        application.getUtilizationDeadline() != null)
                .filter(application ->
                        !application.getUtilizationDeadline()
                                .isBefore(today))
                .toList();
    }

    public List<GrantApplication> getExpiredDeadlines() {

        LocalDate today = LocalDate.now();

        return grantApplicationRepository.findAll()
                .stream()
                .filter(application ->
                        application.getUtilizationDeadline() != null)
                .filter(application ->
                        application.getUtilizationDeadline()
                                .isBefore(today))
                .toList();
    }
}
