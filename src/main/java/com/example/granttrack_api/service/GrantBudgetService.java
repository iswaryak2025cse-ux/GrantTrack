package com.example.granttrack_api.service;

import com.example.granttrack_api.model.ApplicationStatus;
import com.example.granttrack_api.model.GrantApplication;
import com.example.granttrack_api.model.GrantBudget;
import com.example.granttrack_api.repository.GrantApplicationRepository;
import com.example.granttrack_api.repository.GrantBudgetRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class GrantBudgetService {

    private final GrantBudgetRepository grantBudgetRepository;
    private final GrantApplicationRepository grantApplicationRepository;

    public GrantBudgetService(
            GrantBudgetRepository grantBudgetRepository,
            GrantApplicationRepository grantApplicationRepository) {

        this.grantBudgetRepository = grantBudgetRepository;
        this.grantApplicationRepository = grantApplicationRepository;
    }

    public List<GrantBudget> getAllBudgets() {
        return grantBudgetRepository.findAll();
    }

    public Optional<GrantBudget> getBudgetById(Long id) {
        return grantBudgetRepository.findById(id);
    }

    public Optional<GrantBudget> getBudgetByApplication(
            Long applicationId) {

        return grantBudgetRepository
                .findByApplicationId(applicationId);
    }

    public GrantBudget createBudget(
            Long applicationId,
            BigDecimal approvedAmount) {

        GrantApplication application =
                grantApplicationRepository.findById(applicationId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Grant application not found"));

        if (application.getStatus() != ApplicationStatus.APPROVED) {
            throw new RuntimeException(
                    "Budget can be created only for approved applications");
        }

        if (grantBudgetRepository
                .findByApplicationId(applicationId)
                .isPresent()) {

            throw new RuntimeException(
                    "Budget already exists for this application");
        }

        if (approvedAmount == null ||
                approvedAmount.compareTo(BigDecimal.ZERO) <= 0) {

            throw new RuntimeException(
                    "Approved amount must be greater than zero");
        }

        if (approvedAmount.compareTo(
                application.getRequestedAmount()) > 0) {

            throw new RuntimeException(
                    "Approved amount cannot exceed requested amount");
        }

        GrantBudget budget =
                new GrantBudget(
                        approvedAmount,
                        application);

        return grantBudgetRepository.save(budget);
    }

    public GrantBudget updateBudget(
            Long id,
            BigDecimal approvedAmount) {

        GrantBudget budget =
                grantBudgetRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Grant budget not found"));

        if (approvedAmount == null ||
                approvedAmount.compareTo(BigDecimal.ZERO) <= 0) {

            throw new RuntimeException(
                    "Approved amount must be greater than zero");
        }

        if (approvedAmount.compareTo(
                budget.getApplication().getRequestedAmount()) > 0) {

            throw new RuntimeException(
                    "Approved amount cannot exceed requested amount");
        }

        budget.setApprovedAmount(approvedAmount);

        BigDecimal remaining =
                approvedAmount.subtract(
                        budget.getTotalExpenditure());

        if (remaining.compareTo(BigDecimal.ZERO) < 0) {

            throw new RuntimeException(
                    "Approved amount cannot be less than total expenditure");
        }

        budget.setRemainingAmount(remaining);

        return grantBudgetRepository.save(budget);
    }

    public void deleteBudget(Long id) {

        if (!grantBudgetRepository.existsById(id)) {
            throw new RuntimeException(
                    "Grant budget not found");
        }

        grantBudgetRepository.deleteById(id);
    }
}
