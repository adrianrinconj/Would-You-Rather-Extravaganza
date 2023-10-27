package edu.carroll.cs389.api.repository;//package edu.carroll.cs389.api.repository;//package edu.carroll.cs389.api.repository;
//////
//////
//////import edu.carroll.cs389.jpa.model.Question;
//////import edu.carroll.cs389.jpa.repo.QuestionRepository;
//////import org.assertj.core.api.Assertions;
//////import org.junit.jupiter.api.Test;
//////import org.springframework.beans.factory.annotation.Autowired;
//////import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
//////import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//////import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//////
//////@DataJpaTest
//////@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
//////
//////public class QuestionTests {
//////    @Autowired
//////    private QuestionRepository questionRepository;
//////
//////    @Test
//////    public void QuestionRepository_SaveAll_ReturnSavedQuestion(){
//////        //Arrange
//////        Question question = Question.builder().optionA("blue").optionB("green").build();
//////
//////
//////        //Act
//////        Question savedQuestion = questionRepository.save(question);
//////
//////        //Assert
//////        Assertions.assertThat(savedQuestion).isNotNull();
////////        Assertions.assertThat(savedQuestion.getId()).isGreaterThan(0);
//////
//////    }
//////
//////}
////
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import edu.carroll.cs389.jpa.model.Question;
//import edu.carroll.cs389.jpa.repo.QuestionRepository;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DataJpaTest
//public class QuestionTests {
//
//    @Autowired
//    private QuestionRepository questionRepository;
//
//    @Test
//    public void testSaveAndFind() {
//        // Create a sample Question entity
//        Question question = new Question("Option A", "Option B");
//
//        // Save the entity to the repository
//        questionRepository.save(question);
//
//        // Retrieve the entity from the repository
//        Question foundQuestion = questionRepository.findById(question.getId()).orElse(null);
//
//        // Check that the retrieved entity matches the original
//        assertThat(foundQuestion).isNotNull();
//        assertThat(foundQuestion.getOptionA()).isEqualTo("Option A");
//        assertThat(foundQuestion.getOptionB()).isEqualTo("Option B");
//    }
//}

//import edu.carroll.cs389.jpa.model.Question;
//import edu.carroll.cs389.jpa.repo.QuestionRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DataJpaTest
//public class QuestionRepositoryTest {
//
//    @Autowired
//    private TestEntityManager entityManager;
//
//    @Autowired
//    private QuestionRepository questionRepository;
//
//    @BeforeEach
//    public void setUp() {
//        // You can initialize test data here using the TestEntityManager
//        Question question1 = new Question("Option A1", "Option B1");
//        Question question2 = new Question("Option A2", "Option B2");
//
//        entityManager.persist(question1);
//        entityManager.persist(question2);
//    }
//
//    @Test
//    public void testFindAll() {
//        List<Question> questions = questionRepository.findAll();
//        assertThat(questions).hasSize(2);
//    }
//
//    @Test
//    public void testFindById() {
//        Question question = questionRepository.findById(1).orElse(null);
//        assertThat(question).isNotNull();
//        assertThat(question.getOptionA()).isEqualTo("Option A1");
//        assertThat(question.getOptionB()).isEqualTo("Option B1");
//    }
//
//    // Add more test cases as needed
//
//    @Test
//    public void testFindByQuestionIgnoreCase() {
//        List<Question> questions = questionRepository.findByQuestionIgnoreCase("Option A1");
//        assertThat(questions).hasSize(1);
//        assertThat(questions.get(0).getOptionA()).isEqualTo("Option A1");
//    }
//}
