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

    /**
     * Checks to see if question was added.
     */

    @Test
    public void addQuestionTest() {
        Question question = new Question();
        question.setOptionA(optionA);
        question.setOptionB(optionB);
        assertTrue("addQuestionTest: should succeed if question was added", questionServiceInterface.addQuestion(question));
    }

    @Test
    public void checkForCorrectIndices(){
        questionServiceInterface.addQuestion(question);
        List<Question> qi = questionServiceInterface.getAllQuestions();
        assertEquals("checkForCorrectIndices: there should only be one question", qi.size(), 1);
    }

    @Test
    public void checkOptionAMatches(){
        questionServiceInterface.addQuestion(question);
        List<Question> qi = questionServiceInterface.getAllQuestions();
        Question addedQuestion = qi.get(0);
        assertEquals("checkOptionAMatches: optionA should match the question that was just added", question.getOptionA(), addedQuestion.getOptionA());
    }

    @Test
    public void checkOptionBMatches(){
        questionServiceInterface.addQuestion(question);
        List<Question> qi = questionServiceInterface.getAllQuestions();
        Question addedQuestion = qi.get(0);
        assertEquals("checkOptionBMatches: optionB should match the question that was just added", question.getOptionB(), addedQuestion.getOptionB());
    }

    @Test
    public void getAllQuestionsTest(){
        questionServiceInterface.addQuestion(question);
        assertNotNull("getAllQuestionsTest: there should be at least one question in the repository",
                questionServiceInterface.getAllQuestions());
    }

    @Test
    public void checkForNullQuestions(){
        assertNotEquals("checkForNullQuestions: there should be no questions at all",
                questionServiceInterface.getAllQuestions(), question);
    }
//    @Test
//    public void checkForNullOptionA(){
//        Question q = new Question();
//        q.setOptionA(null);
//        q.setOptionB(optionB);
////        questionServiceInterface.addQuestion(q);
//        Long qId = q.getId();
////        Integer qIdInt = (Integer)qId;
////        assertTrue("checkForNullOptionA: optionA is null and should not allow question to be added",
////                questionRepository.existsById(Math.toIntExact(qId)));
//        assertTrue("checkForNullOptionA: optionA is null and should not allow question to be added",
//                q.getOptionA() == null);
//
////        assertFalse("addQuestionTest: will fail if optionA is added and optionB is not added", questionServiceInterface
//    }
//
//
//    @Test
//    public void checkForNullOptionB(){
//        Question q = new Question();
//        q.setOptionA(optionA);
//        q.setOptionB(null);
//        assertFalse("checkForNullOptionB: optionB is null and should not allow question to be added",
//                q.getOptionB() == null);
//
////        assertFalse("addQuestionTest: will fail if optionA is added and optionB is not added", questionServiceInterface
//    }

    @Test
    public void checkForNullOptionA(){
        assertFalse("checkForNullOptionA: optionA is null and should not be.",  question.getOptionA() == null);
    }

    @Test
    public void checkForNullOptionB(){
        assertFalse("checkForNullOptionA: optionA is null and should not be.",  question.getOptionB() == null);
    }


    @Test
    public void uniqueQuestionTest() {
        assertTrue("uniqueQuestionTest: should succeed if question is unique",
                questionServiceInterface.uniqueQuestion(question));
    }

