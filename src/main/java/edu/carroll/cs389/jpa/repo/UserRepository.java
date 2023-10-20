package edu.carroll.cs389.jpa.repo;

import edu.carroll.cs389.jpa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findByUsernameIgnoreCase(String username);


}