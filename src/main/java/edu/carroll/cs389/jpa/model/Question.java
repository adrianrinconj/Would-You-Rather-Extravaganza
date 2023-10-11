package edu.carroll.cs389.jpa.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a question entity with two options for voting.
 */
@Entity
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "optionA", nullable = false, unique = true)
    private String optionA;

    @Column(name = "optionB", nullable = false, unique = true)
    private String optionB;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "votes_optionA",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> votesForOptionA = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "votes_optionB",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> votesForOptionB = new ArrayList<>();

    /**
     * Default constructor.
     */
    public Question() {
    }

    /**
     * Constructs a question with the given options.
     *
     * @param optionA The first voting option.
     * @param optionB The second voting option.
     */
    public Question(String optionA, String optionB) {
        this.optionA = optionA;
        this.optionB = optionB;
    }

    /**
     * @return The first voting option.
     */
    public String getOptionA() {
        return optionA;
    }

    /**
     * Sets the first voting option.
     *
     * @param optionA The first voting option.
     */
    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    /**
     * @return The second voting option.
     */
    public String getOptionB() {
        return optionB;
    }

    /**
     * Sets the second voting option.
     *
     * @param optionB The second voting option.
     */
    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    /**
     * @return The list of users who voted for the first option.
     */
    public List<User> getVotesForOptionA() {
        return votesForOptionA;
    }

    /**
     * Adds a user's vote for the first option.
     *
     * @param votingUser The user who is voting.
     */
    public void voteForOptionA(User votingUser) {
        votesForOptionA.add(votingUser);
    }

    /**
     * @return The list of users who voted for the second option.
     */
    public List<User> getVotesForOptionB() {
        return votesForOptionB;
    }

    /**
     * Adds a user's vote for the second option.
     *
     * @param votingUser The user who is voting.
     */
    public void voteForOptionB(User votingUser) {
        votesForOptionB.add(votingUser);
    }

    /**
     * @return The unique identifier of the question.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the question.
     *
     * @param id The unique identifier.
     */
    public void setId(Long id) {
        this.id = id;
    }
}
