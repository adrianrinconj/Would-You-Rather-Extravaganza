//package edu.carroll.cs389.api.repository;//package edu.carroll.cs389.api.repository;
////
////
////import edu.carroll.cs389.jpa.model.Question;
////import edu.carroll.cs389.jpa.repo.QuestionRepository;
////import org.assertj.core.api.Assertions;
////import org.junit.jupiter.api.Test;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
////import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
////import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
////
////@DataJpaTest
////@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
////
////public class QuestionTests {
////    @Autowired
////    private QuestionRepository questionRepository;
////
////    @Test
////    public void QuestionRepository_SaveAll_ReturnSavedQuestion(){
////        //Arrange
////        Question question = Question.builder().optionA("blue").optionB("green").build();
////
////
////        //Act
////        Question savedQuestion = questionRepository.save(question);
////
////        //Assert
////        Assertions.assertThat(savedQuestion).isNotNull();
//////        Assertions.assertThat(savedQuestion.getId()).isGreaterThan(0);
////
////    }
////
////}
//
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