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
 * Checks if methods within QuestionServiceInterface function as expected.
 */
@Transactional
@SpringBootTest
public class QuestionServiceTest {
    private static final String optionA = "blue";
    private static final String optionB = "red";
    Question question = new Question(optionA, optionB);

//    Question nextQuestions = new Question("diamonds", "rhinestones");
    private static final String username = "testuser";
    private static final String password = "testpass";
    User user = new User(username, password);

    @Autowired
    private QuestionServiceInterface questionServiceInterface;

    @Autowired
    private UserServiceInterface userServiceInterface;

    /**
     * Test to verify if a question can be successfully added.
     */
    @Test
    public void addQuestionTest() {
        Question question = new Question();
        question.setOptionA(optionA);
        question.setOptionB(optionB);
        List<Question> newList = questionServiceInterface.getAllQuestions();
        assertEquals("addQuestionTest: list of questions should have size of 0", 0, newList.size());
        assertTrue("addQuestionTest: should succeed if question was added", questionServiceInterface.addQuestion(question));
        List<Question> qList = questionServiceInterface.getAllQuestions();
        assertEquals("addQuestionTest: list of questions should have size of 1", 1, qList.size());
        Question qWithinList = qList.get(0);
        assertNotNull("addQuestionTest: question in list should not be null", qWithinList);
        assertEquals("addQuestionTest: question in list optionA should match added question", optionA, qWithinList.getOptionA());
        assertEquals("addQuestionTest: question in list optionB should match added question", optionB, qWithinList.getOptionB());

    }

    /**
     * Test to ensure that the index of questions is correctly maintained after adding a question.
     */
    @Test
    public void addTwoQuestionsTest() {
        Question question = new Question();
        question.setOptionA(optionA);
        question.setOptionB(optionB);
        Question question2 = new Question("this", "that");
        List<Question> newList = questionServiceInterface.getAllQuestions();
        assertEquals("addTwoQuestionsTest: list of questions should have size of 0", 0, newList.size());
        assertTrue("addTwoQuestionsTest: should succeed if question was added", questionServiceInterface.addQuestion(question));
        List<Question> qList = questionServiceInterface.getAllQuestions();
        assertEquals("addQuestionTest: list of questions should have size of 1", 1, qList.size());
        Question qWithinList = qList.get(0);
        assertNotNull("addTwoQuestionsTest: question in list should not be null", qWithinList);
        assertEquals("addTwoQuestionsTest: question in list optionA should match added question", optionA, qWithinList.getOptionA());
        assertEquals("addTwoQuestionsTest: question in list optionB should match added question", optionB, qWithinList.getOptionB());
        assertTrue("addTwoQuestionsTest: should succeed if question2 was added", questionServiceInterface.addQuestion(question2));
        List<Question> qList2 = questionServiceInterface.getAllQuestions();
        assertEquals("addQuestionTest: list of questions should have size of 2", 2, qList2.size());
        Question qWithinList2 = qList2.get(1);
        assertNotNull("addTwoQuestionsTest: question2 in list should not be null", qWithinList2);
        assertEquals("addTwoQuestionsTest: question2 in list optionA should match added question", question2.getOptionA(), qWithinList2.getOptionA());
        assertEquals("addTwoQuestionsTest: question2 in list optionB should match added question", question2.getOptionB(), qWithinList2.getOptionB());
    }

