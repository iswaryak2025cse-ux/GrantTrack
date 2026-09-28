package com.example.granttrack_api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "committee_remarks")
public class CommitteeRemark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String remarks;

    @ManyToOne
    @JoinColumn(name = "application_id", nullable = false)
    private GrantApplication application;

    public CommitteeRemark() {
    }

    public CommitteeRemark(
            String remarks,
            GrantApplication application) {

        this.remarks = remarks;
        this.application = application;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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