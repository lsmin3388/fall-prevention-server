package com.happyaging.fallprevention.survey.survey.entity;

import java.util.ArrayList;
import java.util.List;

import com.happyaging.fallprevention.base.BaseAuditEntity;
import com.happyaging.fallprevention.senior.entity.Senior;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Survey extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "survey_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "senior_id")
    private Senior senior;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Response> responses = new ArrayList<>();

    private String pdfUrl;

    @Enumerated(EnumType.STRING)
    private RiskLevel riskLevel;

    private String summary;

    @Builder
    public Survey(Long id, Senior senior, List<Response> responses, String pdfUrl, RiskLevel riskLevel,
        String summary) {
        this.id = id;
        this.senior = senior;
        this.responses = responses;
        this.pdfUrl = pdfUrl;
        this.riskLevel = riskLevel;
        this.summary = summary;
    }

    // ==== 수정 메서드 ====
    public void changeSenior(Senior senior) {
        this.senior = senior;
    }

    public void changePdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public void changeRiskLevel(RiskLevel riskLevel) {
        this.riskLevel = riskLevel;
    }

    public void changeSummary(String summary) {
        this.summary = summary;
    }
}