    @Test
    public void addThreeQuestionsTest() {
        Question question = new Question();
        question.setOptionA(optionA);
        question.setOptionB(optionB);
        Question question2 = new Question("this", "that");
        Question question3 = new Question("up", "down");
        List<Question> newList = questionServiceInterface.getAllQuestions();
        assertEquals("addThreeQuestionsTest: list of questions should have size of 0", 0, newList.size());
        assertTrue("addThreeQuestionsTest: should succeed if question was added", questionServiceInterface.addQuestion(question));
        List<Question> qList = questionServiceInterface.getAllQuestions();
        assertEquals("addThreeQuestionTest: list of questions should have size of 1", 1, qList.size());
        Question qWithinList = qList.get(0);
        assertNotNull("addThreeQuestionsTest: question in list should not be null", qWithinList);
        assertEquals("addThreeQuestionsTest: question in list optionA should match added question", optionA, qWithinList.getOptionA());
        assertEquals("addThreeQuestionsTest: question in list optionB should match added question", optionB, qWithinList.getOptionB());
        assertTrue("addThreeQuestionsTest: should succeed if question2 was added", questionServiceInterface.addQuestion(question2));
        List<Question> qList2 = questionServiceInterface.getAllQuestions();
        assertEquals("addThreeQuestionTest: list of questions should have size of 2", 2, qList2.size());
        Question qWithinList2 = qList2.get(1);
        assertNotNull("addThreeQuestionsTest: question2 in list should not be null", qWithinList2);
        assertEquals("addThreeQuestionsTest: question2 in list optionA should match added question", question2.getOptionA(), qWithinList2.getOptionA());
        assertTrue("addThreeQuestionsTest: should succeed if question3 was added", questionServiceInterface.addQuestion(question3));
        assertEquals("addThreeQuestionsTest: question2 in list optionB should match added question", question2.getOptionB(), qWithinList2.getOptionB());
        List<Question> qList3 = questionServiceInterface.getAllQuestions();
        assertEquals("addThreeQuestionTest: list of questions should have size of 3", 3, qList3.size());
        Question qWithinList3 = qList3.get(2);
        assertNotNull("addThreeQuestionsTest: question3 in list should not be null", qWithinList3);
        assertEquals("addThreeQuestionsTest: question3 in list optionA should match added question", question3.getOptionA(), qWithinList3.getOptionA());
        assertEquals("addThreeQuestionsTest: question3 in list optionB should match added question", question3.getOptionB(), qWithinList3.getOptionB());
    }

//    @Test
//    public void checkForCorrectIndices() {
//        questionServiceInterface.addQuestion(question);
//        List<Question> qi = questionServiceInterface.getAllQuestions();
//        assertEquals("checkForCorrectIndices: there should only be one question", qi.size(), 1);
//    }

    /**
     * Test to verify that the option A in the added question matches the expected value.
     */
    @Test
    public void checkOptionAMatches() {
        assertTrue("checkOptionAMatches: should run if question was added", questionServiceInterface.addQuestion(question));
        List<Question> qi = questionServiceInterface.getAllQuestions();
        Question addedQuestion = qi.get(0);
        assertEquals("checkOptionAMatches: optionA should match the question that was just added", question.getOptionA(), addedQuestion.getOptionA());
    }

    /**
     * Test to verify that the option B in the added question matches the expected value.
     */
    @Test
    public void checkOptionBMatches() {
        assertTrue("checkOptionBMatches: should run if question was added", questionServiceInterface.addQuestion(question));
        List<Question> qi = questionServiceInterface.getAllQuestions();
        Question addedQuestion = qi.get(0);
        assertEquals("checkOptionBMatches: optionB should match the question that was just added", question.getOptionB(), addedQuestion.getOptionB());
    }

    @Test
    public void getAllQuestionsTest() {
        List<Question> qList = questionServiceInterface.getAllQuestions();
        assertNotNull("getAllQuestionsTest: qList should not be null", qList);
        assertEquals("getAllQuestionsTest: there should be no questions in the repository",
                qList.size(), 0);
        assertTrue("getAllQuestionTest: question should have been added",
                questionServiceInterface.addQuestion(question));
        qList = questionServiceInterface.getAllQuestions();
        assertNotNull("getAllQuestionsTest: qList should not be null", qList);
        assertEquals("getAllQuestionsTest: there should be 1 question in qList",
                qList.size(), 1);
    }

//    @Test
//    public void checkForNullQuestions() {
//        assertNull("checkForNullQuestions: there should at least be zero questions within the repository",
//                questionServiceInterface.getAllQuestions());
//        assertNotEquals("checkForNullQuestions: there should be no questions at all",
//                questionServiceInterface.getAllQuestions(), question);
//    }

    @Test
    public void checkForNullOptionA() {
        assertTrue("checkForNullOptionA: this should run if getOptionA is working", Objects.equals(question.getOptionA(), optionA));
        assertFalse("checkForNullOptionA: optionA is null and should not be.", question.getOptionA() == null);
    }

