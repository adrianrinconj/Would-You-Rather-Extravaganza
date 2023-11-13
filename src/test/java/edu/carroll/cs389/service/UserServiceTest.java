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

    @Test
    public void addUserTest() {
        assertTrue("addUserTest: should succeed in having a user added", userServiceInterface.addUser(username, password));
    }

    @Test
    public void checkAddUsernameNotNullTest() {
        User user = new User(username, password);
        userServiceInterface.addUser(user.getUsername(), user.getPassword());
        assertNotNull("checkAddUsernameNotNullTest: the addUser() method should not return a null username",
                userServiceInterface.userLookupUsername(user.getUsername()));
    }

    @Test
    public void addedUsersHavePasswords(){
        userServiceInterface.addUser(username, password);
        User user = userServiceInterface.userLookupUsername(username);
        assertNotNull("addedUsersHavePasswords: every user should have a password",
                user.getPassword());
    }

    @Test
    public void dontAddDuplicateUsernames(){
        User user = new User(username, password);
        User user2 = new User(username, password);
        userServiceInterface.addUser(user.getUsername(), user.getPassword());
        userServiceInterface.addUser(user2.getUsername(), user2.getPassword());
        User user2ish = userServiceInterface.userLookupUsername(user2.getUsername());
        assertFalse("dontAddDuplicateUsernames: two users should not be able to have the same username",
                userServiceInterface.addUser(user2.getUsername(), user2.getPassword()));
    }

    @Test
    public void checkAddPasswordNotNullTest() {
        User user = new User(username, password);
        userServiceInterface.addUser(user.getUsername(), user.getPassword());
        assertNotNull("checkAddPasswordNotNullTest: the addUser() method should not return a null password", user.getPassword());
    }

    @Test
    public void checkNullFieldsUser() {
        User user = new User(username, password);
        userServiceInterface.addUser(user.getUsername(), user.getPassword());
        assertNotNull("checkNullFieldsUser: the addUser() method should not return a null username",
                userServiceInterface.userLookupUsername(user.getUsername()));
        assertNotNull("checkNullFieldsUser: the addUser() method should not return a null password",
                user.getPassword());
    }

    @Test
    public void uniqueUserTest(){
        assertTrue("uniqueUserTest: new user should not be added yet, or username is already taken",
                userServiceInterface.uniqueUser(username));

    }

    @Test
    public void checkDuplicateUniqueUsernames(){
        User user = new User(username, password);
        User user2 = new User(username, password);
        userServiceInterface.addUser(user.getUsername(), user.getPassword());
        userServiceInterface.addUser(user2.getUsername(), user2.getPassword());
        assertTrue("checkDuplicateUsernames: two different users with the same username were added to the repository",
                userServiceInterface.uniqueUser(String.valueOf(userServiceInterface.userLookupUsername(user.getUsername()))));

    }

    @Test
    public void userLookupUsernameTest() {
        userServiceInterface.addUser(username, password);
        User user = userServiceInterface.userLookupUsername(username);
        assertNotNull("userLookupUsernameTest: is null but should return a username",
                userServiceInterface.userLookupUsername(user.getUsername()));
    }

    @Test
    public void lookupNonexistentUsernameTest() {
        assertNull("lookupNonexistentUsernameTest: is null but should return a username",
                userServiceInterface.userLookupUsername("sdalkfjads;lf"));
    }

    @Test
    public void nonAddedUserReturnsNullUsername(){
        User user = new User(username, password);
        assertNull("nonAddedUserReturnsNullUsername: user was not added so should return null ",
                userServiceInterface.userLookupUsername(user.getUsername()));
    }

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

    @Test
    public void userIdLookupReturnNullTest(){
        assertNull("userIdLookupReturnNullTest: put in a random Long, there should be no users"
                , userServiceInterface.userLookupID(481L));
    }

    @Test
    public void nonAddedUserEqualsNull(){
        User user = new User(username, password);
        Long userId = user.getId();
        assertNull("nonAddedUserEqualsNull: user was not added so this should return null",
                userServiceInterface.userLookupID(userId));

    }
    @Test
    public void loginValidationTest(){
        User user = new User(username, password);
        userServiceInterface.addUser(user.getUsername(), user.getPassword());
        assertNotNull("loginValidationTest: username and password should not be null",
                userServiceInterface.loginValidation(user.getUsername(), user.getPassword()));
    }

    @Test
    public void loginValidationUsernameNullTest(){
        User user = new User(username, password);
        userServiceInterface.addUser(user.getUsername(), user.getPassword());
        assertNull("loginValidationUsernameNullTest: username should be null",
                userServiceInterface.loginValidation(null, user.getPassword()));
    }

