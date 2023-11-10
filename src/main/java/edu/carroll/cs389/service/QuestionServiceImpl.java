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
 * Service implementation for managing questions within the application.
 * This service provides functionalities such as adding, retrieving, and voting on questions.
 */
@Service
public class QuestionServiceImpl implements QuestionServiceInterface {

    private static final Logger log = LoggerFactory.getLogger(QuestionServiceImpl.class);
    private final QuestionRepository repository;
    private final UserServiceInterface userService;
    private final UserRepository userRepo;

    /**
     * Constructs a new instance of QuestionServiceImpl with required repositories.
     *
     * @param repository   The repository for accessing question data.
     * @param userService  The service for accessing user-related operations.
     * @param userRepo     The repository for accessing user data.
     */
    public QuestionServiceImpl(QuestionRepository repository, UserServiceInterface userService, UserRepository userRepo) {
        this.repository = repository;
        this.userService = userService;
        this.userRepo = userRepo;
    }

    /**
     * Retrieves all questions available in the repository.
     *
     * @return An Iterable collection of all questions.
     */
    @Override
    public Iterable<Question> getAllQuestions() {
        return repository.findAll();
    }

    /**
     * Adds a new question to the repository if it is unique.
     *
     * @param question The question to be added.
     * @return True if the question is added successfully, false otherwise.
     */
    @Override
    public boolean addQuestion(Question question) {
        if (uniqueQuestion(question)) {
            repository.save(question);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if a given question is unique based on its options.
     *
     * @param newQuestion The question to check for uniqueness.
     * @return True if the question is unique, false otherwise.
     */
    @Override
    public boolean uniqueQuestion(Question newQuestion) {
        for (Question currentQuestion : getAllQuestions()) {
            if (newQuestion.getOptionA().equals(currentQuestion.getOptionA()) || Objects.equals(newQuestion.getOptionB(), currentQuestion.getOptionB())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Retrieves a random question that the specified user has not seen yet.
     *
     * @param currentUser The user for whom the unseen question is to be retrieved.
     * @return A random unseen question or null if all questions have been seen.
     */
    @Override
    public Question randomUnseenQuestion(User currentUser) {
        List<Question> allQ = repository.findAll();
        allQ.removeAll(currentUser.getSeenQuestions());

        if (allQ.isEmpty()) {
            return null;
        }

        int idx = (int) (Math.random() * allQ.size());
        return allQ.get(idx);
    }

    /**
     * Retrieves a seen question based on the last seen question and the direction of navigation (next or previous).
     *
     * @param currentUser  The user for whom the seen question is retrieved.
     * @param lastQuestion The last question seen by the user.
     * @param gettingNext  Determines if the next or previous question is to be retrieved.
     * @return The seen question based on the navigation direction or null if no applicable question is found.
     */
    @Override
    public Question getSeenQuestion(User currentUser, Question lastQuestion, Boolean gettingNext) {
        List<Question> seenQuestions = currentUser.getSeenQuestions();

        // Return null if no questions have been seen
        if (seenQuestions.isEmpty()) {
            return null;
        }
        // Return a random seen question if lastQuestion or gettingNext is not specified
        else if (lastQuestion == null || gettingNext == null) {
            int idx = (int) (Math.random() * (seenQuestions.size() - 1));
            return seenQuestions.get(idx);
        }
        // Navigate to the next or previous question based on gettingNext
        // (true for next false for previous)
        else if (seenQuestions.contains(lastQuestion)) {
            //discern the location of the last question displayed in the list of seen questions
            int questionIndex = seenQuestions.indexOf(lastQuestion);
            // move the index of the question to be displayed next forward or back
            // depending on the getting next param
            if (gettingNext && questionIndex != seenQuestions.size() - 1) {
                questionIndex += 1;
            } else if (!gettingNext && questionIndex != 0) {
                questionIndex -= 1;
            }
            return seenQuestions.get(questionIndex);
        }
        // return null if all else fails
        else {
            return null;
        }
    }

    /**
     * Looks up a question by its unique identifier.
     *
     * @param questionID The identifier of the question to find.
     * @return The question if found, or null otherwise.
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
     * Records a vote for option A of a specified question by a user.
     *
     * @param user     The user casting the vote.
     * @param question The question being voted on.
     */
    @Override
    public void voteForOptionA(User user, Question question) {
        question.getVotesForOptionA().add(user);
        repository.save(question);
    }

    /**
     * Records a vote for option B of a specified question by a user.
     *
     * @param user     The user casting the vote.
     * @param question The question being voted on.
     */
    @Override
    public void voteForOptionB(User user, Question question) {
        question.getVotesForOptionB().add(user);
        repository.save(question);
    }

    /**
     * Marks a question as seen by a specific user.
     *
     * @param user     The user for whom the question is marked as seen.
     * @param question The question to mark as seen.
     * @return True if the operation is successful, false otherwise.
     */
    @Override
    public boolean markQuestionAsSeen(User user, Question question) {
        if (question != null) {
            user.getSeenQuestions().add(question);
            userRepo.save(user);
            return true;
        } else {
            return false;
        }
    }
}