    @Test
    public void checkForNullOptionB() {
        assertTrue("checkForNullOptionB: this should run if getOptionB is working", Objects.equals(question.getOptionB(), optionB));
        assertFalse("checkForNullOptionB: optionB is null and should not be.", question.getOptionB() == null);
    }


//    @Test
//    public void uniqueQuestionTest() {
//        Question question = new Question(optionA, optionB);
//        assertTrue("uniqueQuestionTest: should run if question was added", questionServiceInterface.addQuestion(question));
//        Question question2 = new Question("This", "that");
//        assertTrue("uniqueQuestionTest: should run if question2 was added", questionServiceInterface.addQuestion(question2));
//        questionServiceInterface.addQuestion(question2);
//        List<Question> thisList = questionServiceInterface.getAllQuestions();
//        assertTrue("uniqueQuestionTest: this should run if getOptionA for question is working", Objects.equals(question.getOptionA(), optionA));
//        assertTrue("uniqueQuestionTest: this should run if getOptionB for question is working", Objects.equals(question.getOptionB(), optionB));
//        assertTrue("uniqueQuestionTest: this should run if getOptionA for question2 is working", Objects.equals(question2.getOptionA(), "This"));
//        assertTrue("uniqueQuestionTest: this should run if getOptionB for question2 is working", Objects.equals(question2.getOptionB(), "that"));
//        assertTrue("uniqueQuestionTest: should succeed if question is unique",
//                questionServiceInterface.uniqueQuestion(question));
//    }

    @Test
    public void uniqueOptionsTest() {
        Question nextQuestions = new Question("diamonds", "rhinestones");
        assertFalse("uniqueOptionsTest: makes sure that different questions aren't the same with the options switched",
                Objects.equals(question.getOptionA(), nextQuestions.getOptionB()) && Objects.equals(question.getOptionB(), nextQuestions.getOptionA()));
    }

    @Test
    public void checkForDuplicateQuestions() {
        assertTrue("checkForDuplicateQuestions: should run if question was added",questionServiceInterface.addQuestion(question));
        Question sameQ = new Question("that", "This");
        /// add qList
        assertTrue("checkForDuplicateQuestions: should run if sameQ was added",questionServiceInterface.addQuestion(sameQ));
        assertNotNull("checkForDuplicateQuestions: list should not be empty", questionServiceInterface.getAllQuestions());
        assertTrue("checkForDuplicateQuestions: sameQ should have added since it is a completely" +
                        " different question",
                questionServiceInterface.getAllQuestions().size() > 1);
    }

    @Test
    public void checkForDuplicateOptionA() {
        assertTrue("checkForDuplicateOptionA: this should run if question was added",questionServiceInterface.addQuestion(question));
        Question sameQ = new Question(optionA, "This");
        assertFalse("checkForDuplicateOptionA: this should not run since sameQ shares the same optionA as question",questionServiceInterface.addQuestion(sameQ));
//        assertNotNull("checkForDuplicateOptionA: list should not be empty", questionServiceInterface.getAllQuestions());
        assertFalse("checkForDuplicateOptionA: sameQ should not get added since it shares the same optionA" +
                        " with another question",
                questionServiceInterface.getAllQuestions().size() > 1);
    }

    @Test
    public void checkForDuplicateOptionB() {
        assertTrue("checkForDuplicateOptionB: this should run if question was added",questionServiceInterface.addQuestion(question));
        Question sameQ = new Question("that", optionB);
        assertFalse("checkForDuplicateOptionB: this should not run since sameQ shares the same optionA as question",questionServiceInterface.addQuestion(sameQ));
//        assertNotNull("checkForDuplicateOptionB: list should not be empty", questionServiceInterface.getAllQuestions());
        assertFalse("checkForDuplicateOptionB: sameQ should not get added since it shares the same optionB" +
                        " with another question",
                questionServiceInterface.getAllQuestions().size() > 1);
    }
    // ... (rest of the test methods with their implementations)

    /**
     * Test to verify the functionality of marking a question as seen by a user.
     */
    @Test
    public void markQuestionAsSeenTest() {
        assertTrue("markQuestionAsSeenTest: should run if user was added", userServiceInterface.addUser(username, password));
        User newUser = userServiceInterface.userLookupUsername(username);
        assertTrue("markQuestionAsSeenTest: inputted question should be within the getSeenQuestions list",
                questionServiceInterface.markQuestionAsSeen(newUser, question));
    }

