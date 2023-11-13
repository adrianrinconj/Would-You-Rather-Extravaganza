package edu.carroll.cs389.service;

import java.util.ArrayList;
import java.util.List;

import edu.carroll.cs389.jpa.model.Question;
import edu.carroll.cs389.jpa.model.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.util.AssertionErrors.*;

@Transactional
@SpringBootTest
public class UserServiceTest{
    private static final String username = "testuser";
    private static final String password = "testpass";

    private static final User userModel = new User();

    private static final String optionA = "blue";
    private static final String optionB = "red";
    Question question = new Question(optionA, optionB);

    @Autowired
    private UserServiceInterface userServiceInterface;

    @Autowired
    private QuestionServiceInterface questionServiceInterface;

    /**
     * Test certifying that adding a user is successful
     */
    @Test
    public void addUserTest() {
        assertTrue("addUserTest: should succeed in having a user added", userServiceInterface.addUser(username, password));
    }

    /**
     * Test certifying that username lookup returns a user when used with a username that is within the database
     */
    @Test
    public void checkAddUsernameNotNullTest() {
        User user = new User(username, password);
        userServiceInterface.addUser(user.getUsername(), user.getPassword());
        assertNotNull("checkAddUsernameNotNullTest: the addUser() method should not return a null username",
                userServiceInterface.userLookupUsername(user.getUsername()));
    }

    /**
     * Test certifying that passwords are stored
     */
    @Test
    public void addedUsersHavePasswords(){
        userServiceInterface.addUser(username, password);
        User user = userServiceInterface.userLookupUsername(username);
        assertNotNull("addedUsersHavePasswords: every user should have a password",
                user.getPassword());
    }

    /**
     * Test certifying that users with duplicate usernames cannot be added
     */
    @Test
    public void dontAddDuplicateUsernames(){
        User user = new User(username, password);
        User user2 = new User(username, password);
        userServiceInterface.addUser(user.getUsername(), user.getPassword());
        userServiceInterface.addUser(user2.getUsername(), user2.getPassword());
        assertFalse("dontAddDuplicateUsernames: two users should not be able to have the same username",
                userServiceInterface.addUser(user2.getUsername(), user2.getPassword()));
    }

    /**
     * Test certifying that users added to the database have passwords
     */
    @Test
    public void checkAddPasswordNotNullTest() {
        User user = new User(username, password);
        userServiceInterface.addUser(user.getUsername(), user.getPassword());
        assertNotNull("checkAddPasswordNotNullTest: the addUser() method should not return a null password", user.getPassword());
    }

    /**
     * Test certifying that neither the username nor the password of a user can be null
     */
    @Test
    public void checkNullFieldsUser() {
        User user = new User(username, password);
        userServiceInterface.addUser(user.getUsername(), user.getPassword());
        assertNotNull("checkNullFieldsUser: the addUser() method should not return a null username",
                userServiceInterface.userLookupUsername(user.getUsername()));
        assertNotNull("checkNullFieldsUser: the addUser() method should not return a null password",
                user.getPassword());
    }

    /**
     * Test certifying that a username tests as unique if it is not already registered
     */
    @Test
    public void uniqueUserTest(){
        assertTrue("uniqueUserTest: new user should not be added yet, or username is already taken",
                userServiceInterface.uniqueUser(username));

    }

    /**
     * Test certifying that a user is not added if adding two users with the same username is impossible
     */
    @Test
    public void checkDuplicateUniqueUsernames(){
        User user = new User(username, password);
        User user2 = new User(username, password);
        userServiceInterface.addUser(user.getUsername(), user.getPassword());
        userServiceInterface.addUser(user2.getUsername(), user2.getPassword());
        assertTrue("checkDuplicateUsernames: two different users with the same username were added to the repository",
                userServiceInterface.uniqueUser(String.valueOf(userServiceInterface.userLookupUsername(user.getUsername()))));

    }

    /**
     * Test certifying that username lookup correctly finds user by name
     */
    @Test
    public void userLookupUsernameTest() {
        userServiceInterface.addUser(username, password);
        User user = userServiceInterface.userLookupUsername(username);
        assertNotNull("userLookupUsernameTest: is null but should return a username",
                userServiceInterface.userLookupUsername(user.getUsername()));
    }

    /**
     * Test certifying that no user is returned if the username is not registered
     */
    @Test
    public void lookupNonexistentUsernameTest() {
        assertNull("lookupNonexistentUsernameTest: is null but should return a username",
                userServiceInterface.userLookupUsername("sdalkfjads;lf"));
    }

    /**
     * Test certifying that no user is returned on username lookup if the user should not have been added (bad registration)
     */
    @Test
    public void nonAddedUserReturnsNullUsername(){
        User user = new User(username, password);
        assertNull("nonAddedUserReturnsNullUsername: user was not added so should return null ",
                userServiceInterface.userLookupUsername(user.getUsername()));
    }

