package com.example.granttrack_api.service;

import com.example.granttrack_api.model.ApplicationStatus;
import com.example.granttrack_api.repository.ExpenditureRepository;
import com.example.granttrack_api.repository.GrantApplicationRepository;
import com.example.granttrack_api.repository.GrantBudgetRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class ReportService {

    private final GrantApplicationRepository grantApplicationRepository;
    private final GrantBudgetRepository grantBudgetRepository;
    private final ExpenditureRepository expenditureRepository;

    public ReportService(
            GrantApplicationRepository grantApplicationRepository,
            GrantBudgetRepository grantBudgetRepository,
            ExpenditureRepository expenditureRepository) {

        this.grantApplicationRepository = grantApplicationRepository;
        this.grantBudgetRepository = grantBudgetRepository;
        this.expenditureRepository = expenditureRepository;
    }

    public Map<String, Object> getGrantReport() {

        Map<String, Object> report = new HashMap<>();

        long totalApplications =
                grantApplicationRepository.count();

        long submittedApplications =
                grantApplicationRepository
                        .findByStatus(ApplicationStatus.SUBMITTED)
                        .size();

        long underReviewApplications =
                grantApplicationRepository
                        .findByStatus(ApplicationStatus.UNDER_REVIEW)
                        .size();

        long approvedApplications =
                grantApplicationRepository
                        .findByStatus(ApplicationStatus.APPROVED)
                        .size();

        long rejectedApplications =
                grantApplicationRepository
                        .findByStatus(ApplicationStatus.REJECTED)
                        .size();

        BigDecimal totalApprovedGrant =
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
                totalApprovedGrant.subtract(totalExpenditure);

        report.put("totalApplications", totalApplications);
        report.put("submittedApplications", submittedApplications);
        report.put("underReviewApplications", underReviewApplications);
        report.put("approvedApplications", approvedApplications);
        report.put("rejectedApplications", rejectedApplications);
        report.put("totalApprovedGrant", totalApprovedGrant);
        report.put("totalExpenditure", totalExpenditure);
        report.put("remainingGrant", remainingGrant);

        return report;
    }
}
