package edu.carroll.cs389.service;

import edu.carroll.cs389.jpa.model.User;
import edu.carroll.cs389.jpa.model.Question;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Objects;

import static org.springframework.test.util.AssertionErrors.*;


/**
 * Tests for QuestionServiceInterface.
 * Checks if methods within QuestionServiceInterface
 */

@Transactional
@SpringBootTest
public class QuestionServiceTest {
    private static final String optionA = "blue";
    private static final String optionB = "red";
    Question question = new Question(optionA, optionB);

    Question nextQuestion = new Question("diamonds", "rhinestones");
    private static final String username = "testuser";
    private static final String password = "testpass";
    User user = new User(username, password);


    @Autowired
    private QuestionServiceInterface questionServiceInterface;

    @Autowired
    private UserServiceInterface userServiceInterface;

    @Test
    public void addQuestionTest() {
        Question question = new Question();
        question.setOptionA(optionA);
        question.setOptionB(optionB);
        assertTrue("addQuestionTest: should succeed if question was added", questionServiceInterface.addQuestion(question));
    }

    @Test
    public void checkForCorrectIndices() {
        questionServiceInterface.addQuestion(question);
        List<Question> qi = questionServiceInterface.getAllQuestions();
        assertEquals("checkForCorrectIndices: there should only be one question", qi.size(), 1);
    }

    @Test
    public void checkOptionAMatches() {
        questionServiceInterface.addQuestion(question);
        List<Question> qi = questionServiceInterface.getAllQuestions();
        Question addedQuestion = qi.get(0);
        assertEquals("checkOptionAMatches: optionA should match the question that was just added", question.getOptionA(), addedQuestion.getOptionA());
    }

    @Test
    public void checkOptionBMatches() {
        questionServiceInterface.addQuestion(question);
        List<Question> qi = questionServiceInterface.getAllQuestions();
        Question addedQuestion = qi.get(0);
        assertEquals("checkOptionBMatches: optionB should match the question that was just added", question.getOptionB(), addedQuestion.getOptionB());
    }

    @Test
    public void getAllQuestionsTest() {
        questionServiceInterface.addQuestion(question);
        assertNotNull("getAllQuestionsTest: there should be at least one question in the repository",
                questionServiceInterface.getAllQuestions());
    }

    @Test
    public void checkForNullQuestions() {
        assertNotEquals("checkForNullQuestions: there should be no questions at all",
                questionServiceInterface.getAllQuestions(), question);
    }

    @Test
    public void checkForNullOptionA() {
        assertFalse("checkForNullOptionA: optionA is null and should not be.", question.getOptionA() == null);
    }

    @Test
    public void checkForNullOptionB() {
        assertFalse("checkForNullOptionA: optionA is null and should not be.", question.getOptionB() == null);
    }


    @Test
    public void uniqueQuestionTest() {
        assertTrue("uniqueQuestionTest: should succeed if question is unique",
                questionServiceInterface.uniqueQuestion(question));
    }

    @Test
    public void uniqueOptionsTest() {
        assertFalse("uniqueOptionsTest: makes sure that different questions aren't the same with the options switched",
                Objects.equals(question.getOptionA(), nextQuestion.getOptionB()) && Objects.equals(question.getOptionB(), nextQuestion.getOptionA()));
    }

    @Test
    public void checkForDuplicateQuestions() {
        questionServiceInterface.addQuestion(question);
        Question sameQ = new Question("that", "This");
        questionServiceInterface.addQuestion(sameQ);
        questionServiceInterface.getAllQuestions();
        assertTrue("checkForDuplicateQuestions: sameQ should have added since it is a completely" +
                        " different question",
                questionServiceInterface.getAllQuestions().size() > 1);
    }

    @Test
    public void checkForDuplicateOptionA() {
        questionServiceInterface.addQuestion(question);
        Question sameQ = new Question(optionA, "This");
        questionServiceInterface.addQuestion(sameQ);
        questionServiceInterface.getAllQuestions();
        assertFalse("checkForDuplicateOptionA: sameQ should not get added since it shares the same optionA" +
                        " with another question",
                questionServiceInterface.getAllQuestions().size() > 1);
    }

    @Test
    public void checkForDuplicateOptionB() {
        questionServiceInterface.addQuestion(question);
        Question sameQ = new Question("that", optionB);
        questionServiceInterface.addQuestion(sameQ);
        questionServiceInterface.getAllQuestions();
        assertFalse("checkForDuplicateOptionB: sameQ should not get added since it shares the same optionB" +
                        " with another question",
                questionServiceInterface.getAllQuestions().size() > 1);
    }

    @Test
    public void markQuestionAsSeenTest() {
        userServiceInterface.addUser(username, password);
        User newUser = userServiceInterface.userLookupUsername(username);
        assertTrue("markQuestionAsSeenTest: inputted question should be within the getSeenQuestions list",
                questionServiceInterface.markQuestionAsSeen(newUser, question));
    }

