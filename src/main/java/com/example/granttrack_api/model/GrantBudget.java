package com.example.granttrack_api.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "grant_budgets")
public class GrantBudget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal approvedAmount;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal totalExpenditure;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal remainingAmount;

    @OneToOne
    @JoinColumn(name = "application_id", nullable = false, unique = true)
    private GrantApplication application;

    public GrantBudget() {
    }

    public GrantBudget(
            BigDecimal approvedAmount,
            GrantApplication application) {

        this.approvedAmount = approvedAmount;
        this.totalExpenditure = BigDecimal.ZERO;
        this.remainingAmount = approvedAmount;
        this.application = application;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getApprovedAmount() {
        return approvedAmount;
    }

    public void setApprovedAmount(BigDecimal approvedAmount) {
        this.approvedAmount = approvedAmount;
    }

    public BigDecimal getTotalExpenditure() {
        return totalExpenditure;
    }

    public void setTotalExpenditure(BigDecimal totalExpenditure) {
        this.totalExpenditure = totalExpenditure;
    }

    public BigDecimal getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(BigDecimal remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public GrantApplication getApplication() {
        return application;
    }

    public void setApplication(GrantApplication application) {
        this.application = application;
    }
}