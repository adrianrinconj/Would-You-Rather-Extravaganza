package edu.carroll.cs389.service;

import edu.carroll.cs389.jpa.model.User;
import edu.carroll.cs389.jpa.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCrypt;


import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Implementation of the User Service.
 * Provides functionalities related to User entities.
 */
@Service
public class UserServiceImpl implements UserServiceInterface {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;

    private final BCrypt userCrypto = new BCrypt();

    /**
     * Constructs a new instance of UserServiceImpl.
     *
     * @param userRepository The user repository used for data access.
     */
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Attempts to add a new user to the system.
     *
     * @param newUsername The String representing the new users desired username.
     *                    This must be unique from the usernames of existing users.
     * @return true if the user is successfully added, false if a user with the same username already exists.
     */
    @Override
    public boolean addUser(String newUsername, String newPassword) {
        if (uniqueUser(newUsername)) {
            User newUser = new User(newUsername,BCrypt.hashpw(newPassword, BCrypt.gensalt()));
            userRepository.save(newUser);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if a user with the given username exists in the system.
     *
     * @param Username The username to check for uniqueness.
     * @return true if the username is unique, false otherwise.
     */
    @Override
    public boolean uniqueUser(String Username) {
        for (User a : userRepository.findAll()) {
            if (Objects.equals(a.getUsername(), Username)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Validates a user's login credentials.
     *
     * @param Username The username of the user attempting to log in.
     * @param rawPassword The password provided by the user.
     * @return The User object if the login is successful, null otherwise.
     */
    @Override
    public User loginValidation(String Username, String rawPassword) {
        // log information about the login attempt
        log.info("loginValidation: user '{}' attempted login", Username);

        //ensure only one user with the given username exists (should always be true)
        List<User> users = userRepository.findByUsernameIgnoreCase(Username);
        if (users.size() != 1) {
            log.debug("loginValidation: found {} users", users.size());
            return null;
        }
        //check the single users username and password for validity
        for (User a : userRepository.findAll()) {
            if (Objects.equals(a.getUsername(), Username) && BCrypt.checkpw(rawPassword, a.getPassword())) {
                // return the user object if the login is successful
                log.info("login successful");
                return a;
            }
        }
        return null;
    }

    /**
     * Looks up a user by their ID.
     *
     * @param UserID The ID of the user to find.
     * @return The User object if found, null otherwise.
     */
    @Override
    public User userLookupID(Long UserID) {
        for (User a : userRepository.findAll()) {
            if (Objects.equals(a.getId(), UserID)) {
                return a;
            }
        }
        return null;
    }

    /**
     * Looks up a user by their username.
     *
     * @param Username The username of the user to find.
     * @return The User object if found, null otherwise.
     */
    @Override
    public User userLookupUsername(String Username) {
        for (User a : userRepository.findAll()) {
            if (a != null && Objects.equals(a.getUsername(), Username)) {
                return a;
            }
        }
        return null;
    }

    /**
     * Resets the list of seen questions for a user.
     *
     * @param user The user whose seen questions list will be cleared.
     */
    @Override
    public void resetSeenQuestions(User user) {
        user.getSeenQuestions().clear();
    }
}