    @Test
    public void moreSeenQuestions() {
        User user2 = new User("john", "putz");
        userServiceInterface.addUser(username, password);
        userServiceInterface.addUser(user2.getUsername(), user2.getPassword());
        User newUser = userServiceInterface.userLookupUsername(username);
        User newUser2 = userServiceInterface.userLookupUsername(user2.getUsername());
        questionServiceInterface.addQuestion(question);
        questionServiceInterface.addQuestion(nextQuestion);
        Question question3 = new Question("always", "never");
        questionServiceInterface.addQuestion(question3);
        questionServiceInterface.markQuestionAsSeen(newUser, question);
        questionServiceInterface.markQuestionAsSeen(newUser, nextQuestion);
        questionServiceInterface.markQuestionAsSeen(newUser, question3);
        questionServiceInterface.markQuestionAsSeen(newUser2, question);
        List<Question> user1List = newUser.getSeenQuestions();
        List<Question> user2List = newUser2.getSeenQuestions();
        assertTrue("moreSeenQuestions: newUser has all three questions seen, while newUser2 only has one of them seen, ",
                user1List.size() > user2List.size());
    }


    @Test
    public void getSeenQuestionTest() {
        userServiceInterface.addUser(username, password);
        User newUser = userServiceInterface.userLookupUsername(username);
        questionServiceInterface.addQuestion(question);
        questionServiceInterface.markQuestionAsSeen(newUser, question);
        assertEquals("getSeenQuestionTest: the marked question and the inputted question should be equal",
                questionServiceInterface.getSeenQuestion(newUser, question, false), question);

    }

    @Test
    public void onlyOneSeenQuestionTest() {
        userServiceInterface.addUser(username, password);
        User newUser = userServiceInterface.userLookupUsername(username);
        userServiceInterface.resetSeenQuestions(newUser);
        questionServiceInterface.addQuestion(question);
        questionServiceInterface.markQuestionAsSeen(newUser, question);
        List<Question> newUserSeenQ = newUser.getSeenQuestions();
        assertEquals("onlyOneSeenQuestionTest: there should only be one question within the seenQuestions" +
                " list for inputted user", newUserSeenQ.size(), 1);
    }

    @Test
    public void noneSeenQuestionTest() {
        userServiceInterface.addUser(username, password);
        User newUser = userServiceInterface.userLookupUsername(username);
        userServiceInterface.resetSeenQuestions(newUser);
        questionServiceInterface.addQuestion(question);
        List<Question> newUserSeenQ = newUser.getSeenQuestions();
        assertEquals("noneSeenQuestionTest: there should be no seen questions within the seenQuestions" +
                " list for inputted user", newUserSeenQ.size(), 0);
    }

    @Test
    public void deleteSeenQuestionsForOneUserAndLetTheOtherKeepTheirs() {
        userServiceInterface.addUser(username, password);
        User newUser = userServiceInterface.userLookupUsername(username);
        userServiceInterface.addUser("john", "putz");
        User newUser2 = userServiceInterface.userLookupUsername("john");
        userServiceInterface.resetSeenQuestions(newUser);
        questionServiceInterface.addQuestion(question);
        questionServiceInterface.markQuestionAsSeen(newUser2, question);
        List<Question> newUserSeenQ = newUser.getSeenQuestions();
        List<Question> newUser2SeenQ = newUser2.getSeenQuestions();
        assertTrue("deleteSeenQuestionsForOneUserAndLetTheOtherKeepTheirs: newUser2 should have more seen questions" +
                        " than newUser since newUser's seen question list got cleared",
                newUserSeenQ.size() < newUser2SeenQ.size());
    }

    @Test
    public void checkForNotSeenQuestionTest() {
        userServiceInterface.addUser(username, password);
        User newUser = userServiceInterface.userLookupUsername(username);
        questionServiceInterface.addQuestion(question);
        questionServiceInterface.markQuestionAsSeen(newUser, question);
        assertNotEquals("checkForNotSeenQuestionTest: the marked question and the inputted question should not be equal",
                questionServiceInterface.getSeenQuestion(newUser, question, false), nextQuestion);

    }

    @Test
    public void checkForCorrectSeenQuestionTest() {
        userServiceInterface.addUser(username, password);
        User newUser = userServiceInterface.userLookupUsername(username);
        questionServiceInterface.addQuestion(question);
        questionServiceInterface.addQuestion(nextQuestion);
        questionServiceInterface.markQuestionAsSeen(newUser, question);
        List<Question> newUserSeenQ = newUser.getSeenQuestions();
        assertFalse("checkForCorrectSeenQuestionTest: question should be marked as seen while nextQuestion" +
                        " should not be marked as seen",
                newUserSeenQ.contains(nextQuestion));
    }

    @Test
    public void checkForNullGetSeenQuestionTest() {
        assertNull("checkForNullGetSeenQuestionTest: no questions were marked as seen so this should" +
                        " return null",
                questionServiceInterface.getSeenQuestion(user, null, true));
    }

