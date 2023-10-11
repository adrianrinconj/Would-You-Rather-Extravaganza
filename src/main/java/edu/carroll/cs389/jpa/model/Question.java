package edu.carroll.cs389.jpa.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    // Constructors
    public Question() {
    }

    public Question(String optionA, String optionB) {
        this.optionA = optionA;
        this.optionB = optionB;

    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public List<User> getVotesForOptionA() {
        return votesForOptionA;
    }

    public void voteForOptionA(User votingUser) {
        votesForOptionA.add(votingUser);
    }

    public List<User> getVotesForOptionB() {
        return votesForOptionB;
    }

    public void voteForOptionB(User votingUser) {
        votesForOptionB.add(votingUser);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
