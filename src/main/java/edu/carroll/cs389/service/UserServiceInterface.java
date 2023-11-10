package edu.carroll.cs389.service;

import edu.carroll.cs389.jpa.model.User;

/**
 * Defines the contract for the User Service.
 */
public interface UserServiceInterface {

    /**
     * Adds a new user.
     *
     * @param newUser The user to be added.
     * @return True if the user was added successfully, false otherwise.
     */

    boolean addUser(String newUsername, String newPassword);

    /**
     * Checks if a user is unique based on the username.
     *
     * @param Username The username to be checked.
     * @return True if the username is unique, false otherwise.
     */
    boolean uniqueUser(String Username);

    /**
     * Validates the login credentials.
     *
     * @param Username   The username.
     * @param rawPassword The raw password.
     * @return The user if the credentials are valid, null otherwise.
     */
    User loginValidation(String Username, String rawPassword);

    /**
     * Looks up a user based on the user ID.
     *
     * @param UserID The user ID.
     * @return The user if found, null otherwise.
     */
    User userLookupID(Long UserID);

    /**
     * Looks up a user based on the username.
     *
     * @param Username The username.
     * @return The user if found, null otherwise.
     */
    User userLookupUsername(String Username);

    void resetSeenQuestions(User user);
}

