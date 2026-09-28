package com.example.granttrack_api.controller;

import com.example.granttrack_api.model.GrantBudget;
import com.example.granttrack_api.service.GrantBudgetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/grant-budgets")
@CrossOrigin(origins = "*")
public class GrantBudgetController {

    private final GrantBudgetService grantBudgetService;

    public GrantBudgetController(
            GrantBudgetService grantBudgetService) {

        this.grantBudgetService = grantBudgetService;
    }

    @GetMapping
    public List<GrantBudget> getAllBudgets() {
        return grantBudgetService.getAllBudgets();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GrantBudget> getBudgetById(
            @PathVariable Long id) {

        return grantBudgetService.getBudgetById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/application/{applicationId}")
    public ResponseEntity<GrantBudget> getBudgetByApplication(
            @PathVariable Long applicationId) {

        return grantBudgetService
                .getBudgetByApplication(applicationId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public GrantBudget createBudget(
            @RequestParam Long applicationId,
            @RequestBody Map<String, Object> request) {

        BigDecimal approvedAmount =
                new BigDecimal(
                        request.get("approvedAmount").toString());

        return grantBudgetService.createBudget(
                applicationId,
                approvedAmount);
    }

    @PutMapping("/{id}")
    public GrantBudget updateBudget(
            @PathVariable Long id,
            @RequestBody Map<String, Object> request) {

        BigDecimal approvedAmount =
                new BigDecimal(
                        request.get("approvedAmount").toString());

        return grantBudgetService.updateBudget(
                id,
                approvedAmount);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBudget(
            @PathVariable Long id) {

        grantBudgetService.deleteBudget(id);

        return ResponseEntity.noContent().build();
    }
}