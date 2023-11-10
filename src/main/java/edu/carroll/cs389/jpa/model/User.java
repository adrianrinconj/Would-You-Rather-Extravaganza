package edu.carroll.cs389.jpa.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a user entity within the application, including authentication and relationship details.
 */
@Entity
@Table(name = "users")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_seen_questions",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id")
    )
    private List<Question> seenQuestions;

    /**
     * Default constructor for JPA.
     */
    public User() {
    }

    /**
     * Constructs a user with the specified username and password.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.seenQuestions = new ArrayList<>();
    }

    /**
     * Gets the unique identifier of the user.
     *
     * @return The unique identifier of the user.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the user.
     *
     * @param id The unique identifier to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the username of the user.
     *
     * @return The username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     *
     * @param username The new username of the user.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password of the user.
     *
     * @return The password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password The new password of the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the list of questions seen by the user.
     *
     * @return The list of questions seen by the user.
     */
    public List<Question> getSeenQuestions() {
        return seenQuestions;
    }

    /**
     * Sets the list of questions seen by the user.
     *
     * @param seenQuestions The new list of questions seen by the user.
     */
    public void setSeenQuestions(List<Question> seenQuestions) {
        this.seenQuestions = seenQuestions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password);
    }
}
