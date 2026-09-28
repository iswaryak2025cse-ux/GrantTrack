package com.example.granttrack_api.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "grant_applications")
public class GrantApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal requestedAmount;

    @Column(columnDefinition = "TEXT")
    private String abstractText;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status;

    private LocalDate utilizationDeadline;

    @ManyToOne
    @JoinColumn(name = "faculty_id", nullable = false)
    private Faculty faculty;

    public GrantApplication() {
    }

    public GrantApplication(
            String title,
            BigDecimal requestedAmount,
            String abstractText,
            LocalDate utilizationDeadline,
            Faculty faculty) {

        this.title = title;
        this.requestedAmount = requestedAmount;
        this.abstractText = abstractText;
        this.utilizationDeadline = utilizationDeadline;
        this.faculty = faculty;
        this.status = ApplicationStatus.SUBMITTED;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(BigDecimal requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public String getAbstractText() {
        return abstractText;
    }

    public void setAbstractText(String abstractText) {
        this.abstractText = abstractText;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public LocalDate getUtilizationDeadline() {
        return utilizationDeadline;
    }

    public void setUtilizationDeadline(LocalDate utilizationDeadline) {
        this.utilizationDeadline = utilizationDeadline;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }
}