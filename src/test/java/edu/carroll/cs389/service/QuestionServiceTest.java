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


    /**
     * Checks to see if question was added.
     */

    @Test
    public void addQuestionTest() {
        Question question = new Question();
        question.setOptionA(optionA);
        question.setOptionB(optionB);
        assertTrue("addQuestionTest: should succeed if question was added", questionServiceInterface.addQuestion(question));

        List<Question> qi = questionServiceInterface.getAllQuestions();
        assertEquals("addQuestionTest: there should only be one question", qi.size(), 1);
        Question addedQuestion = qi.get(0);
        assertEquals("addQuestionTest: optionA should match the question that was just added", question.getOptionA(), addedQuestion.getOptionA());
        assertEquals("addQuestionTest: optionB should match the question that was just added", question.getOptionB(), addedQuestion.getOptionB());

        //        qi.
//        assertNotNull("addQuestionTest: will fail if optionA is null", questionServiceInterface.);
//        assertNotNull("addQuestionTest: will fail if optionB is null", questionServiceInterface.getOptionB());
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
    public void uniqueQuestionTest(){
        assertTrue("uniqueQuestionTest: should succeed if question is unique", questionServiceInterface.uniqueQuestion(question));
        assertFalse("uniqueQuestionTest: makes sure that different questions aren't the same with the options switched",
                Objects.equals(question.getOptionA(), nextQuestion.getOptionB()) && Objects.equals(question.getOptionB(), nextQuestion.getOptionA()));
    }

//    @Test
//    public void randomUnseenQuestionTest(){
//        questionServiceInterface.addQuestion(question);
//        assertNotEquals("randomUnseenQuestionTest: should succeed if the new question if question is different and random", );
//    }

//    @Test
//    public void getSeenQuestionTest(){
//        assertNotNull("");
//
//    }


    @Test
    public void getSeenQuestionTest() {
        questionServiceInterface.markQuestionAsSeen(user, question);
        questionServiceInterface.markQuestionAsSeen(user, nextQuestion);

//        Question newSeenQuestion = questionServiceInterface.getSeenQuestion(user, question,false);
//        Question oldSeenQuestion = questionServiceInterface.getSeenQuestion(user, null, false);
        assertNotNull("getSeenQuestionTest: will fail if seen question is not added to the seen questions list",
                questionServiceInterface.getSeenQuestion(user, question, false));
    }

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