//    @Test
//    public void duplicateUniqueQuestionTest(){
//        questionServiceInterface.addQuestion(question);
//        Question exactQ = new Question(optionA, optionB);
//        questionServiceInterface.addQuestion(exactQ);
//        questionServiceInterface.addQuestion(nextQuestion);
//        questionServiceInterface.getAllQuestions();
//        List<Question> allQuestions = questionServiceInterface.getAllQuestions();
//        assertTrue("duplicateUniqueQuestionTest: question should be unique since exactQ wasn't able" +
//                " to be added",  questionServiceInterface.uniqueQuestion(question));
//    }

    @Test
    public void uniqueOptionsTest(){
        assertFalse("uniqueOptionsTest: makes sure that different questions aren't the same with the options switched",
                Objects.equals(question.getOptionA(), nextQuestion.getOptionB()) && Objects.equals(question.getOptionB(), nextQuestion.getOptionA()));
    }

    @Test
    public void checkForDuplicateQuestions(){
        questionServiceInterface.addQuestion(question);
        Question sameQ = new Question("that", "This");
        questionServiceInterface.addQuestion(sameQ);
        questionServiceInterface.getAllQuestions();
        assertTrue("checkForDuplicateQuestions: sameQ should have added since it is a completely" +
                        " different question",
                questionServiceInterface.getAllQuestions().size() > 1);
    }

    @Test
    public void checkForDuplicateOptionA(){
        questionServiceInterface.addQuestion(question);
        Question sameQ = new Question(optionA, "This");
        questionServiceInterface.addQuestion(sameQ);
        questionServiceInterface.getAllQuestions();
        assertFalse("checkForDuplicateOptionA: sameQ should not get added since it shares the same optionA" +
                        " with another question",
                questionServiceInterface.getAllQuestions().size() > 1);
    }

    @Test
    public void checkForDuplicateOptionB(){
        questionServiceInterface.addQuestion(question);
        Question sameQ = new Question("that", optionB);
        questionServiceInterface.addQuestion(sameQ);
        questionServiceInterface.getAllQuestions();
        assertFalse("checkForDuplicateOptionB: sameQ should not get added since it shares the same optionB" +
                        " with another question",
                questionServiceInterface.getAllQuestions().size() > 1);
    }

    @Test
    public void markQuestionAsSeenTest(){
        userServiceInterface.addUser(username, password);
        User newUser = userServiceInterface.userLookupUsername(username);
        assertTrue("markQuestionAsSeenTest: inputted question should be within the getSeenQuestions list",
                questionServiceInterface.markQuestionAsSeen(newUser, question));
    }


    @Test
    public void getSeenQuestionTest(){
        User user = new User(username, password);
        userServiceInterface.addUser(username, password);
        User newUser = userServiceInterface.userLookupUsername(username);
        questionServiceInterface.addQuestion(question);
        questionServiceInterface.markQuestionAsSeen(newUser, question);
        assertEquals("getSeenQuestionTest: the marked question and the inputted question should be equal",
                questionServiceInterface.getSeenQuestion(newUser, question, false), question);

    }

    @Test
    public void onlyOneSeenQuestionTest(){
        User user = new User(username, password);
        userServiceInterface.addUser(username, password);
        User newUser = userServiceInterface.userLookupUsername(username);
        userServiceInterface.resetSeenQuestions(newUser);
        questionServiceInterface.addQuestion(question);
        questionServiceInterface.markQuestionAsSeen(newUser, question);
        List<Question> newUserSeenQ = newUser.getSeenQuestions();
//        newUser.
        assertEquals("onlyOneSeenQuestionTest: there should only be one question within the seenQuestions" +
                " list for inputted user", newUserSeenQ.size(), 1);
    }

    @Test
    public void checkForNotSeenQuestionTest(){
        User user = new User(username, password);
        userServiceInterface.addUser(username, password);
        User newUser = userServiceInterface.userLookupUsername(username);
        questionServiceInterface.addQuestion(question);
        questionServiceInterface.markQuestionAsSeen(newUser, question);
        assertNotEquals("checkForNotSeenQuestionTest: the marked question and the inputted question should not be equal",
                questionServiceInterface.getSeenQuestion(newUser, question, false), nextQuestion);

    }

    @Test
    public void checkForCorrectSeenQuestionTest(){
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
    public void checkForNullGetSeenQuestionTest(){
        assertNull("checkForNullGetSeenQuestionTest: no questions were marked as seen so this should" +
                        " return null",
                questionServiceInterface.getSeenQuestion(user, null, true));
    }

//    @Test
//    public void

    @Test
    public void randomUnseenQuestionTest(){
        User user = new User(username, password);
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
    public void checkToSeeRandomQuestionReturnNull(){
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
        Question randomQ = questionServiceInterface.randomUnseenQuestion(newUser);
        assertTrue("checkRandomUnseenQuestionForMoreThanOneElement: " +
                        "unseenQ list should have more than one element " ,
                unseenQ.size() > 1);
    }

//    @Test
//    public void randomUnseenQuestionTest(){
//        questionServiceInterface.addQuestion(question);
//        assertNotEquals("randomUnseenQuestionTest: should succeed if the new question if question is different and random", );
//    }

//    @Test
//    public void getSeenQuestionTest(){
//        assertNotNull("");



//package edu.carroll.cs389.service;
//
//import edu.carroll.cs389.jpa.model.User;
//import edu.carroll.cs389.jpa.repo.UserRepository;
//import edu.carroll.cs389.jpa.model.Question;
//import edu.carroll.cs389.jpa.repo.QuestionRepository;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//
//import static org.springframework.test.util.AssertionErrors.*;
//
//
///**
// * Tests for QuestionServiceInterface.
// * Checks if methods within QuestionServiceInterface
// */
//
//@Transactional
//@SpringBootTest
//public class QuestionServiceTest {
//
//
//    private static final String optionA = "blue";
//    private static final String optionB = "red";
//    Question question = new Question(optionA, optionB);
//
//    Question nextQuestion = new Question("diamonds", "rhinestones");
//    private static final String username = "testuser";
//    private static final String password = "testpass";
//    User user = new User(username, password);
//
//
//    @Autowired
//    private QuestionServiceInterface questionServiceInterface;
//
//    @Autowired
//    private QuestionRepository questionRepository;
//
//    /**
//     * Checks to see if question was added.
//     */
//
//    @Test
//    public void addQuestionTest() {
//        assertTrue("addQuestionTest: should succeed if question was added", questionServiceInterface.addQuestion(question));
//
//        assertNotNull("addQuestionTest: will fail if optionA is null", question.getOptionA());
//
//        assertNotNull("addQuestionTest: will fail if optionB is null", question.getOptionB());
//    }


//    @Test
//    public void getSeenQuestionTest() {
//        questionServiceInterface.markQuestionAsSeen(user, question);
//        questionServiceInterface.markQuestionAsSeen(user, nextQuestion);
//
////        Question newSeenQuestion = questionServiceInterface.getSeenQuestion(user, question,false);
////        Question oldSeenQuestion = questionServiceInterface.getSeenQuestion(user, null, false);
//        assertNotNull("getSeenQuestionTest: will fail if seen question is not added to the seen questions list",
//                questionServiceInterface.getSeenQuestion(user, question, false));
//    }

//    @Test
//    public void questionsDontMatch(){
//        questionServiceInterface.addQuestion(question);
//        questionServiceInterface.addQuestion(nextQuestion);
//        List<Question> seenQuestions = user.getSeenQuestions();
//        int oldSeenQuestionIdx = seenQuestions.indexOf(question);
//        int newSeenQuestion
//        assertFalse("getSeenQuestionTest: will fail if previous seen question is the same as the next question",
//                seenQuestions.get(oldSeenQuestionIdx) == seenQuestions.get(oldSeenQuestionIdx + 1));
//    }

    @Test
    public void questionsDontMatch(){
        questionServiceInterface.addQuestion(question);
        questionServiceInterface.addQuestion(nextQuestion);
        List<Question> qi = questionServiceInterface.getAllQuestions();
        int oldQuestionIdx = qi.indexOf(question);
        int newQuestionIdx = qi.indexOf(nextQuestion);
        assertFalse("getSeenQuestionTest: will fail if previous seen question is the same as the next question",
                oldQuestionIdx == newQuestionIdx);
    }

    @Test
    public void questionIdLookupTest(){
        questionServiceInterface.addQuestion(question);
        questionServiceInterface.addQuestion(nextQuestion);
        Long questionId = question.getId();
        Long questionId2 = nextQuestion.getId();
//        assertNotNull("questionIdLookupTest: fails if question has no ID", questionServiceInterface.questionIdLookup(questionId));
        assertNotEquals("questionIdLookupTest: fails if question IDs are the same", questionId, questionId2);
    }


    @Test
    public void voteForOptionATest(){
        User user = new User(username, password);
        questionServiceInterface.addQuestion(question);
        questionServiceInterface.voteForOptionA(user, question);
        List<User> userVote = question.getVotesForOptionA();
        assertEquals("voteForOptionATest: this should return only one item ",
                userVote.size(), 1);
    }

    @Test
    public void voteForOptionBTest(){
        User user = new User(username, password);
        questionServiceInterface.addQuestion(question);
        questionServiceInterface.voteForOptionB(user, question);
        List<User> userVote = question.getVotesForOptionB();
        assertEquals("voteForOptionBTest: this should return only one item ",
                 userVote.size(), 1);
}

    @Test
    public void checkOptionAVoteIsEmpty(){
        User user = new User(username, password);
        questionServiceInterface.addQuestion(question);
        List<User> userVote = question.getVotesForOptionA();
        assertEquals("checkOptionAVoteIsEmpty: getVotesForOptionA list should be empty",
                userVote.size(), 0);
    }

    @Test
    public void checkOptionBVoteIsEmpty(){
        User user = new User(username, password);
        questionServiceInterface.addQuestion(question);
        List<User> userVote = question.getVotesForOptionB();
        assertEquals("checkOptionBVoteIsEmpty: getVotesForOptionB list should be empty",
                userVote.size(), 0);
    }

//    @Test
//    public void checkWhichOptionVoteIsAdded(){
//        User user = new User(username, password);
//        questionServiceInterface.addQuestion(question);
//        questionServiceInterface.voteForOptionA(user, question);
//        List<User> userVote = question.getVotesForOptionA();
//        assertEquals("voteForOptionATest: this should return only one item ",
//                userVote.size(), 1);
//    }


//    @Test
//    public void validateTheyDidNotVoteBothOptions(){
//        questionServiceInterface.
////        assertFalse("validateTheyDidNotVoteBothOptions: this check to make sure that both options were not voted for",
////                questionServiceInterface.voteForOptionA(user, question) &&
////                        questionServiceInterface.voteForOptionB(user, question));
//
//        assertNotEquals("validateTheyDidNotVoteBothOptions: this check to make sure that both options were not voted for",
//                question.getVotesForOptionA(), question.getVotesForOptionB());
    }

/////////////////////////////////////////////////////////////////////
//
//    @Test
//    public void checkForNullOptions(){
//        questionServiceInterface.addQuestion(question);
//        assertFalse("addQuestionTest: will fail if optionA is null and optionB is not null", question.getOptionA() == null &&
//                question.getOptionB() != null);
//        assertFalse("addQuestionTest: will fail if optionA is not null and optionB is null", question.getOptionA() != null &&
//                question.getOptionB() == null);
//
////        assertFalse("addQuestionTest: will fail if optionA is added and optionB is not added", questionServiceInterface
//    }
//
//    @Test
//    public void uniqueQuestionTest(){
//        assertTrue("uniqueQuestionTest: should succeed if question is unique", questionServiceInterface.uniqueQuestion(question));
//        assertFalse("uniqueQuestionTest: makes sure that different questions aren't the same with the options switched",
//                question.getOptionA() == nextQuestion.getOptionB() && question.getOptionB() == nextQuestion.getOptionA());
//    }
//
////    @Test
////    public void randomUnseenQuestionTest(){
////        questionServiceInterface.addQuestion(question);
////        assertNotEquals("randomUnseenQuestionTest: should succeed if the new question if question is different and random", );
////    }
//
////    @Test
////    public void getSeenQuestionTest(){
////        assertNotNull("");
////
////    }
//
//
//    @Test
//    public void getSeenQuestionTest() {
//        questionServiceInterface.markQuestionAsSeen(user, question);
//        questionServiceInterface.markQuestionAsSeen(user, nextQuestion);
//
////        Question newSeenQuestion = questionServiceInterface.getSeenQuestion(user, question,false);
////        Question oldSeenQuestion = questionServiceInterface.getSeenQuestion(user, null, false);
////        assertNotNull("getSeenQuestionTest: will fail if seen question is not added to the seen questions list",
////                questionServiceInterface.getSeenQuestion(user, question, false));
////    }
//
////    @Test
////    public void questionsDontMatch(){
////        List<Question> seenQuestions = user.getSeenQuestions();
////        int oldSeenQuestionIdx = seenQuestions.indexOf(question);
////        assertFalse("getSeenQuestionTest: will fail if previous seen question is the same as the next question",
////                seenQuestions.get(oldSeenQuestionIdx) == seenQuestions.get(oldSeenQuestionIdx + 1));
////    }
//
//    @Test
//    public void questionIdLookupTest(){
//        questionServiceInterface.addQuestion(question);
//        questionServiceInterface.addQuestion(nextQuestion);
//        Long questionId = question.getId();
//        Long questionId2 = nextQuestion.getId();
////        assertNotNull("questionIdLookupTest: fails if question has no ID", questionServiceInterface.questionIdLookup(questionId));
//        assertNotEquals("questionIdLookupTest: fails if question IDs are the same", questionId, questionId2);
//    }
//
////    @Test
////    public void validateTheyDidNotVoteBothOptions(){
////
//////        assertFalse("validateTheyDidNotVoteBothOptions: this check to make sure that both options were not voted for",
//////                questionServiceInterface.voteForOptionA(user, question) &&
//////                        questionServiceInterface.voteForOptionB(user, question));
////
////        assertNotEquals("validateTheyDidNotVoteBothOptions: this check to make sure that both options were not voted for",
////                question.getVotesForOptionA(), question.getVotesForOptionB());
////    }
//
//}
/////////////////////////////////////////////////////////////////////