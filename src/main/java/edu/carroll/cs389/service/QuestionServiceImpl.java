package edu.carroll.cs389.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger log = LoggerFactory.getLogger(QuestionServiceImpl.class);


    private final QuestionRepository repository;
    private final UserRepository userRepository;

//    public boolean validateUser(String username, String password) {
//        log.debug("validateUser: user '{}' attempted login", username);
//
//        // Always do the lookup in a case-insensitive manner (lower-casing the data).
//        List<Question> questions = repository.findByUsernameIgnoreCase(username);
//
//        // We expect 0 or 1, so if we get more than 1, bail out as this is an error we don't deal with properly.
//        if (questions.size() != 1) {
//            log.debug("validateUser: found {} users", users.size());
//            return false;
//        }
//        Question u = questions.get(0);
//        // XXX - Using Java's hashCode is wrong on SO many levels, but is good enough for demonstration purposes.
//        // NEVER EVER do this in production code!
//        final String questionProvidedId = Integer.toString(password.hashCode());
//        if (!u.getHashedPassword().equals(userProvidedHash)) {
//            log.debug("validateUser: password !match");
//            return false;
//        }
//
//        // User exists, and the provided password matches the hashed password in the database.
//        log.info("validateUser: successful login for {}", username);
//        return true;
//    }

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
    public void markQuestionAsSeen(User user, Question question) {
        user.getSeenQuestions().add(question);
        userRepository.save(user);
    }
}

