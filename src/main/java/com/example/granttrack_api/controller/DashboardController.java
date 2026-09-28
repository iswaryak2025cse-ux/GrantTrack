package com.example.granttrack_api.controller;

import com.example.granttrack_api.model.ApplicationStatus;
import com.example.granttrack_api.repository.FacultyRepository;
import com.example.granttrack_api.repository.GrantApplicationRepository;
import com.example.granttrack_api.repository.GrantBudgetRepository;
import com.example.granttrack_api.repository.ExpenditureRepository;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    private final FacultyRepository facultyRepository;
    private final GrantApplicationRepository grantApplicationRepository;
    private final GrantBudgetRepository grantBudgetRepository;
    private final ExpenditureRepository expenditureRepository;

    public DashboardController(
            FacultyRepository facultyRepository,
            GrantApplicationRepository grantApplicationRepository,
            GrantBudgetRepository grantBudgetRepository,
            ExpenditureRepository expenditureRepository) {

        this.facultyRepository = facultyRepository;
        this.grantApplicationRepository = grantApplicationRepository;
        this.grantBudgetRepository = grantBudgetRepository;
        this.expenditureRepository = expenditureRepository;
    }

    @GetMapping
    public Map<String, Object> getDashboard() {

        Map<String, Object> dashboard = new HashMap<>();

        long facultyCount =
                facultyRepository.count();

        long applicationCount =
                grantApplicationRepository.count();

        long submittedCount =
                grantApplicationRepository
                        .findByStatus(ApplicationStatus.SUBMITTED)
                        .size();

        long underReviewCount =
                grantApplicationRepository
                        .findByStatus(ApplicationStatus.UNDER_REVIEW)
                        .size();

        long approvedCount =
                grantApplicationRepository
                        .findByStatus(ApplicationStatus.APPROVED)
                        .size();

        long rejectedCount =
                grantApplicationRepository
                        .findByStatus(ApplicationStatus.REJECTED)
                        .size();

        BigDecimal approvedGrant =
                grantBudgetRepository.findAll()
                        .stream()
                        .map(budget -> budget.getApprovedAmount())
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalExpenditure =
                expenditureRepository.findAll()
                        .stream()
                        .map(expenditure -> expenditure.getAmount())
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal remainingGrant =
                approvedGrant.subtract(totalExpenditure);

        dashboard.put("facultyCount", facultyCount);
        dashboard.put("applicationCount", applicationCount);
        dashboard.put("submittedCount", submittedCount);
        dashboard.put("underReviewCount", underReviewCount);
        dashboard.put("approvedCount", approvedCount);
        dashboard.put("rejectedCount", rejectedCount);
        dashboard.put("approvedGrant", approvedGrant);
        dashboard.put("totalExpenditure", totalExpenditure);
        dashboard.put("remainingGrant", remainingGrant);

        return dashboard;
    }
}