    /**
     * Test to compare the number of seen questions between two users.
     */
    @Test
    public void moreSeenQuestions() {
        Question nextQuestions = new Question("diamonds", "rhinestones");
        User user2 = new User("john", "putz");
        assertTrue("moreSeenQuestions: should run if user was added",
                userServiceInterface.addUser(username, password));
        assertTrue("moreSeenQuestions: should run if user was added",
                userServiceInterface.addUser(user2.getUsername(), user2.getPassword()));
        User newUser = userServiceInterface.userLookupUsername(username);
        User newUser2 = userServiceInterface.userLookupUsername(user2.getUsername());
        assertTrue("moreSeenQuestions: should run if question was added",
                questionServiceInterface.addQuestion(question));
        assertTrue("moreSeenQuestions: should run if nextQuestions was added",
                questionServiceInterface.addQuestion(nextQuestions));
        Question question3 = new Question("always", "never");
        assertTrue("moreSeenQuestions: should run if question3 was added",
                questionServiceInterface.addQuestion(question3));
        assertTrue("moreSeenQuestions: should run if question was marked for user",
                questionServiceInterface.markQuestionAsSeen(newUser, question));
        assertTrue("moreSeenQuestions: should run if nextQuestions was marked for user",
                questionServiceInterface.markQuestionAsSeen(newUser, nextQuestions));
        assertTrue("moreSeenQuestions: should run if question3 was marked for user",
                questionServiceInterface.markQuestionAsSeen(newUser, question3));
        assertTrue("moreSeenQuestions: should run if question was marked for user2",
                questionServiceInterface.markQuestionAsSeen(newUser2, question));
        List<Question> user1List = newUser.getSeenQuestions();
        List<Question> user2List = newUser2.getSeenQuestions();
        assertTrue("moreSeenQuestions: newUser has all three questions seen, while newUser2 only has one of them seen, ",
                user1List.size() > user2List.size());
    }

    /**
     * Test to verify the functionality of retrieving a seen question.
     */
    @Test
    public void getSeenQuestionTest() {
        assertTrue("getSeenQuestionTest: should run if user was added",
                userServiceInterface.addUser(username, password));
        User newUser = userServiceInterface.userLookupUsername(username);
        assertTrue("getSeenQuestionTest: should run if question was added",
                questionServiceInterface.addQuestion(question));
        assertTrue("getSeenQuestionTest: should run if newUser had question marked as seen",
                questionServiceInterface.markQuestionAsSeen(newUser, question));
        assertEquals("getSeenQuestionTest: the marked question and the inputted question should be equal",
                questionServiceInterface.getSeenQuestion(newUser, question, false), question);
    }

    /**
     * Test to ensure only one question is seen for a user after resetting seen questions.
     */
    @Test
    public void onlyOneSeenQuestionTest() {
        assertTrue("onlyOneSeenQuestionTest: should run if user was added",
                userServiceInterface.addUser(username, password));
        User newUser = userServiceInterface.userLookupUsername(username);
        userServiceInterface.resetSeenQuestions(newUser);
        assertEquals("onlyOneSeenQuestionTest: should run if the resetSeenQuestions method worked",
                newUser.getSeenQuestions().size(), 0);
        assertTrue("onlyOneSeenQuestionTest: should run if question was added",
                questionServiceInterface.addQuestion(question));
        assertTrue("onlyOneSeenQuestionTest: should run if question is marked for newUser",
                questionServiceInterface.markQuestionAsSeen(newUser, question));
        List<Question> newUserSeenQ = newUser.getSeenQuestions();
        assertEquals("onlyOneSeenQuestionTest: there should only be one question within the seenQuestions" +
                " list for inputted user", newUserSeenQ.size(), 1);
    }

    /**
     * Test to verify that no questions are seen for a new user.
     */
    @Test
    public void noneSeenQuestionTest() {
        assertTrue("noneSeenQuestionTest: should run if user was added",
                userServiceInterface.addUser(username, password));
        User newUser = userServiceInterface.userLookupUsername(username);
        userServiceInterface.resetSeenQuestions(newUser);
        assertEquals("noneSeenQuestionTest: should run if the resetSeenQuestions method worked",
                newUser.getSeenQuestions().size(), 0);
        assertTrue("noneSeenQuestionTest: should run if question was added",
                questionServiceInterface.addQuestion(question));
        List<Question> newUserSeenQ = newUser.getSeenQuestions();
        assertEquals("noneSeenQuestionTest: there should be no seen questions within the seenQuestions" +
                " list for inputted user", newUserSeenQ.size(), 0);
    }

