package com.example.granttrack_api.service;

import com.example.granttrack_api.model.GrantApplication;
import com.example.granttrack_api.model.GrantStage;
import com.example.granttrack_api.repository.GrantApplicationRepository;
import com.example.granttrack_api.repository.GrantStageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GrantStageService {

    private final GrantStageRepository grantStageRepository;
    private final GrantApplicationRepository grantApplicationRepository;

    public GrantStageService(
            GrantStageRepository grantStageRepository,
            GrantApplicationRepository grantApplicationRepository) {

        this.grantStageRepository = grantStageRepository;
        this.grantApplicationRepository = grantApplicationRepository;
    }

    public List<GrantStage> getAllStages() {
        return grantStageRepository.findAll();
    }

    public Optional<GrantStage> getStageById(Long id) {
        return grantStageRepository.findById(id);
    }

    public List<GrantStage> getStagesByApplication(
            Long applicationId) {

        return grantStageRepository
                .findByApplicationId(applicationId);
    }

    public GrantStage createStage(
            Long applicationId,
            String stageName,
            String remarks) {

        GrantApplication application =
                grantApplicationRepository.findById(applicationId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Grant application not found"));

        GrantStage stage =
                new GrantStage(
                        stageName,
                        remarks,
                        application);

        return grantStageRepository.save(stage);
    }

    public GrantStage updateStage(
            Long id,
            String stageName,
            String remarks) {

        GrantStage stage =
                grantStageRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Grant stage not found"));

        stage.setStageName(stageName);
        stage.setRemarks(remarks);

        return grantStageRepository.save(stage);
    }

    public void deleteStage(Long id) {

        if (!grantStageRepository.existsById(id)) {
            throw new RuntimeException(
                    "Grant stage not found");
        }

        grantStageRepository.deleteById(id);
    }
}
