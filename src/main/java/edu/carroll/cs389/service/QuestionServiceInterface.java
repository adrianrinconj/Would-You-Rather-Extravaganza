package edu.carroll.cs389.service;

import edu.carroll.cs389.jpa.model.Question;
import edu.carroll.cs389.jpa.model.User;

import java.util.List;

/**
 * Defines the contract for the Question Service.
 * This interface provides methods for managing questions and user interactions with them.
 */
public interface QuestionServiceInterface {

    /**
     * Retrieves all questions available.
     *
     * @return An iterable collection of all questions.
     */
    List<Question> getAllQuestions();

    /**
     * Adds a new question to the system if it is unique.
     *
     * @param question The question to be added.
     * @return True if the question is successfully added, false otherwise.
     */
    boolean addQuestion(Question question);

    /**
     * Checks if a given question is unique in the system.
     *
     * @param newQuestion The question to check for uniqueness.
     * @return True if the question is unique, false otherwise.
     */
    boolean uniqueQuestion(Question newQuestion);

    /**
     * Retrieves a random unseen question for a specified user.
     *
     * @param currentUser The user for whom the unseen question is to be retrieved.
     * @return A random unseen question or null if all questions have been seen by the user.
     */
    Question randomUnseenQuestion(User currentUser);

    /**
     * Retrieves a seen question for a user, based on the last question seen and the direction of navigation.
     *
     * @param currentUser The user for whom the question is retrieved.
     * @param lastQuestion The last question seen by the user.
     * @param gettingNext A flag indicating whether to get the next or previous seen question.
     * @return A seen question based on the specified parameters or null if no suitable question is found.
     */
    Question getSeenQuestion(User currentUser, Question lastQuestion, Boolean gettingNext);

    /**
     * Looks up a question by its unique identifier.
     *
     * @param questionID The identifier of the question.
     * @return The question if found, or null otherwise.
     */
    Question questionIdLookup(Long questionID);

    /**
     * Records a vote for option A on a specified question by a user.
     *
     * @param user     The user casting the vote.
     * @param question The question being voted on.
     */
    void voteForOptionA(User user, Question question);

    /**
     * Records a vote for option B on a specified question by a user.
     *
     * @param user     The user casting the vote.
     * @param question The question being voted on.
     */
    void voteForOptionB(User user, Question question);

    /**
     * Marks a specific question as seen by a user.
     *
     * @param user     The user for whom the question is marked as seen.
     * @param question The question to be marked as seen.
     * @return True if the question is successfully marked as seen, false otherwise.
     */
    boolean markQuestionAsSeen(User user, Question question);
}