    /**
     * Test to check behavior when seen questions are reset for one user but not for another.
     */
    @Test
    public void deleteSeenQuestionsForOneUserAndLetTheOtherKeepTheirs() {
        assertTrue("deleteSeenQuestionsForOneUserAndLetTheOtherKeepTheirs: " +
                "should run if user was added",
                userServiceInterface.addUser(username, password));
        User newUser = userServiceInterface.userLookupUsername(username);
        assertTrue("deleteSeenQuestionsForOneUserAndLetTheOtherKeepTheirs: " +
                    "should run if user 'john' was added",
                    userServiceInterface.addUser("john", "putz"));
        User newUser2 = userServiceInterface.userLookupUsername("john");
        userServiceInterface.resetSeenQuestions(newUser);
        assertEquals("deleteSeenQuestionsForOneUserAndLetTheOtherKeepTheirs: " +
                    "should run if the resetSeenQuestions method worked",
                    newUser.getSeenQuestions().size(), 0);
        assertTrue("deleteSeenQuestionsForOneUserAndLetTheOtherKeepTheirs: " +
                "should run if question was added to the interface",
                questionServiceInterface.addQuestion(question));
        assertTrue("deleteSeenQuestionsForOneUserAndLetTheOtherKeepTheirs: " +
                "should run if question was marked as seen for newUser2",
                questionServiceInterface.markQuestionAsSeen(newUser2, question));
        List<Question> newUserSeenQ = newUser.getSeenQuestions();
        List<Question> newUser2SeenQ = newUser2.getSeenQuestions();
        assertTrue("deleteSeenQuestionsForOneUserAndLetTheOtherKeepTheirs: newUser2 should have more seen questions" +
                        " than newUser since newUser's seen question list got cleared",
                newUserSeenQ.size() < newUser2SeenQ.size());
    }

    /**
     * Test to ensure a not seen question is correctly identified.
     */
    @Test
    public void checkForNotSeenQuestionTest() {
        Question nextQuestions = new Question("diamonds", "rhinestones");
        assertTrue("checkForNotSeenQuestionTest: should run if user was added",
                userServiceInterface.addUser(username, password));
        User newUser = userServiceInterface.userLookupUsername(username);
        assertTrue("checkForNotSeenQuestionTest: should run if question was added",
                questionServiceInterface.addQuestion(question));
        assertTrue("checkForNotSeenQuestionTest: should run if question was marked as seen for newUser",
                questionServiceInterface.markQuestionAsSeen(newUser, question));
        assertNotEquals("checkForNotSeenQuestionTest: the marked question and the inputted question should not be equal",
                questionServiceInterface.getSeenQuestion(newUser, question, false), nextQuestions);

    }

    /**
     * Test to verify correct seen question retrieval behavior.
     */
    @Test
    public void checkForCorrectSeenQuestionTest() {
        Question nextQuestions = new Question("diamonds", "rhinestones");
        assertTrue("checkForCorrectSeenQuestionTest: should run if new user was added",
                userServiceInterface.addUser(username, password));
        User newUser = userServiceInterface.userLookupUsername(username);
        assertTrue("checkForCorrectSeenQuestionTest: should run if question was added",
                questionServiceInterface.addQuestion(question));
        assertTrue("checkForCorrectSeenQuestionTest: should run if nextQuestions was added",
        questionServiceInterface.addQuestion(nextQuestions));
        assertTrue("checkForCorrectSeenQuestionTest: should run if question was marked as seen for newUser",
                questionServiceInterface.markQuestionAsSeen(newUser, question));
        List<Question> newUserSeenQ = newUser.getSeenQuestions();
        assertFalse("checkForCorrectSeenQuestionTest: question should be marked as seen while nextQuestion" +
                        " should not be marked as seen",
                newUserSeenQ.contains(nextQuestions));
    }

    /**
     * Test to verify correct behavior when getSeenQuestion is used when no questions have been  marked seen.
     */
    @Test
    public void checkForNullGetSeenQuestionTest() {
        assertNull("checkForNullGetSeenQuestionTest: no questions were marked as seen so this should" +
                        " return null",
                questionServiceInterface.getSeenQuestion(user, null, true));
    }

