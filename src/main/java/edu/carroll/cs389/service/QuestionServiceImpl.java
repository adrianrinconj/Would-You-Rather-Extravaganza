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
 * Service implementation for managing questions.
 * Provides functionality to add, retrieve, and vote on questions.
 */
@Service
public class QuestionServiceImpl implements QuestionServiceInterface {

    private static final Logger log = LoggerFactory.getLogger(QuestionServiceImpl.class);
    private final QuestionRepository repository;
    private final UserServiceInterface userService;
    private final UserRepository userRepo;

    /**
     * Constructor for the QuestionServiceImpl.
     *
     * @param repository   the question repository.
     * @param userService  the user service.
     * @param userRepo     the user repository.
     */
    public QuestionServiceImpl(QuestionRepository repository, UserServiceInterface userService, UserRepository userRepo) {
        this.repository = repository;
        this.userService = userService;
        this.userRepo = userRepo;
    }

    /**
     * Retrieves all questions.
     *
     * @return an Iterable of all questions.
     */
    @Override
    public Iterable<Question> getAllQuestions() {
        return repository.findAll();
    }

    /**
     * Adds a new question if it is unique.
     *
     * @param question the question to be added.
     */
    //make happy code unindented
    @Override
    public boolean addQuestion(Question question) {
        if (uniqueQuestion(question)) {
            repository.save(question);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Checks if a question is unique.
     *
     * @param newQuestion the question to be checked.
     * @return true if the question is unique, false otherwise.
     */
    @Override
    public boolean uniqueQuestion(Question newQuestion) {
        for (Question currentQuestion : getAllQuestions()) {
            // make this line symetrical
            if (newQuestion.getOptionA().equals( currentQuestion.getOptionA()) || Objects.equals(newQuestion.getOptionB(), currentQuestion.getOptionB())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Retrieves a random unseen question for a user.
     *
     * @param currentUser the user for whom the question is retrieved.
     * @return a random unseen question or null if the user has seen all questions.
     */
    @Override
    public Question randomUnseenQuestion(User currentUser) {
        List<Question> allQ = repository.findAll();
        allQ.removeAll(currentUser.getSeenQuestions());

        if (allQ.isEmpty()) {
            return null;
        }

        // gen at start
        int idx = (int) (Math.random() * allQ.size());
        return allQ.get(idx);
    }

    /**
     * Retrieves a seen question based on the last question and the direction of navigation.
     *
     * @param currentUser the user for whom the question is retrieved.
     * @param lastQuestion the last question seen.
     * @param gettingNext true if retrieving the next question, false if retrieving the previous question.
     * @return a seen question or null if no questions are seen or input parameters are invalid.
     */
    @Override
    //clean up and comment this method
    public Question getSeenQuestion(User currentUser, Question lastQuestion, Boolean gettingNext) {
        List<Question> seenQuestions = currentUser.getSeenQuestions();

        if (seenQuestions.isEmpty()) {
            return null;
        } else if (lastQuestion == null || gettingNext == null) {
            int idx = (int) (Math.random() * (seenQuestions.size() - 1));
            return seenQuestions.get(idx);
        } else if (seenQuestions.contains(lastQuestion)) {
            int questionIndex = seenQuestions.indexOf(lastQuestion);
            if (gettingNext && questionIndex != seenQuestions.size() - 1) {
                questionIndex += 1;
            } else if (!gettingNext && questionIndex != 0) {
                questionIndex -= 1;
            }
            return seenQuestions.get(questionIndex);
        } else {
            return null;
        }
    }

    /**
     * Looks up a question by its ID.
     *
     * @param questionID the ID of the question.
     * @return the question if found, null otherwise.
     */
    @Override
    public Question questionIdLookup(Long questionID) {
        for (Question a : getAllQuestions()) {
            if (Objects.equals(a.getId(), questionID)) {
                return a;
            }
        }
        return null;
    }

    /**
     * Votes for option A of a question.
     *
     * @param user the user who is voting.
     * @param question the question being voted on.
     */
    @Override
    public void voteForOptionA(User user, Question question) {
        question.getVotesForOptionA().add(user);
        repository.save(question);
    }

    /**
     * Votes for option B of a question.
     *
     * @param user the user who is voting.
     * @param question the question being voted on.
     */
    @Override
    public void voteForOptionB(User user, Question question) {
        question.getVotesForOptionB().add(user);
        repository.save(question);
    }

    /**
     * Marks a question as seen for a user.
     *
     * @param user the user for whom the question is marked as seen.
     * @param question the question being marked as seen.
     */
    @Override
    //boolean return
    public void markQuestionAsSeen(User user, Question question) {
        if (question != null) {
            user.getSeenQuestions().add(question);
            userRepo.save(user);
        }
    }
}
