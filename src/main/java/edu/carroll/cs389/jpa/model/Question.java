package edu.carroll.cs389.jpa.model;

import jakarta.persistence.*;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "optionA", nullable = false, unique = true)
    private String optionA;

    @Column(name = "optionB", nullable = false, unique = true)
    private String optionB;

    // Constructors
    public Question() {}

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
// Getters and Setters
    // ... auto-generate using your IDE
}
