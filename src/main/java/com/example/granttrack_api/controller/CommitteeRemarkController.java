package com.example.granttrack_api.controller;

import com.example.granttrack_api.model.CommitteeRemark;
import com.example.granttrack_api.service.CommitteeRemarkService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/committee-remarks")
@CrossOrigin(origins = "*")
public class CommitteeRemarkController {

    private final CommitteeRemarkService committeeRemarkService;

    public CommitteeRemarkController(
            CommitteeRemarkService committeeRemarkService) {

        this.committeeRemarkService = committeeRemarkService;
    }

    @GetMapping
    public List<CommitteeRemark> getAllRemarks() {
        return committeeRemarkService.getAllRemarks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommitteeRemark> getRemarkById(
            @PathVariable Long id) {

        return committeeRemarkService.getRemarkById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/application/{applicationId}")
    public List<CommitteeRemark> getRemarksByApplication(
            @PathVariable Long applicationId) {

        return committeeRemarkService
                .getRemarksByApplication(applicationId);
    }

    @PostMapping
    public CommitteeRemark createRemark(
            @RequestParam Long applicationId,
            @RequestBody Map<String, String> request) {

        String remarks = request.get("remarks");

        return committeeRemarkService
                .createRemark(applicationId, remarks);
    }

    @PutMapping("/{id}")
    public CommitteeRemark updateRemark(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {

        String remarks = request.get("remarks");

        return committeeRemarkService
                .updateRemark(id, remarks);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRemark(
            @PathVariable Long id) {

        committeeRemarkService.deleteRemark(id);

        return ResponseEntity.noContent().build();
    }
}