    /**
     * Test confirming that randomUnseenQuestionTest does not return a seen question
     */
    @Test
    public void randomUnseenQuestionTest() {
        Question nextQuestions = new Question("diamonds", "rhinestones");
        assertTrue("randomUnseenQuestionsTest: should run if user was added",
                userServiceInterface.addUser(username, password));
        User newUser = userServiceInterface.userLookupUsername(username);
        assertTrue("randomUnseenQuestionTest: should run if question was added",
                questionServiceInterface.addQuestion(question));
        assertTrue("randomUnseenQuestionTest: should run if nextQuestions was added",
                questionServiceInterface.addQuestion(nextQuestions));
        Question question3 = new Question("always", "never");
        assertTrue("randomUnseenQuestionTest: should run if question3 was added",
                questionServiceInterface.addQuestion(question3));
        assertTrue("randomUnseenQuestionTest: should run if question was marked as seen for newUser",
                questionServiceInterface.markQuestionAsSeen(newUser, question));
        List<Question> newUserSeenQ = newUser.getSeenQuestions();
        Question randomQ = questionServiceInterface.randomUnseenQuestion(newUser);
        assertFalse("randomUnseenQuestionTest: newUserSeenQ list should not have randomUnseenQuestion " +
                        "as an element",
                newUserSeenQ.contains(randomQ));
    }

    /**
     * Test certifying that randomUnseenQuestion does not return a question when all questions are seen
     */
    @Test
    public void checkToSeeRandomQuestionReturnNull() {
        assertTrue("checkToSeeRandomQuestionReturnNull: should run if user was added",
                userServiceInterface.addUser(username, password));
        User newUser = userServiceInterface.userLookupUsername(username);
        assertTrue("checkToSeeRandomQuestionReturnNull: should run if question was added",
                questionServiceInterface.addQuestion(question));
        assertTrue("checkToSeeRandomQuestionReturnNull: should run if question was marked as seen for newUser",
                questionServiceInterface.markQuestionAsSeen(newUser, question));
        assertNull("checkToSeeRandomQuestionReturnNull: newUserSeenQ list should not have randomUnseenQuestion " +
                        "as an element",
                questionServiceInterface.randomUnseenQuestion(newUser));
    }

    /**
     * Test certifying when there are multiple questions a user has not seen, that the users unseen question list is greater than 1
     */
    @Test
    public void checkRandomUnseenQuestionForMoreThanOneElement() {
        Question nextQuestions = new Question("diamonds", "rhinestones");
        assertTrue("checkRandomUnseenQuestionForMoreThanOneElement: should run if user was added",
                userServiceInterface.addUser(username, password));
        User newUser = userServiceInterface.userLookupUsername(username);
        assertTrue("checkRandomUnseenQuestionForMoreThanOneElement: should run if question was added",
                questionServiceInterface.addQuestion(question));
        assertTrue("checkRandomUnseenQuestionForMoreThanOneElement: should run if nextQuestions was added",
                questionServiceInterface.addQuestion(nextQuestions));
        Question question3 = new Question("always", "never");
        assertTrue("checkRandomUnseenQuestionForMoreThanOneElement: should run if question3 was added",
                questionServiceInterface.addQuestion(question3));
        assertTrue("checkRandomUnseenQuestionForMoreThanOneElement: should run if question was marked as seen",
                questionServiceInterface.markQuestionAsSeen(newUser, question));
        List<Question> newUserSeenQ = newUser.getSeenQuestions();
        List<Question> unseenQ = questionServiceInterface.getAllQuestions();
        unseenQ.removeAll(newUserSeenQ);
        assertTrue("checkRandomUnseenQuestionForMoreThanOneElement: " +
                        "unseenQ list should have more than one element ",
                unseenQ.size() > 1);
    }

    /**
     * Test certifying that questions are not duplicate in the service
     */
    @Test
    public void questionsDontMatch() {
        Question nextQuestions = new Question("diamonds", "rhinestones");
        assertTrue("questionsDontMatch: should run if question was added",
                questionServiceInterface.addQuestion(question));
        assertTrue("questionsDontMatch: should run if nextQuestions was added",
                questionServiceInterface.addQuestion(nextQuestions));
        List<Question> qi = questionServiceInterface.getAllQuestions();
        int oldQuestionIdx = qi.indexOf(question);
        int newQuestionIdx = qi.indexOf(nextQuestions);
        assertFalse("getSeenQuestionTest: will fail if previous seen question is the same as the next question",
                oldQuestionIdx == newQuestionIdx);
    }

