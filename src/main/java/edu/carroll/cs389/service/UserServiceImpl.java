package edu.carroll.cs389.service;

import edu.carroll.cs389.jpa.model.User;
import edu.carroll.cs389.jpa.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;

/**
 * Implementation of the User Service.
 */
@Service
public class UserServiceImpl implements UserServiceInterface {

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
    public User loginValidation(String Username, String rawPassword) {
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
}
