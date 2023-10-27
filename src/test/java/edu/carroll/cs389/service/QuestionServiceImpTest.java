package edu.carroll.cs389.service;

import edu.carroll.cs389.jpa.model.Question;
import edu.carroll.cs389.jpa.model.User;
import edu.carroll.cs389.jpa.repo.QuestionRepository;
import edu.carroll.cs389.jpa.repo.UserRepository;
import edu.carroll.cs389.service.UserServiceInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class QuestionServiceImplTest {

    @Mock
    private QuestionRepository questionRepo;

    @Mock
    private UserServiceInterface userService;

    @Mock
    private UserRepository userRepo;

    @InjectMocks
    private QuestionServiceImpl questionService;

    private User testUser;
    private Question testQuestion1;
    private Question testQuestion2;

    @BeforeEach
    void setUp() {
        testUser = new User("TestUser", "Password");
        testQuestion1 = new Question("Option A", "Option B");
        testQuestion2 = new Question("Option C", "Option D");

        Mockito.when(questionRepo.findAll()).thenReturn(Arrays.asList(testQuestion1, testQuestion2));
        Mockito.when(questionRepo.save(any(Question.class))).thenAnswer(i -> i.getArguments()[0]);
        Mockito.when(userRepo.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);
    }

    @Test
    void getAllQuestions_ShouldReturnAllQuestions() {
        List<Question> questions = (List<Question>) questionService.getAllQuestions();
        assertThat(questions).containsExactlyInAnyOrder(testQuestion1, testQuestion2);
    }

    @Test
    void addQuestion_ShouldSaveQuestion_WhenQuestionIsUnique() {
        Question newQuestion = new Question("Option X", "Option Y");
        questionService.addQuestion(newQuestion);

        Mockito.verify(questionRepo).save(newQuestion);
    }

    @Test
    void addQuestion_ShouldNotSaveQuestion_WhenQuestionIsNotUnique() {
        questionService.addQuestion(testQuestion1);

        Mockito.verify(questionRepo, Mockito.never()).save(any(Question.class));
    }

    @Test
    void randomUnseenQuestion_ShouldReturnUnseenQuestion() {
        // Set up user to have seen all questions except one
        testUser.getSeenQuestions().add(testQuestion1);
        Mockito.when(userService.userLookupID(testUser.getId())).thenReturn(testUser);

        Question unseenQuestion = questionService.randomUnseenQuestion(testUser);
        assertThat(unseenQuestion).isEqualTo(testQuestion2);
    }

    @Test
    void randomUnseenQuestion_ShouldReturnNull_WhenAllQuestionsSeen() {
        // Set up user to have seen all questions
        testUser.getSeenQuestions().addAll(Arrays.asList(testQuestion1, testQuestion2));
        Mockito.when(userService.userLookupID(testUser.getId())).thenReturn(testUser);

        Question unseenQuestion = questionService.randomUnseenQuestion(testUser);
        assertThat(unseenQuestion).isNull();
    }

    @Test
    void questionIdLookup_ShouldReturnQuestion_WhenIdMatches() {
        Long id = 1L;
        testQuestion1.setId(id);
        Mockito.when(questionRepo.findById(Math.toIntExact(id))).thenReturn(Optional.of(testQuestion1));

        Question foundQuestion = questionService.questionIdLookup(id);
        assertThat(foundQuestion).isEqualTo(testQuestion1);
    }

    @Test
    void questionIdLookup_ShouldReturnNull_WhenIdDoesNotMatch() {
        Long id = 999L;
        Question foundQuestion = questionService.questionIdLookup(id);
        assertThat(foundQuestion).isNull();
    }

    @Test
    void voteForOptionA_ShouldAddUserToVotesForOptionA() {
        questionService.voteForOptionA(testUser, testQuestion1);

        Mockito.verify(questionRepo).save(testQuestion1);
        assertThat(testQuestion1.getVotesForOptionA()).contains(testUser);
    }

    @Test
    void voteForOptionB_ShouldAddUserToVotesForOptionB() {
        questionService.voteForOptionB(testUser, testQuestion1);

        Mockito.verify(questionRepo).save(testQuestion1);
        assertThat(testQuestion1.getVotesForOptionB()).contains(testUser);
    }

    @Test
    void markQuestionAsSeen_ShouldAddQuestionToUserSeenQuestions() {
        questionService.markQuestionAsSeen(testUser, testQuestion1);

        Mockito.verify(userRepo).save(testUser);
        assertThat(testUser.getSeenQuestions()).contains(testQuestion1);
    }

    @Test
    void getSeenQuestion_ShouldReturnSeenQuestion() {
        testUser.getSeenQuestions().add(testQuestion1);
        Mockito.when(userService.userLookupID(testUser.getId())).thenReturn(testUser);

        Question seenQuestion = questionService.getSeenQuestion(testUser, null, null);
        assertThat(seenQuestion).isEqualTo(testQuestion1);
    }

    // Other test methods as necessary...
}
