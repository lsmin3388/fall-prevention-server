package survey.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import survey.entity.reponse.ResponseOption;

public interface ResponseOptionRepository extends JpaRepository<ResponseOption, Long> {
}
