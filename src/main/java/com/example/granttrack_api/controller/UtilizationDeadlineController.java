package com.example.granttrack_api.controller;

import com.example.granttrack_api.model.GrantApplication;
import com.example.granttrack_api.service.UtilizationDeadlineService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utilization-deadlines")
@CrossOrigin(origins = "*")
public class UtilizationDeadlineController {

    private final UtilizationDeadlineService utilizationDeadlineService;

    public UtilizationDeadlineController(
            UtilizationDeadlineService utilizationDeadlineService) {

        this.utilizationDeadlineService = utilizationDeadlineService;
    }

    @GetMapping("/upcoming")
    public List<GrantApplication> getUpcomingDeadlines() {

        return utilizationDeadlineService.getUpcomingDeadlines();
    }

    @GetMapping("/expired")
    public List<GrantApplication> getExpiredDeadlines() {

        return utilizationDeadlineService.getExpiredDeadlines();
    }
}