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


    //Have nate check this!!!!
    //Do I do two of these for each question, if so, did I do it right???


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


    //Have nate check this!!!!
    //Do I do two of these for each question, if so, did I do it right???
//    @Override
//    public boolean uniqueQuestion(Question newQuestion) {
//        log.debug("validateQuestion: question '{}' was an attempted submit", newQuestion);
//
//        // Always do the lookup in a case-insensitive manner (lower-casing the data).
//
//        //Ask about String.valueOf()
//        List<Question> questions = repository.findByQuestionIgnoreCase(String.valueOf(newQuestion));
//
//        // We expect 0 or 1, so if we get more than 1, bail out as this is an error we don't deal with properly.
//        if (questions.size() != 1) {
//            log.debug("uniqueQuestion: found {} questions", questions.size());
//            return false;
//        }
////            Question u = questions.get(0);
//
//
//
//        for (Question currentQuestion : getAllQuestions()) {
//            if (Objects.equals(newQuestion.getOptionA(), currentQuestion.getOptionA()) || Objects.equals(newQuestion.getOptionB(), currentQuestion.getOptionB())) {
//                return false;
//            }
//        }
//        // Question exists, and matches a question within the database. Compares to see if there is a duplicate (this should be impossible)
//        //This block of code may be redundant
//        log.info("uniqueQuestion: this is not a unique question {}", newQuestion);
//        return true;
//    }

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