    @Test
    public void randomUnseenQuestionTest() {
        userServiceInterface.addUser(username, password);
        User newUser = userServiceInterface.userLookupUsername(username);
        questionServiceInterface.addQuestion(question);
        questionServiceInterface.addQuestion(nextQuestion);
        Question question3 = new Question("always", "never");
        questionServiceInterface.addQuestion(question3);
        questionServiceInterface.markQuestionAsSeen(newUser, question);
        List<Question> newUserSeenQ = newUser.getSeenQuestions();
        Question randomQ = questionServiceInterface.randomUnseenQuestion(newUser);
        assertFalse("randomUnseenQuestionTest: newUserSeenQ list should not have randomUnseenQuestion " +
                        "as an element",
                newUserSeenQ.contains(randomQ));
    }

    @Test
    public void checkToSeeRandomQuestionReturnNull() {
        userServiceInterface.addUser(username, password);
        User newUser = userServiceInterface.userLookupUsername(username);
        questionServiceInterface.addQuestion(question);
        questionServiceInterface.markQuestionAsSeen(newUser, question);
        assertNull("checkToSeeRandomQuestionReturnNull: newUserSeenQ list should not have randomUnseenQuestion " +
                        "as an element",
                questionServiceInterface.randomUnseenQuestion(newUser));
    }

    @Test
    public void checkRandomUnseenQuestionForMoreThanOneElement() {
        userServiceInterface.addUser(username, password);
        User newUser = userServiceInterface.userLookupUsername(username);
        questionServiceInterface.addQuestion(question);
        questionServiceInterface.addQuestion(nextQuestion);
        Question question3 = new Question("always", "never");
        questionServiceInterface.addQuestion(question3);
        questionServiceInterface.markQuestionAsSeen(newUser, question);
        List<Question> newUserSeenQ = newUser.getSeenQuestions();
        List<Question> unseenQ = questionServiceInterface.getAllQuestions();
        unseenQ.removeAll(newUserSeenQ);
        assertTrue("checkRandomUnseenQuestionForMoreThanOneElement: " +
                        "unseenQ list should have more than one element ",
                unseenQ.size() > 1);
    }

    @Test
    public void questionsDontMatch() {
        questionServiceInterface.addQuestion(question);
        questionServiceInterface.addQuestion(nextQuestion);
        List<Question> qi = questionServiceInterface.getAllQuestions();
        int oldQuestionIdx = qi.indexOf(question);
        int newQuestionIdx = qi.indexOf(nextQuestion);
        assertFalse("getSeenQuestionTest: will fail if previous seen question is the same as the next question",
                oldQuestionIdx == newQuestionIdx);
    }

    @Test
    public void questionIdLookupTest() {
        questionServiceInterface.addQuestion(question);
        questionServiceInterface.addQuestion(nextQuestion);
        Long questionId = question.getId();
        Long questionId2 = nextQuestion.getId();
        assertNotEquals("questionIdLookupTest: fails if question IDs are the same", questionId, questionId2);
    }

    @Test
    public void questionIdLookupNullTest() {
        Long questionId = question.getId();
        assertNull("questionIdLookupNullTest: question wasn't added so the Id should return null",
                questionServiceInterface.questionIdLookup(questionId));
    }

    @Test
    public void voteForOptionATest() {
        User user = new User(username, password);
        questionServiceInterface.addQuestion(question);
        questionServiceInterface.voteForOptionA(user, question);
        List<User> userVote = question.getVotesForOptionA();
        assertEquals("voteForOptionATest: this should return only one item ",
                userVote.size(), 1);
    }

    @Test
    public void voteForOptionBTest() {
        User user = new User(username, password);
        questionServiceInterface.addQuestion(question);
        questionServiceInterface.voteForOptionB(user, question);
        List<User> userVote = question.getVotesForOptionB();
        assertEquals("voteForOptionBTest: this should return only one item ",
                userVote.size(), 1);
    }

    @Test
    public void checkOptionAVoteIsEmpty() {
        questionServiceInterface.addQuestion(question);
        List<User> userVote = question.getVotesForOptionA();
        assertEquals("checkOptionAVoteIsEmpty: getVotesForOptionA list should be empty",
                userVote.size(), 0);
    }

    @Test
    public void checkOptionBVoteIsEmpty() {
        questionServiceInterface.addQuestion(question);
        List<User> userVote = question.getVotesForOptionB();
        assertEquals("checkOptionBVoteIsEmpty: getVotesForOptionB list should be empty",
                userVote.size(), 0);
    }

    @Test
    public void compareVotingTest() {
        User user2 = new User("john", "putz");
        User user3 = new User("Adrian", "crow");
        userServiceInterface.addUser(username, password);
        userServiceInterface.addUser(user2.getUsername(), user2.getPassword());
        userServiceInterface.addUser(user3.getUsername(), user3.getPassword());
        User newUser = userServiceInterface.userLookupUsername(username);
        User newUser2 = userServiceInterface.userLookupUsername(user2.getUsername());
        User newUser3 = userServiceInterface.userLookupUsername(user3.getUsername());
        questionServiceInterface.addQuestion(question);
        questionServiceInterface.voteForOptionA(newUser, question);
        questionServiceInterface.voteForOptionA(newUser2, question);
        questionServiceInterface.voteForOptionB(newUser3, question);
        assertTrue("compareVotingTest: optionA should have 2 votes and optionB should have 1 vote",
                question.getVotesForOptionA().size() > question.getVotesForOptionB().size());
    }
}