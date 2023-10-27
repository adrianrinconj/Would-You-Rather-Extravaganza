package edu.carroll.cs389.service;

import edu.carroll.cs389.jpa.model.User;
import edu.carroll.cs389.jpa.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User("TestUser", "Password123");
        when(userRepository.findAll()).thenReturn(Collections.singletonList(testUser));
        when(userRepository.findByUsernameIgnoreCase("TestUser")).thenReturn(Collections.singletonList(testUser));
        when(userRepository.findByUsernameIgnoreCase("NonExistentUser")).thenReturn(List.of());
    }

    @Test
    void addUser_ShouldSaveUser_WhenUserIsUnique() {
        User newUser = new User("NewUser", "NewPassword123");
        boolean result = userService.addUser(newUser);
        assertThat(result).isTrue();
        verify(userRepository).save(newUser);
    }

    @Test
    void addUser_ShouldNotSaveUser_WhenUserIsNotUnique() {
        User newUser = new User("TestUser", "NewPassword123");
        boolean result = userService.addUser(newUser);
        assertThat(result).isFalse();
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void uniqueUser_ShouldReturnFalse_WhenUserExists() {
        boolean result = userService.uniqueUser("TestUser");
        assertThat(result).isFalse();
    }

    @Test
    void uniqueUser_ShouldReturnTrue_WhenUserDoesNotExist() {
        boolean result = userService.uniqueUser("NonExistentUser");
        assertThat(result).isTrue();
    }

    @Test
    void loginValidation_ShouldReturnUser_WhenCredentialsAreCorrect() {
        // Setup additional mocks or methods as needed to handle the encryptPassword method
        // ...

        // Test the login validation
        User result = userService.loginValidation("TestUser", "CorrectPassword");
        assertThat(result).isEqualTo(testUser);
    }

    @Test
    void loginValidation_ShouldReturnNull_WhenCredentialsAreIncorrect() {
        User result = userService.loginValidation("TestUser", "IncorrectPassword");
        assertThat(result).isNull();
    }

    @Test
    void userLookupID_ShouldReturnUser_WhenUserExists() {
        when(userRepository.findById(Math.toIntExact(testUser.getId()))).thenReturn(Optional.of(testUser));
        User result = userService.userLookupID(testUser.getId());
        assertThat(result).isEqualTo(testUser);
    }

    @Test
    void userLookupID_ShouldReturnNull_WhenUserDoesNotExist() {
        User result = userService.userLookupID(999L);
        assertThat(result = userService.userLookupID(999L));
        assertThat(result).isNull();
    }

    @Test
    void userLookupUsername_ShouldReturnUser_WhenUserExists() {
        User result = userService.userLookupUsername("TestUser");
        assertThat(result).isEqualTo(testUser);
    }

    @Test
    void userLookupUsername_ShouldReturnNull_WhenUserDoesNotExist() {
        User result = userService.userLookupUsername("NonExistentUser");
        assertThat(result).isNull();
    }

    @Test
    void resetSeenQuestions_ShouldClearSeenQuestions() {
        // Add a seen question to the test user
        // ...

        userService.resetSeenQuestions(testUser);
        assertThat(testUser.getSeenQuestions()).isEmpty();
    }
}
