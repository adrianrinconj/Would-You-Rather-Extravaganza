package edu.carroll.cs389.jpa.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user entity with associated authentication and relationship details.
 */
@Entity
@Table(name = "users")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @Column makes the tables. In this case this is the user table; not nullable
    @Column(unique = true, nullable = false)
    private String username;

    // Using Byte allows larger numbers of users to be stored
    @Column(nullable = false)
    private byte[] password;

    private transient SecureRandom random = new SecureRandom();
    private byte[] salt = new byte[16];

    // ManyToMany relationship with Question entity
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_seen_questions",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id"))
    private List<Question> seenQuestions;


    // creates the User; constructor
    /**
     * Constructs a user with the given username and raw password.
     *
     * @param username    The username of the user.
     * @param rawPassword The raw password which will be encrypted.
     */
    public User(String username, String rawPassword) {
        this.username = username;
        this.password = encryptPassword(rawPassword);  // Ensuring the password is encoded
        this.seenQuestions = new ArrayList<>();
        random.nextBytes(salt);
    }

    //empty constructor needed
    /**
     * Default constructor.
     */
    public User() {
    }

    /**
     * @return The unique identifier of the user.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the user.
     *
     * @param id The unique identifier.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return The username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     *
     * @param username The username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return The encrypted password of the user.
     */
    public byte[] getPassword() {
        return password;
    }

    // hashing algorithm that stores 'salt' and compares it to other passwords 'salt'
    /**
     * Encrypts the given raw password using SHA-512 and a salt.
     *
     * @param rawPassword The raw password to be encrypted.
     * @return The encrypted password.
     */
    public byte[] encryptPassword(String rawPassword) {
        try {
            // This portion uses SHA-512 encryption to hash the password and store it
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            return md.digest(rawPassword.getBytes(StandardCharsets.UTF_8));
        } catch (java.security.NoSuchAlgorithmException e) {
            System.out.println("SHA-512 does not exist");
        }
        return salt;
    }

    /**
     * @return The list of questions seen by the user.
     */
    public List<Question> getSeenQuestions() {
        return seenQuestions;
    }
}