    /**
     * Test certifying that questions do not have duplicate ID's
     */
    @Test
    public void questionIdLookupTest() {
        Question nextQuestions = new Question("diamonds", "rhinestones");
        assertTrue("questionIdLookupTest: should run if question was added",
                questionServiceInterface.addQuestion(question));
        assertTrue("questionsDontMatch: should run if nextQuestions was added",
                questionServiceInterface.addQuestion(nextQuestions));
        Long questionId = question.getId();
        Long questionId2 = nextQuestions.getId();
        assertNotEquals("questionIdLookupTest: fails if question IDs are the same", questionId, questionId2);
    }

    /**
     * Test certifying that the questionIdLookup returns null when searching for a bad ID
     */
    @Test
    public void questionIdLookupNullTest() {
        Long questionId = question.getId();
        assertNull("questionIdLookupNullTest: question wasn't added so the Id should return null",
                questionServiceInterface.questionIdLookup(questionId));
    }

    /**
     * Test certifying that question voting for option A counts properly
     */
    @Test
    public void voteForOptionATest() {
        User user = new User(username, password);
        assertTrue("voteForOptionATest: should run if question was added",
                questionServiceInterface.addQuestion(question));
        questionServiceInterface.voteForOptionA(user, question);
        assertTrue("voteForOptionATest: should run if optionA was voted for question",
                question.getVotesForOptionA().size() == 1);
        List<User> userVote = question.getVotesForOptionA();
        assertEquals("voteForOptionATest: this should return only one item ",
                userVote.size(), 1);
    }

    /**
     * Test certifying that question voting for option B counts properly
     */
    @Test
    public void voteForOptionBTest() {
        User user = new User(username, password);
        assertTrue("voteForOptionBTest: should run if question was added",
                questionServiceInterface.addQuestion(question));
        questionServiceInterface.voteForOptionB(user, question);
        assertTrue("voteForOptionBTest: should run if optionB was voted for question",
                question.getVotesForOptionB().size() == 1);
        List<User> userVote = question.getVotesForOptionB();
        assertEquals("voteForOptionBTest: this should return only one item ",
                userVote.size(), 1);
    }

    /**
     * Test certifying that votes for option A are not falsely counted
     */
    @Test
    public void checkOptionAVoteIsEmpty() {
        assertTrue("checkOptionAVoteIsEmpty: should run if question was added",
                questionServiceInterface.addQuestion(question));
        List<User> userVote = question.getVotesForOptionA();
        assertEquals("checkOptionAVoteIsEmpty: getVotesForOptionA list should be empty",
                userVote.size(), 0);
    }

    /**
     * Test certifying that votes for option B are not falsely counted.
     */
    @Test
    public void checkOptionBVoteIsEmpty() {
        assertTrue("checkOptionBVoteIsEmpty: should run if question was added",
                questionServiceInterface.addQuestion(question));
        List<User> userVote = question.getVotesForOptionB();
        assertEquals("checkOptionBVoteIsEmpty: getVotesForOptionB list should be empty",
                userVote.size(), 0);
    }

    /**
     * Combination Test certifying that when multiple votes happen for various options they are counted properly
     */
    @Test
    public void compareVotingTest() {
        User user2 = new User("john", "putz");
        User user3 = new User("Adrian", "crow");
        assertTrue("compareVotingTest: should run if user was added",
                userServiceInterface.addUser(username, password));
        assertTrue("compareVotingTest: should run if user 'john' was added",
                userServiceInterface.addUser(user2.getUsername(), user2.getPassword()));
        assertTrue("compareVotingTest: should run if user 'Adrian' was added",
                userServiceInterface.addUser(user3.getUsername(), user3.getPassword()));
        User newUser = userServiceInterface.userLookupUsername(username);
        User newUser2 = userServiceInterface.userLookupUsername(user2.getUsername());
        User newUser3 = userServiceInterface.userLookupUsername(user3.getUsername());
        assertTrue("compareVotingTest: should run if question was added",
                questionServiceInterface.addQuestion(question));
        questionServiceInterface.voteForOptionA(newUser, question);
        questionServiceInterface.voteForOptionA(newUser2, question);
        questionServiceInterface.voteForOptionB(newUser3, question);
        assertTrue("compareVotingTest: optionA should have 2 votes and optionB should have 1 vote",
                question.getVotesForOptionA().size() > question.getVotesForOptionB().size());
    }
}