package edu.carroll.cs389.jpa.repo;

import edu.carroll.cs389.jpa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Spring JPA repository for the {@link User} entity.
 * This interface extends JpaRepository, providing a layer for storage,
 * retrieval, update, and deletion on {@code User} objects
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Retrieves a list of users by their username, ignoring case.
     * This method is useful for case-insensitive authentication processes.
     *
     * @param username The username to search for.
     * @return A list of {@link User} entities matching the given username, ignoring case.
     *         Returns an empty list if no matching users are found.
     */
    List<User> findByUsernameIgnoreCase(String username);
}
