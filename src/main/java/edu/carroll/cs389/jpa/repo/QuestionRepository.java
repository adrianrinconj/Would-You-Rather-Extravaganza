package edu.carroll.cs389.jpa.repo;

import edu.carroll.cs389.jpa.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

}