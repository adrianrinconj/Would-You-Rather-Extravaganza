package edu.carroll.cs389.jpa.repo;

import edu.carroll.cs389.jpa.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring JPA repository for the {@link Question} entity.
 * This interface provides the mechanism for storage, retrieval, update,
 * and deletion on {@code Question} objects.
 */
@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

}
