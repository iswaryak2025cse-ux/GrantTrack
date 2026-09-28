package com.example.granttrack_api.service;

import com.example.granttrack_api.model.ApplicationStatus;
import com.example.granttrack_api.model.Expenditure;
import com.example.granttrack_api.model.GrantApplication;
import com.example.granttrack_api.model.GrantBudget;
import com.example.granttrack_api.repository.ExpenditureRepository;
import com.example.granttrack_api.repository.GrantApplicationRepository;
import com.example.granttrack_api.repository.GrantBudgetRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenditureService {

    private final ExpenditureRepository expenditureRepository;
    private final GrantApplicationRepository grantApplicationRepository;
    private final GrantBudgetRepository grantBudgetRepository;

    public ExpenditureService(
            ExpenditureRepository expenditureRepository,
            GrantApplicationRepository grantApplicationRepository,
            GrantBudgetRepository grantBudgetRepository) {

        this.expenditureRepository = expenditureRepository;
        this.grantApplicationRepository = grantApplicationRepository;
        this.grantBudgetRepository = grantBudgetRepository;
    }

    public List<Expenditure> getAllExpenditures() {
        return expenditureRepository.findAll();
    }

    public Optional<Expenditure> getExpenditureById(Long id) {
        return expenditureRepository.findById(id);
    }

    public List<Expenditure> getExpendituresByApplication(
            Long applicationId) {

        return expenditureRepository
                .findByApplicationId(applicationId);
    }

    public Expenditure createExpenditure(
            Long applicationId,
            String description,
            BigDecimal amount) {

        GrantApplication application =
                grantApplicationRepository.findById(applicationId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Grant application not found"));

        if (application.getStatus() != ApplicationStatus.APPROVED) {
            throw new RuntimeException(
                    "Expenditure can be added only for approved applications");
        }

        if (amount == null ||
                amount.compareTo(BigDecimal.ZERO) <= 0) {

            throw new RuntimeException(
                    "Expenditure amount must be greater than zero");
        }

        GrantBudget budget =
                grantBudgetRepository
                        .findByApplicationId(applicationId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Grant budget not found"));

        if (amount.compareTo(
                budget.getRemainingAmount()) > 0) {

            throw new RuntimeException(
                    "Expenditure cannot exceed remaining grant amount");
        }

        Expenditure expenditure =
                new Expenditure(
                        description,
                        amount,
                        application);

        Expenditure savedExpenditure =
                expenditureRepository.save(expenditure);

        BigDecimal newTotal =
                budget.getTotalExpenditure()
                        .add(amount);

        BigDecimal newRemaining =
                budget.getApprovedAmount()
                        .subtract(newTotal);

        budget.setTotalExpenditure(newTotal);
        budget.setRemainingAmount(newRemaining);

        grantBudgetRepository.save(budget);

        return savedExpenditure;
    }

    public Expenditure updateExpenditure(
            Long id,
            String description,
            BigDecimal amount) {

        Expenditure expenditure =
                expenditureRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Expenditure not found"));

        GrantBudget budget =
                grantBudgetRepository
                        .findByApplicationId(
                                expenditure.getApplication().getId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Grant budget not found"));

        BigDecimal oldAmount =
                expenditure.getAmount();

        BigDecimal newTotal =
                budget.getTotalExpenditure()
                        .subtract(oldAmount)
                        .add(amount);

        if (newTotal.compareTo(
                budget.getApprovedAmount()) > 0) {

            throw new RuntimeException(
                    "Total expenditure cannot exceed approved amount");
        }

        expenditure.setDescription(description);
        expenditure.setAmount(amount);

        budget.setTotalExpenditure(newTotal);

        budget.setRemainingAmount(
                budget.getApprovedAmount()
                        .subtract(newTotal));

        expenditureRepository.save(expenditure);
        grantBudgetRepository.save(budget);

        return expenditure;
    }

    public void deleteExpenditure(Long id) {

        Expenditure expenditure =
                expenditureRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Expenditure not found"));

        GrantBudget budget =
                grantBudgetRepository
                        .findByApplicationId(
                                expenditure.getApplication().getId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Grant budget not found"));

        BigDecimal newTotal =
                budget.getTotalExpenditure()
                        .subtract(expenditure.getAmount());

        budget.setTotalExpenditure(newTotal);

        budget.setRemainingAmount(
                budget.getApprovedAmount()
                        .subtract(newTotal));

        grantBudgetRepository.save(budget);

        expenditureRepository.deleteById(id);
    }
}