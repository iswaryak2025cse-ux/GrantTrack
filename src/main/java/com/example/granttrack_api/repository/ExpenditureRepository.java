package com.example.granttrack_api.repository;

import com.example.granttrack_api.model.Expenditure;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenditureRepository
        extends JpaRepository<Expenditure, Long> {

    List<Expenditure> findByApplicationId(Long applicationId);
}
