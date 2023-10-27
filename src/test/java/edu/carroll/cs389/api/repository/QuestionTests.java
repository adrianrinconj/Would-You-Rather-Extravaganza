//package edu.carroll.cs389.api.repository;
//
//
//import edu.carroll.cs389.jpa.model.Question;
//import edu.carroll.cs389.jpa.repo.QuestionRepository;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
//
//public class QuestionTests {
//    @Autowired
//    private QuestionRepository questionRepository;
//
//    @Test
//    public void QuestionRepository_SaveAll_ReturnSavedQuestion(){
//        //Arrange
//        Question question = Question.builder().optionA("blue").optionB("green").build();
//
//
//        //Act
//        Question savedQuestion = questionRepository.save(question);
//
//        //Assert
//        Assertions.assertThat(savedQuestion).isNotNull();
////        Assertions.assertThat(savedQuestion.getId()).isGreaterThan(0);
//
//    }
//
//}
