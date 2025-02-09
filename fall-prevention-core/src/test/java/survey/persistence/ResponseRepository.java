package survey.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import survey.entity.reponse.Response;

public interface ResponseRepository extends JpaRepository<Response, Long> {
}
