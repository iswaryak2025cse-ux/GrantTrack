package com.example.granttrack_api.controller;

import com.example.granttrack_api.model.GrantStage;
import com.example.granttrack_api.service.GrantStageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/grant-stages")
@CrossOrigin(origins = "*")
public class GrantStageController {

    private final GrantStageService grantStageService;

    public GrantStageController(
            GrantStageService grantStageService) {

        this.grantStageService = grantStageService;
    }

    @GetMapping
    public List<GrantStage> getAllStages() {
        return grantStageService.getAllStages();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GrantStage> getStageById(
            @PathVariable Long id) {

        return grantStageService.getStageById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/application/{applicationId}")
    public List<GrantStage> getStagesByApplication(
            @PathVariable Long applicationId) {

        return grantStageService
                .getStagesByApplication(applicationId);
    }

    @PostMapping
    public GrantStage createStage(
            @RequestParam Long applicationId,
            @RequestBody Map<String, String> request) {

        String stageName = request.get("stageName");
        String remarks = request.get("remarks");

        return grantStageService.createStage(
                applicationId,
                stageName,
                remarks);
    }

    @PutMapping("/{id}")
    public GrantStage updateStage(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {

        String stageName = request.get("stageName");
        String remarks = request.get("remarks");

        return grantStageService.updateStage(
                id,
                stageName,
                remarks);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStage(
            @PathVariable Long id) {

        grantStageService.deleteStage(id);

        return ResponseEntity.noContent().build();
    }
}
