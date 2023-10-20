package edu.carroll.cs389.service;

import edu.carroll.cs389.jpa.model.Question;
import edu.carroll.cs389.jpa.repo.QuestionRepository;
import edu.carroll.cs389.jpa.repo.UserRepository;
import edu.carroll.cs389.jpa.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Implementation of the Question Service.
 */
@Service
public class QuestionServiceImpl implements QuestionServiceInterface {

    private final QuestionRepository repository;
    private final UserRepository userRepository;

    public QuestionServiceImpl(QuestionRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public Iterable<Question> getAllQuestions() {
        return repository.findAll();
    }

    @Override
    public void addQuestion(Question question) {
        if (uniqueQuestion(question)) {
            repository.save(question);
        }
    }

    @Override
    public boolean uniqueQuestion(Question newQuestion) {
        for (Question currentQuestion : getAllQuestions()) {
            if (Objects.equals(newQuestion.getOptionA(), currentQuestion.getOptionA()) || Objects.equals(newQuestion.getOptionB(), currentQuestion.getOptionB())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Question randomUnseenQuestion(User currentUser) {
        List<Question> allQ = repository.findAll();

        // Remove all questions the user has already seen
        allQ.removeAll(currentUser.getSeenQuestions());

        // If the user has seen all the questions, handle accordingly.
        if (allQ.isEmpty()) {
            //XXX HOW SHOULD THIS BE HANDLED?
            return null;
        }

        // Randomly select a question from the remaining questions
        int idx = (int) (Math.random() * allQ.size());
        return allQ.get(idx);
    }

    @Override
    public Question questionIdLookup(Long questionID) {
        for (Question a : getAllQuestions()) {
            if (Objects.equals(a.getId(), questionID)) {
                return a;
            }
        }
        return null;
    }

    @Override
    public void markQuestionAsSeen(User user, Question question) {
        user.getSeenQuestions().add(question);
        userRepository.save(user);
    }
}

