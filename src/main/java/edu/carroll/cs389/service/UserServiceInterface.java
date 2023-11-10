package edu.carroll.cs389.service;

import edu.carroll.cs389.jpa.model.User;

/**
 * Defines the contract for the User Service.
 * This interface provides methods for managing user-related operations such as user creation, authentication, and lookup.
 */
public interface UserServiceInterface {

    /**
     * Adds a new user to the system.
     *
     * @param newUsername The username of the new user.
     * @param newPassword The password of the new user.
     * @return True if the user is successfully added, false if the username already exists.
     */
    boolean addUser(String newUsername, String newPassword);

    /**
     * Checks if a given username is unique across all users.
     *
     * @param Username The username to check for uniqueness.
     * @return True if the username is unique, false otherwise.
     */
    boolean uniqueUser(String Username);

    /**
     * Validates the user's login credentials.
     *
     * @param Username   The username for login.
     * @param rawPassword The raw password for login.
     * @return The User object if credentials are valid, null otherwise.
     */
    User loginValidation(String Username, String rawPassword);

    /**
     * Retrieves a user by their unique ID.
     *
     * @param UserID The unique ID of the user.
     * @return The User object if found, null otherwise.
     */
    User userLookupID(Long UserID);

    /**
     * Retrieves a user by their username.
     *
     * @param Username The username of the user.
     * @return The User object if found, null otherwise.
     */
    User userLookupUsername(String Username);

    /**
     * Resets the list of questions seen by the specified user.
     *
     * @param user The user whose seen questions list will be reset.
     */
    void resetSeenQuestions(User user);
}
