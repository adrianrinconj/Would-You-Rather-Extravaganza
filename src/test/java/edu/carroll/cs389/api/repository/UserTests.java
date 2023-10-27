//package edu.carroll.cs389.api.repository;
//import edu.carroll.cs389.jpa.model.User;
//import edu.carroll.cs389.jpa.repo.UserRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//
//import java.util.Collections;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DataJpaTest
//public class UserTests {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private TestEntityManager testEntityManager;
//
//    @Test
//    public void testFindByUsernameIgnoreCase() {
//        // Given
//        User user1 = new User("TestUser", "Password1");
//        User user2 = new User("testuser", "Password2");
//        testEntityManager.persist(user1);
//        testEntityManager.persist(user2);
//
//        // When
//        List<User> foundUsers = userRepository.findByUsernameIgnoreCase("testuser");
//
//        // Then
//        assertThat(Collections.singletonList(foundUsers)).isNotNull();
//        assertThat(Collections.singletonList(foundUsers)).hasSize(2);
//    }
//
//    @Test
//    public void testFindByUsernameIgnoreCaseNonExistent() {
//        // When
//        List<User> foundUsers = userRepository.findByUsernameIgnoreCase("nonexistentuser");
//
//        // Then
//        assertThat(Collections.singletonList(foundUsers)).isEmpty();
//    }
//}
