

package edu.carroll.cs389.service;

import edu.carroll.cs389.jpa.model.Question;
import edu.carroll.cs389.jpa.model.User;

/**
 * Defines the contract for the Question Service.
 */
public interface QuestionServiceInterface {

    /**
     * Fetches all questions.
     *
     * @return An iterable of all questions.
     */
    Iterable<Question> getAllQuestions();

    /**
     * Adds a new question.
     *
     * @param question The question to be added.
     */
    void addQuestion(Question question);

    /**
     * Checks if a question is unique.
     *
     * @param newQuestion The question to be checked.
     * @return True if the question is unique, false otherwise.
     */
    boolean uniqueQuestion(Question newQuestion);

    /**
     * Fetches a random unseen question for a user.
     *
     * @param currentUser The current user.
     * @return A random unseen question.
     */
    Question randomUnseenQuestion(User currentUser);

    Question getSeenQuestion(User currentUser, Question lastQuestion, Boolean gettingNext);

    Question questionIdLookup (Long questionID);

    void voteForOptionA(User user, Question question);

    void voteForOptionB(User user, Question question);

    /**
     * Marks a question as seen for a user.
     *
     * @param user     The user.
     * @param question The question to be marked as seen.
     */
    void markQuestionAsSeen(User user, Question question);
}

