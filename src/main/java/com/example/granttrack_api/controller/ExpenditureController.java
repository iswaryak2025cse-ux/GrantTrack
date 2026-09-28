package com.example.granttrack_api.controller;

import com.example.granttrack_api.model.Expenditure;
import com.example.granttrack_api.service.ExpenditureService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/expenditures")
@CrossOrigin(origins = "*")
public class ExpenditureController {

    private final ExpenditureService expenditureService;

    public ExpenditureController(
            ExpenditureService expenditureService) {

        this.expenditureService = expenditureService;
    }

    @GetMapping
    public List<Expenditure> getAllExpenditures() {
        return expenditureService.getAllExpenditures();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expenditure> getExpenditureById(
            @PathVariable Long id) {

        return expenditureService
                .getExpenditureById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/application/{applicationId}")
    public List<Expenditure> getExpendituresByApplication(
            @PathVariable Long applicationId) {

        return expenditureService
                .getExpendituresByApplication(applicationId);
    }

    @PostMapping
    public Expenditure createExpenditure(
            @RequestParam Long applicationId,
            @RequestBody Map<String, Object> request) {

        String description =
                request.get("description").toString();

        BigDecimal amount =
                new BigDecimal(
                        request.get("amount").toString());

        return expenditureService.createExpenditure(
                applicationId,
                description,
                amount);
    }

    @PutMapping("/{id}")
    public Expenditure updateExpenditure(
            @PathVariable Long id,
            @RequestBody Map<String, Object> request) {

        String description =
                request.get("description").toString();

        BigDecimal amount =
                new BigDecimal(
                        request.get("amount").toString());

        return expenditureService.updateExpenditure(
                id,
                description,
                amount);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpenditure(
            @PathVariable Long id) {

        expenditureService.deleteExpenditure(id);

        return ResponseEntity.noContent().build();
    }
}