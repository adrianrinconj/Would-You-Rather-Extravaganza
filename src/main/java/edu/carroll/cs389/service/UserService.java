package edu.carroll.cs389.service;

import edu.carroll.cs389.jpa.model.User;
import edu.carroll.cs389.jpa.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean addUser(User newUser) {
        if (uniqueUser(newUser.getUsername())) {
            userRepository.save(newUser);
            return true;
        }
        else {
            return false;
        }
    }

    public boolean uniqueUser(String Username) {
        for (User a : userRepository.findAll()) {
            if (Objects.equals(a.getUsername(), Username)) {
                return false;
            }
        }
        return true;
    }

    public User loginValidation(String Username, String rawPassword) {
        for (User a : userRepository.findAll()) {
            if (Objects.equals(a.getUsername(), Username) && a.getPassword() == a.encryptPassword(rawPassword)) {
                return a;
            }
        }
        return null;
    }

    public User userLookupID (Long UserID) {
        for (User a : userRepository.findAll()) {
            if (Objects.equals(a.getId(), UserID)) {
                return a;
            }
        }
        return null;
    }
    public User userLookupUsername(String Username) {
        for (User a : userRepository.findAll()) {
            if (Objects.equals(a.getUsername(), Username)) {
                return a;
            }
        }
        return null;
    }

}
