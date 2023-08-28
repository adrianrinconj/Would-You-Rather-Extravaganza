package edu.carroll.cs389.service;

import edu.carroll.cs389.web.form.LoginForm;

public interface LoginService {
    boolean validateUser(String username, String password);

    /**
     * Given a loginForm, determine if the information provided is valid, and the user exists in the system.
     * @param form - Data containing user login information, such as username and password.
     * @return true if data exists and matches what's on record, false otherwise
     */
    boolean validateUser(LoginForm form);
}