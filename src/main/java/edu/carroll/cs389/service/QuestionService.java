package edu.carroll.cs389.service;
import edu.carroll.cs389.jpa.model.Question;
import edu.carroll.cs389.jpa.repo.QuestionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository repository;

    // Fetch all questions
    public Iterable<Question> getAllQuestions() {
        return repository.findAll();
    }

    // Add a new question
    public void addQuestion(Question question) {
        repository.save(question);
    }

    public Question randomQuestion() {
        // Stack Overflow help with getting a random entity from the DB
        Long qty = repository.count();
        int idx = (int)(Math.random() * qty);
        Page<Question> questionPage = repository.findAll(PageRequest.of(idx, 1));
        Question q = null;
        if (questionPage.hasContent()) {
            q = questionPage.getContent().get(0);
        }
        return q;
    }

}