//    @Test
//    public void loginValidationPasswordNullTest(){
//        User user = new User(username, password);
//        userServiceInterface.addUser(user.getUsername(), user.getPassword());
//        assertNull("loginValidationPasswordNullTest: password should be null",
//                userServiceInterface.loginValidation(user.getUsername(), null));
//    }

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




//    @Test
//    public void userLookupIDTest() {
//        User user = new User(username, password);
//        userServiceInterface.addUser(user);
//        Long userIDCheck = user.getId();
//    }
//package edu.carroll.cs389.service;
//
//import java.util.List;
//
//import edu.carroll.cs389.jpa.model.User;
//import edu.carroll.cs389.jpa.repo.UserRepository;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.springframework.test.util.AssertionErrors.*;
//
//@Transactional
//@SpringBootTest
//public class UserServiceTest{
//    private static final String username = "testuser";
//    private static final String password = "testpass";
//    @Autowired
//    private UserServiceInterface userServiceInterface;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Test
//    public void addUserTest(){
//       User user = new User(username, password);
//       assertTrue("addUserTest: should succeed in having a user added", userServiceInterface.addUser(user));
//       User bob = userServiceInterface.userLookupUsername(username);
//       assertNotNull("addUserTest: a new user is not within the database", bob);
//       assertEquals("addUserTest: newly added user name does not match fetched user", username, bob.getUsername());
////       assertTrue("addUserTest: user should have an ID added", )
//
//        //I have a question about this for Nate
//       assertNotNull("addUserTest: a password was not added", user.getPassword());
//
//    }
//
//    @Test
//    public void uniqueUserTest(){
//        User user = new User(username, password);
////        String newUser = userServiceInterface.userLookupUsername(username);
////        assertFalse("uniqueUserTest: new user should not have same username as another user", userServiceInterface.uniqueUser((user.getUsername())));
//        assertTrue("uniqueUserTest: new user should not have same username as another user", userServiceInterface.uniqueUser((username)));
//
//    }
//
//    @Test
//    public void userLookupIDTest(){
//        User user = new User(username, password);
//        userServiceInterface.addUser(user);
//        Long userIDCheck = user.getId();
////        assertNotNull("userLookupIDTest: new user ID was not added", userServiceInterface.userLookupID(userIDCheck));
//        assertNotNull("userLookupIDTest: new user ID was not added", userServiceInterface.userLookupID(userIDCheck));
//
//
////        assertFalse("userLookupIDTest: new user ID shouldn't match existing user ID", userIDCheck == userRepository.findAll() );
////        assertTrue("userLookupID")
//    }
//
//    @Test
//    public void loginValidationTest(){
//        User user = new User(username, password);
////        String usernameCheck = user.getUsername();
//
//        //ask nate about how I would implement this since password returns byte[] datatype now
////        String passwordCheck = user.getPassword();
////        assertNotNull("loginValidationTest: fails if username or password is null", userServiceInterface.loginValidation(user.getUsername(), user.getPassword()));
//
//        assertNotNull("loginValidationTest: fails if username for new user is null", user.getUsername());
//        assertNotNull("loginValidationTest: fails if password for new user is null", user.getPassword());
//
////        @Test
////        public void validateUserInvalidUserValidPasswordTest() {
////            assertFalse("validateUserInvalidUserValidPasswordTest: should fail using an invalid user, valid pass", loginService.validateUser(username + "not", password));
////        }
////
////        @Test
////        public void validateUserInvalidUserInvalidPasswordTest() {
////            assertFalse("validateUserInvalidUserInvalidPasswordTest: should fail using an invalid user, valid pass", loginService.validateUser(username + "not", password + "extra"));
//        }
//
//        @Test
//        public void validateUserInvalidUserInvalidPasswordTest() {
//            assertFalse("validateUserInvalidUserInvalidPasswordTest: should fail using an invalid user, valid pass", loginService.validateUser(username + "not", password + "extra"));
//        }


