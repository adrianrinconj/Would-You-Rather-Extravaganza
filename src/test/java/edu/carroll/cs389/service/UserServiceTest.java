package edu.carroll.cs389.service;

import java.util.ArrayList;
import java.util.List;

import edu.carroll.cs389.jpa.model.Question;
import edu.carroll.cs389.jpa.model.User;
import edu.carroll.cs389.jpa.repo.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
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

//    private static final User user = new User(username, password);

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
    public void userLookupUsernameTest() {
        userServiceInterface.addUser(username, password);
        User user = userServiceInterface.userLookupUsername(username);
        assertNotNull("userLookupUsernameTest: is null but should return a username",
                userServiceInterface.userLookupUsername(user.getUsername()));
    }

    @Test
    public void lookupNonexistingUsernameTest() {
        assertNull("lookupNonexistingUsernameTest: is null but should return a username",
                userServiceInterface.userLookupUsername("sdalkfjads;lf"));
    }

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

    @Test
    public void uniqueUserTest(){
        assertTrue("uniqueUserTest: new user should not be added yet, or username is already taken",
                userServiceInterface.uniqueUser(username));

    }

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

    @Test
    public void userLookupIDTest(){
        User user = new User(username, password);
        userServiceInterface.addUser(user.getUsername(), user.getPassword());
        String userUsername = user.getUsername();
        User newUser = userServiceInterface.userLookupUsername(userUsername);
        Long userId = newUser.getId();
//        Long userId = userServiceInterface.userLookupID(newUser.getId());
        assertNotNull("userLookupIDTest: user ID should not be null",
                userServiceInterface.userLookupID(userId));
    }

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

    @Test
    public void loginValidationTest(){
        User user = new User(username, password);
        userServiceInterface.addUser(user.getUsername(), user.getPassword());
        assertNotNull("loginValidationTest: username and password should not be null",
                userServiceInterface.loginValidation(user.getUsername(), user.getPassword()));
//        assertNotNull("loginValidationTest: fails if username for new user is null", user.getUsername());
//        assertNotNull("loginValidationTest: fails if password for new user is null", user.getPassword());
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
//        assertNotNull("loginValidationPasswordNullTest: password should be null",
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
//        assertTrue("");
    }



//    @Test
//    public void markQuestionAsSeenTest(){
//        User user = new User(username, password);
//        userServiceInterface.addUser(username, password);
//        User newUser = userServiceInterface.userLookupUsername(username);
//        assertTrue("markQuestionAsSeenTest: ", userServiceInterface.);
//    }

//    @Test
//    public void userLookupUsernameTest(){
//        User user = new User(username, password);
//        assertNotNull("userLookupUsernameTest: fails if user is not found", userServiceInterface.userLookupUsername(user.getUsername()));
//
//
//    }
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
