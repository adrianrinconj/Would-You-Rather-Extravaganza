package edu.carroll.cs389.jpa.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a question entity in the "Would You Rather" game.
 * Each question contains two options, and users can vote on either option.
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

    /**
     * List of users who voted for the first option.
     * The fetch type is EAGER, meaning the votes are loaded immediately.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "votes_optionA",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> votesForOptionA = new ArrayList<>();

    /**
     * List of users who voted for the second option.
     * The fetch type is EAGER, similar to votes for the first option.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "votes_optionB",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> votesForOptionB = new ArrayList<>();

    /**
     * Default constructor for JPA.
     */
    public Question() {
    }

    /**
     * Constructs a new Question with the specified options.
     *
     * @param optionA The text of the first voting option.
     * @param optionB The text of the second voting option.
     */
    public Question(String optionA, String optionB) {
        this.optionA = optionA;
        this.optionB = optionB;
    }

    /**
     * Gets the first voting option.
     *
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
     * Gets the second voting option.
     *
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
     * Gets the list of users who voted for the first option.
     *
     * @return A List of users who voted for the first option.
     */
    public List<User> getVotesForOptionA() {
        return votesForOptionA;
    }

    /**
     * Gets the list of users who voted for the second option.
     *
     * @return A List of users who voted for the second option.
     */
    public List<User> getVotesForOptionB() {
        return votesForOptionB;
    }

    /**
     * Gets the unique identifier of the question.
     *
     * @return The unique identifier of the question.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the question.
     *
     * @param id The unique identifier to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, optionA, optionB, votesForOptionA, votesForOptionB);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Question)) return false;
        Question question = (Question) o;
        if (optionA.equals(question.optionA) && optionB.equals(question.optionB)){
            return true;
        } else return optionA.equals(question.optionB) && optionB.equals(question.optionA);
    }
}
