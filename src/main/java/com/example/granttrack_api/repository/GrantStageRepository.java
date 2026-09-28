package com.example.granttrack_api.repository;

import com.example.granttrack_api.model.GrantStage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GrantStageRepository
        extends JpaRepository<GrantStage, Long> {

    List<GrantStage> findByApplicationId(Long applicationId);
}
