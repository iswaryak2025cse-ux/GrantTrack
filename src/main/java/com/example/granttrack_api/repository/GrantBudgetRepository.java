package com.example.granttrack_api.repository;

import com.example.granttrack_api.model.GrantBudget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GrantBudgetRepository
        extends JpaRepository<GrantBudget, Long> {

    Optional<GrantBudget> findByApplicationId(Long applicationId);
}