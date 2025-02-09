package survey.entity.survey;

import java.security.Timestamp;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

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
import survey.entity.reponse.Response;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "survey_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "senior_id")
    private Senior senior;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Response> responses;

    private String pdfUrl;

    @Enumerated(EnumType.STRING)
    private RiskLevel riskLevel;

    private String summary;

    @CreationTimestamp
    @Column(nullable = false)
    private Timestamp surveyedAt;

    @Builder
    public Survey(Senior senior, List<Response> responses, String pdfUrl, RiskLevel riskLevel, String summary, Timestamp surveyedAt) {
        this.senior = senior;
        this.responses = responses;
        this.pdfUrl = pdfUrl;
        this.riskLevel = riskLevel;
        this.summary = summary;
        this.surveyedAt = surveyedAt;
    }

}
