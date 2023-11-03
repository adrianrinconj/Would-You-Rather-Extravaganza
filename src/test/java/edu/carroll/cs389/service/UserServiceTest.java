package edu.carroll.cs389.service;

import java.util.List;

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
    @Autowired
    private UserServiceInterface userServiceInterface;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void addUserTest(){
       User user = new User(username, password);
       assertTrue("addUserTest: should succeed in having a user added", userServiceInterface.addUser(user));
       User bob = userServiceInterface.userLookupUsername(username);
       assertNotNull("addUserTest: a new user is not within the database", bob);
       assertEquals("addUserTest: newly added user name does not match fetched user", username, bob.getUsername());
//       assertTrue("addUserTest: user should have an ID added", )

        //I have a question about this for Nate
       assertNotNull("addUserTest: a password was not added", user.getPassword());

    }

    @Test
    public void uniqueUserTest(){
        User user = new User(username, password);
//        String newUser = userServiceInterface.userLookupUsername(username);
//        assertFalse("uniqueUserTest: new user should not have same username as another user", userServiceInterface.uniqueUser((user.getUsername())));
        assertTrue("uniqueUserTest: new user should not have same username as another user", userServiceInterface.uniqueUser((username)));

    }

    @Test
    public void userLookupIDTest(){
        User user = new User(username, password);
        userServiceInterface.addUser(user);
        Long userIDCheck = user.getId();
//        assertNotNull("userLookupIDTest: new user ID was not added", userServiceInterface.userLookupID(userIDCheck));
        assertNotNull("userLookupIDTest: new user ID was not added", userServiceInterface.userLookupID(userIDCheck));


//        assertFalse("userLookupIDTest: new user ID shouldn't match existing user ID", userIDCheck == userRepository.findAll() );
//        assertTrue("userLookupID")
    }

    @Test
    public void loginValidationTest(){
        User user = new User(username, password);
//        String usernameCheck = user.getUsername();

        //ask nate about how I would implement this since password returns byte[] datatype now
//        String passwordCheck = user.getPassword();
//        assertNotNull("loginValidationTest: fails if username or password is null", userServiceInterface.loginValidation(user.getUsername(), user.getPassword()));

        assertNotNull("loginValidationTest: fails if username for new user is null", user.getUsername());
        assertNotNull("loginValidationTest: fails if password for new user is null", user.getPassword());

//        @Test
//        public void validateUserInvalidUserValidPasswordTest() {
//            assertFalse("validateUserInvalidUserValidPasswordTest: should fail using an invalid user, valid pass", loginService.validateUser(username + "not", password));
//        }
//
//        @Test
//        public void validateUserInvalidUserInvalidPasswordTest() {
//            assertFalse("validateUserInvalidUserInvalidPasswordTest: should fail using an invalid user, valid pass", loginService.validateUser(username + "not", password + "extra"));
        }


        @Test
        public void userLookupUsernameTest(){
            User user = new User(username, password);
            assertNotNull("userLookupUsernameTest: fails if user is not found", userServiceInterface.userLookupUsername(user.getUsername()));


        }
    }



