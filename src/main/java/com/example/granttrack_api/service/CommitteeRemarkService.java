package com.example.granttrack_api.service;

import com.example.granttrack_api.model.CommitteeRemark;
import com.example.granttrack_api.model.GrantApplication;
import com.example.granttrack_api.repository.CommitteeRemarkRepository;
import com.example.granttrack_api.repository.GrantApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommitteeRemarkService {

    private final CommitteeRemarkRepository committeeRemarkRepository;
    private final GrantApplicationRepository grantApplicationRepository;

    public CommitteeRemarkService(
            CommitteeRemarkRepository committeeRemarkRepository,
            GrantApplicationRepository grantApplicationRepository) {

        this.committeeRemarkRepository = committeeRemarkRepository;
        this.grantApplicationRepository = grantApplicationRepository;
    }

    public List<CommitteeRemark> getAllRemarks() {
        return committeeRemarkRepository.findAll();
    }

    public Optional<CommitteeRemark> getRemarkById(Long id) {
        return committeeRemarkRepository.findById(id);
    }

    public List<CommitteeRemark> getRemarksByApplication(
            Long applicationId) {

        return committeeRemarkRepository
                .findByApplicationId(applicationId);
    }

    public CommitteeRemark createRemark(
            Long applicationId,
            String remarks) {

        GrantApplication application =
                grantApplicationRepository.findById(applicationId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Grant application not found"));

        CommitteeRemark committeeRemark =
                new CommitteeRemark(remarks, application);

        return committeeRemarkRepository.save(committeeRemark);
    }

    public CommitteeRemark updateRemark(
            Long id,
            String remarks) {

        CommitteeRemark committeeRemark =
                committeeRemarkRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Committee remark not found"));

        committeeRemark.setRemarks(remarks);

        return committeeRemarkRepository.save(committeeRemark);
    }

    public void deleteRemark(Long id) {

        if (!committeeRemarkRepository.existsById(id)) {
            throw new RuntimeException(
                    "Committee remark not found");
        }

        committeeRemarkRepository.deleteById(id);
    }
}