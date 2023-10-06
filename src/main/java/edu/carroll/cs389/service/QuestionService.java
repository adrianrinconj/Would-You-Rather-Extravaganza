package edu.carroll.cs389.service;
import edu.carroll.cs389.jpa.model.Question;
import edu.carroll.cs389.jpa.repo.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository repository;

    public QuestionService(QuestionRepository repository) {
        this.repository = repository;
    }

    // Fetch all questions
    public Iterable<Question> getAllQuestions() {
        return repository.findAll();
    }

    // Add a new question
    public void addQuestion(Question question) {
        repository.save(question);
    }

    public Question randomQuestion() {
        List<Question> allQ = repository.findAll();
        // Stack Overflow help with getting a random entity from the DB
        int idx = (int) (Math.random() * allQ.size());
        return allQ.get(idx);
    }

}