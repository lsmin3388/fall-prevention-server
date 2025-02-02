package com.happyaging.fallprevention.survey.entity.reponse;

import com.happyaging.fallprevention.survey.entity.survey.Survey;
import com.happyaging.fallprevention.survey.entity.question.Question;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Response {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "response_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "survey_id")
    private Survey survey;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @Setter
    @OneToMany(mappedBy = "response", cascade = CascadeType.REMOVE)
    private List<ResponseOption> responseOptions;

    @Setter
    private String answerText;

    @Builder
    public Response(Survey survey, Question question, List<ResponseOption> responseOptions, String answerText) {
        this.question = question;
        this.survey = survey;
        this.responseOptions = responseOptions;
        this.answerText = answerText;
    }

}