//        @Test
//        public void userLookupUsernameTest(){
//            User user = new User(username, password);
//            assertNotNull("userLookupUsernameTest: fails if user is not found", userServiceInterface.userLookupUsername(user.getUsername()));
//
//
//        }




//
////        @Test
////        public void userLookupUsernameTest(){
////            User user = new User(username, password);
////            assertNotNull("userLookupUsernameTest: fails if user is not found", userServiceInterface.userLookupUsername(user.getUsername()));
////
////
////        }
//    }
//
//
//



//    @Test
//    public void loginValidationTest(){
//        User user = new User(username, password);
////        String usernameCheck = user.getUsername();
//
//        //ask nate about how I would implement this since password returns byte[] datatype now
////        String passwordCheck = user.getPassword();
////        assertNotNull("loginValidationTest: fails if username or password is null", userServiceInterface.loginValidation(user.getUsername(), user.getPassword()));
//
//        assertNotNull("loginValidationTest: fails if username for new user is null", user.getUsername());
//        assertNotNull("loginValidationTest: fails if password for new user is null", user.getPassword());
//
////        @Test
////        public void validateUserInvalidUserValidPasswordTest() {
////            assertFalse("validateUserInvalidUserValidPasswordTest: should fail using an invalid user, valid pass", loginService.validateUser(username + "not", password));
////        }
////
////        @Test
////        public void validateUserInvalidUserInvalidPasswordTest() {
////            assertFalse("validateUserInvalidUserInvalidPasswordTest: should fail using an invalid user, valid pass", loginService.validateUser(username + "not", password + "extra"));
//    }


//    @Test
//    public void allReadyAddedUniqueness(){
//        User user = new User(username, password);
//        assertFalse("allReadyAddedUniqueness: if this fails, then the user was already added",
//                userServiceInterface.uniqueUser(user.getUsername()));
//    }

//    @Test
//    public void userLookupIDTest(){
//        User user = new User(username, password);
//        userServiceInterface.addUser(user.getUsername(), user.getPassword());
//        Long userIDCheck = user.getId();
////        assertNotNull("userLookupIDTest: new user ID was not added", userServiceInterface.userLookupID(userIDCheck));
//        assertNotNull("userLookupIDTest: new user ID was not added", userServiceInterface.userLookupID(userIDCheck));
//
//
////        assertFalse("userLookupIDTest: new user ID shouldn't match existing user ID", userIDCheck == userRepository.findAll() );
////        assertTrue("userLookupID")
//    }

//    @Test
//    public void userLookupIDTest(){
//        User user = new User(username, password);
//        userServiceInterface.addUser(user.getUsername(), user.getPassword());
//        Long userIDCheck = user.getId();
//        assertNotNull("userLookupIDTest: new user ID was not added",
//                userServiceInterface.userLookupID(user.getId()));
//    }


//    @Test
//    public void checkForNullUser(){
////        User user = new User(username, password);
//        User bob = userServiceInterface.userLookupUsername(username);
//        assertNotNull("checkForNullUser: a new user is not within the database", bob);
//        assertEquals("checkForNullUser: newly added user name does not match fetched user", username, bob.getUsername());
////       assertTrue("addUserTest: user should have an ID added", )
//        //I have a question about this for Nate
////            assertNotNull("addUserTest: a password was not added", user.getPassword());
//    }




//    @Test
//    public void uniqueUserTest(){
////        User user = new User(username, password);
//        userServiceInterface.addUser("joshual", password);
//
////        userServiceInterface.addUser(username, password);
//        userServiceInterface.addUser(username, "this is it");
////        String users = userRepository.toString();
////        System.out.println(users.toString());
////        String newUser = userServiceInterface.userLookupUsername(username);
////        assertFalse("uniqueUserTest: new user should not have same username as another user", userServiceInterface.uniqueUser((user.getUsername())));
//        assertTrue("uniqueUserTest: new user should not have same username as another user",
//                userServiceInterface.uniqueUser("joshual"));
//
//    }