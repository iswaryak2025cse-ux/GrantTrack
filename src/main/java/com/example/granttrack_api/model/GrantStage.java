package com.example.granttrack_api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "grant_stages")
public class GrantStage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String stageName;

    @Column(columnDefinition = "TEXT")
    private String remarks;

    @ManyToOne
    @JoinColumn(name = "application_id", nullable = false)
    private GrantApplication application;

    public GrantStage() {
    }

    public GrantStage(
            String stageName,
            String remarks,
            GrantApplication application) {

        this.stageName = stageName;
        this.remarks = remarks;
        this.application = application;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public GrantApplication getApplication() {
        return application;
    }

    public void setApplication(GrantApplication application) {
        this.application = application;
    }
} 