    /**
     * Test certifying that userLookupID behaves properly for an existing user and its ID
     */
    @Test
    public void userLookupIDTest(){
        User user = new User(username, password);
        userServiceInterface.addUser(user.getUsername(), user.getPassword());
        String userUsername = user.getUsername();
        User newUser = userServiceInterface.userLookupUsername(userUsername);
        Long userId = newUser.getId();
        assertNotNull("userLookupIDTest: user ID should not be null",
                userServiceInterface.userLookupID(userId));
    }

    /**
     * Test certifying that setting a users ID works properly and can be retrieved by new ID
     */
    @Test
    public void userSetIdTest(){
        User user = new User(username, password);
        userServiceInterface.addUser(user.getUsername(), user.getPassword());
        User newUser = userServiceInterface.userLookupUsername(user.getUsername());
        newUser.setId(007L);
        Long userId = newUser.getId();
        assertEquals("userSetIdTest: user's long Id should have been set to '007L' ",
                userId, 007L);
    }

    /**
     * Test certifying that user's IDs are always unique
     */
    @Test
    public void uniqueUserId(){
        User user = new User(username, password);
        User user2 = new User("Adrian", "dog");
        userServiceInterface.addUser(user.getUsername(), user.getPassword());
        userServiceInterface.addUser(user2.getUsername(), user2.getPassword());
        User newUser = userServiceInterface.userLookupUsername(user.getUsername());
        User newUser2 = userServiceInterface.userLookupUsername(user2.getUsername());
        Long userId = newUser.getId();
        Long userId2 = newUser2.getId();
        assertNotEquals("uniqueUserId: users should not have the same IDs as one another",
                userId, userId2);
    }

    /**
     * Test certifying that user ID lookup fails when no such ID exists
     */
    @Test
    public void userIdLookupReturnNullTest(){
        assertNull("userIdLookupReturnNullTest: put in a random Long, there should be no users"
                , userServiceInterface.userLookupID(481L));
    }

    /**
     * Test certifying that a user that is created but not saved cannot be searched by ID
     */
    @Test
    public void nonAddedUserEqualsNull(){
        User user = new User(username, password);
        Long userId = user.getId();
        assertNull("nonAddedUserEqualsNull: user was not added so this should return null",
                userServiceInterface.userLookupID(userId));

    }

    /**
     * Test certifying that users can log in with correct credentials
     */
    @Test
    public void loginValidationTest(){
        User user = new User(username, password);
        userServiceInterface.addUser(user.getUsername(), user.getPassword());
        assertNotNull("loginValidationTest: username and password should not be null",
                userServiceInterface.loginValidation(user.getUsername(), user.getPassword()));
    }

    /**
     * Test certifying that users cannot log in without a username
     */
    @Test
    public void loginValidationUsernameNullTest(){
        User user = new User(username, password);
        userServiceInterface.addUser(user.getUsername(), user.getPassword());
        assertNull("loginValidationUsernameNullTest: username should be null",
                userServiceInterface.loginValidation(null, user.getPassword()));
    }

    /**
     * Test certifying that resetting a users seen questions is successful
     */
    @Test
    public void resetSeenQuestionsTest(){
        User user = new User(username, password);
        userServiceInterface.addUser(user.getUsername(), user.getPassword());
        User newUser = userServiceInterface.userLookupUsername(username);
        Question question2 = new Question("yes", "no");
        Question question3 = new Question("now", "never");
        questionServiceInterface.markQuestionAsSeen(newUser, question);
        questionServiceInterface.markQuestionAsSeen(newUser, question2);
        questionServiceInterface.markQuestionAsSeen(newUser, question3);
        List<Question> seenQuestions = new ArrayList<Question>();
        seenQuestions.add(question);
        seenQuestions.add(question2);
        seenQuestions.add(question3);
        newUser.setSeenQuestions(seenQuestions);
        userServiceInterface.resetSeenQuestions(newUser);
        assertNull("resetSeenQuestionsTest: this should return the getSeenQuestions list as empty",
                questionServiceInterface.getSeenQuestion(newUser, question, false));
    }

    /**
     * Test certifying that getting a seen question after reset returns null
     */
    @Test
    public void resetSeenQuestionsEmptyTest(){
        User user = new User(username, password);
        userServiceInterface.addUser(user.getUsername(), user.getPassword());
        User newUser = userServiceInterface.userLookupUsername(username);
        userServiceInterface.resetSeenQuestions(newUser);
        assertNull("resetSeenQuestionsEmptyTest: this should return the getSeenQuestions list as empty and not crash due to" +
                        " it previously being empty",
                questionServiceInterface.getSeenQuestion(newUser, question, false));
    }
}