package edu.carroll.cs389.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import edu.carroll.cs389.jpa.model.User;
import edu.carroll.cs389.jpa.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Implementation of the User Service.
 */
@Service
public class UserServiceImpl implements UserServiceInterface {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean addUser(User newUser) {
        if (uniqueUser(newUser.getUsername())) {
            userRepository.save(newUser);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean uniqueUser(String Username) {
        for (User a : userRepository.findAll()) {
            if (Objects.equals(a.getUsername(), Username)) {
                return false;
            }
        }
        return true;
    }

    @Override

    //ask nate about this!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public User loginValidation(String Username, String rawPassword) {
        log.debug("loginValidation: user '{}' attempted login", Username);

        // Always do the lookup in a case-insensitive manner (lower-casing the data).
        List<User> users = userRepository.findByUsernameIgnoreCase(Username);

        // We expect 0 or 1, so if we get more than 1, bail out as this is an error we don't deal with properly.
        if (users.size() != 1) {
            log.debug("loginValidation: found {} users", users.size());
            return null;
        }

        for (User a : userRepository.findAll()) {
            if (Objects.equals(a.getUsername(), Username) && Arrays.equals(a.getPassword(), a.encryptPassword(rawPassword))) {
                return a;
            }
        }
        return null;
    }

    @Override
    public User userLookupID(Long UserID) {
        for (User a : userRepository.findAll()) {
            if (Objects.equals(a.getId(), UserID)) {
                return a;
            }
        }
        return null;
    }

    @Override
    public User userLookupUsername(String Username) {
        for (User a : userRepository.findAll()) {
            if (Objects.equals(a.getUsername(), Username)) {
                return a;
            }
        }
        return null;
    }

    @Override
    public void resetSeenQuestions(User user) {
        ;
    }
}
