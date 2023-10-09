package edu.carroll.cs389.jpa.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

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

    // using Byte allows larger numbers of users to be stored
    @Column(nullable = false)
    private byte[] password;

    private transient SecureRandom random = new SecureRandom();
    private byte[] salt = new byte[16];

    // ManyToMany relationship with Question entity
    @ManyToMany
    @JoinTable(name = "user_seen_questions",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id"))
    private List<Question> seenQuestions;


    // creates the User; constructor
    public User(String username, String rawPassword) {
        this.username = username;
        this.password = encryptPassword(rawPassword);  // Ensuring the password is encoded
        this.seenQuestions  = new ArrayList<>();
        random.nextBytes(salt);

    }

    //empty constructor needed
    public User() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getPassword() {
        return password;
    }

    // hashing algorithm that stores 'salt' and compares it to other passwords 'salt'
    public byte[] encryptPassword(String rawPassword) {

        //Message digest requires exception handling
        try {
            //this portion uses SHA-512 encryption to hash the password and store it
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            return md.digest(rawPassword.getBytes(StandardCharsets.UTF_8));
        }
        catch (java.security.NoSuchAlgorithmException e) {
            System.out.println("SHA-512 doe not exist");
        }

        return salt;
    }

    public List<Question> getSeenQuestions() {
        return seenQuestions;
    }

}
