package survey.entity.reponse;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import survey.entity.question.QuestionOption;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "response_option_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "response_id")
    private Response response;

    @ManyToOne
    @JoinColumn(name = "question_option_id")
    private QuestionOption questionOption;

    @Builder
    public ResponseOption(QuestionOption questionOption, Response response) {
        this.questionOption = questionOption;
        this.response = response;
    }
}
