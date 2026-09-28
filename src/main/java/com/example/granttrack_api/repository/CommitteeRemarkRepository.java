package com.example.granttrack_api.repository;

import com.example.granttrack_api.model.CommitteeRemark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommitteeRemarkRepository
        extends JpaRepository<CommitteeRemark, Long> {

    List<CommitteeRemark> findByApplicationId(Long applicationId);
}