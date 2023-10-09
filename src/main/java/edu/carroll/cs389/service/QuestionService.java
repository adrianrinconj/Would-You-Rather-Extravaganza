//package edu.carroll.cs389.service;
//import edu.carroll.cs389.jpa.model.Question;
//import edu.carroll.cs389.jpa.repo.QuestionRepository;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.HashSet;
//import java.util.Iterator;
//
//@Service
//public class QuestionService {
//
//    @Autowired
//    private QuestionRepository repository;
//
//    // Fetch all questions
//    public Iterable<Question> getAllQuestions() {
//        return repository.findAll();
//    }
//
//    // Add a new question
//    public void addQuestion(Question question) {
//        repository.save(question);
//    }
//
//    public Question randomQuestion() {
//        List<Question> allQ = repository.findAll();
//        // Stack Overflow help with getting a random entity from the DB
//        Long qty = repository.count();
//        int idx = (int)(Math.random() * qty);
//        Page<Question> questionPage = repository.findAll(PageRequest.of(idx, 1));
//        Question q = null;
//        if (questionPage.hasContent()) {
//            q = questionPage.getContent().get(0);
//        }
//        return q;
//    }
//
//}


package edu.carroll.cs389.service;
import edu.carroll.cs389.jpa.model.Question;
import edu.carroll.cs389.jpa.repo.QuestionRepository;
import edu.carroll.cs389.jpa.repo.UserRepository;
import org.springframework.stereotype.Service;
import edu.carroll.cs389.jpa.model.User;

import java.util.List;

@Service
public class QuestionService {

    private QuestionRepository repository;

    private UserRepository userRepository;

    // constructor
    public QuestionService(QuestionRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    // Fetch all questions
    public Iterable<Question> getAllQuestions() {
        return repository.findAll();
    }

    // Add a new question
    public void addQuestion(Question question) {
        if (uniqueQuestion(question)) {
            repository.save(question);
        }
    }
    // makes sure that all questions are unique
    public boolean uniqueQuestion(Question newQuestion) {
        for (Question currentQuestion : getAllQuestions()) {
            if (newQuestion.getOptionA() == currentQuestion.getOptionA() || newQuestion.getOptionB() == currentQuestion.getOptionB()){
                return false;
            }
        }
        return true;
    }


    public Question randomUnseenQuestion(User currentUser) {
        List<Question> allQ = repository.findAll();

        // Remove all questions the user has already seen
        allQ.removeAll(currentUser.getSeenQuestions());

        // If the user has seen all the questions, handle accordingly.
        if (allQ.isEmpty()) {
            //XXX HOW SHOULD THIS BE HANDLED?
            return null;
        }

        // Randomly select a question from the remaining questions
        int idx = (int)(Math.random() * allQ.size());
        return allQ.get(idx);
    }

    public void markQuestionAsSeen(User user, Question question) {
        user.getSeenQuestions().add(question);
        userRepository.save(user);
    }


}