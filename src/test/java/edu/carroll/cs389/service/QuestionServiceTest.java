package edu.carroll.cs389.service;

import edu.carroll.cs389.jpa.model.User;
import edu.carroll.cs389.jpa.repo.UserRepository;
import edu.carroll.cs389.jpa.model.Question;
import edu.carroll.cs389.jpa.repo.QuestionRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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
    private QuestionRepository questionRepository;

    /**
     * Checks to see if question was added.
     */

    @Test
    public void addQuestionTest() {
        assertTrue("addQuestionTest: should succeed if question was added", questionServiceInterface.addQuestion(question));

        assertNotNull("addQuestionTest: will fail if optionA is null", question.getOptionA());

        assertNotNull("addQuestionTest: will fail if optionB is null", question.getOptionB());
    }

    @Test
    public void checkForNullOptions(){
        questionServiceInterface.addQuestion(question);
        assertFalse("addQuestionTest: will fail if optionA is null and optionB is not null", question.getOptionA() == null &&
                question.getOptionB() != null);
        assertFalse("addQuestionTest: will fail if optionA is not null and optionB is null", question.getOptionA() != null &&
                question.getOptionB() == null);

//        assertFalse("addQuestionTest: will fail if optionA is added and optionB is not added", questionServiceInterface
    }

    @Test
    public void uniqueQuestionTest(){
        assertTrue("uniqueQuestionTest: should succeed if question is unique", questionServiceInterface.uniqueQuestion(question));
        assertFalse("uniqueQuestionTest: makes sure that different questions aren't the same with the options switched",
                question.getOptionA() == nextQuestion.getOptionB() && question.getOptionB() == nextQuestion.getOptionA());
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

    @Test
    public void questionsDontMatch(){
        List<Question> seenQuestions = user.getSeenQuestions();
        int oldSeenQuestionIdx = seenQuestions.indexOf(question);
        assertFalse("getSeenQuestionTest: will fail if previous seen question is the same as the next question",
                seenQuestions.get(oldSeenQuestionIdx) == seenQuestions.get(oldSeenQuestionIdx + 1));
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
    public void validateTheyDidNotVoteBothOptions(){

//        assertFalse("validateTheyDidNotVoteBothOptions: this check to make sure that both options were not voted for",
//                questionServiceInterface.voteForOptionA(user, question) &&
//                        questionServiceInterface.voteForOptionB(user, question));

        assertNotEquals("validateTheyDidNotVoteBothOptions: this check to make sure that both options were not voted for",
                question.getVotesForOptionA(), question.getVotesForOptionB());
    }

}
