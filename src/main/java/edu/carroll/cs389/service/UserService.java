package edu.carroll.cs389.service;

import edu.carroll.cs389.jpa.model.User;
import edu.carroll.cs389.jpa.repo.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(User newUser) {
        userRepository.save(newUser);
    }

    public User loginValidation(String Username, String rawPassword) {

        for (User a : userRepository.findAll()) {

            if(a.getUsername() == Username && a.getPassword() == a.encryptPassword(rawPassword)){
                return a;
            }

        }

        return null;
    }
    

}
