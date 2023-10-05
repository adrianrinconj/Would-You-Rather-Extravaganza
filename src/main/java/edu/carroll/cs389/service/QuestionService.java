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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository repository;

    // Fetch all questions
    public Iterable<Question> getAllQuestions() {
        return repository.findAll();
    }

    // Add a new question
    public void addQuestion(Question question) {
        repository.save(question);
    }

    public Question randomQuestion() {
        List<Question> allQ = repository.findAll();
        // Stack Overflow help with getting a random entity from the DB
        int idx = (int)(Math.random() * allQ.size());
        return allQ.get(idx);
//        Question q = null;
//        if (questionPage.hasContent()) {
//            q = questionPage.getContent().get(0);
        }
//        return q;
    }




//package edu.carroll.cs389.service;
//        import edu.carroll.cs389.jpa.model.Question;
//        import edu.carroll.cs389.jpa.repo.QuestionRepository;
//        import org.springframework.data.domain.Page;
//        import org.springframework.data.domain.PageRequest;
//        import org.springframework.beans.factory.annotation.Autowired;
//        import org.springframework.stereotype.Service;
//        import java.util.*;
//
//        import java.util.HashSet;
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
//        // Stack Overflow help with getting a random entity from the DB
//        Long qty = repository.count();
//        HashSet hs = new HashSet();
//        while (hs.size() < qty) {
//            int idx = (int) (Math.random() * qty);
//            hs.add(idx);
//        }
//        Iterator it = hs.iterator();
//        int itNum = 0;
//        while (it.hasNext()) {
//            itNum = (Integer) it.next();
//        }
//        Page<Question> questionPage = repository.findAll(PageRequest.of(itNum, 1));
//        Question q = null;
//        if (questionPage.hasContent()) {
//            q = questionPage.getContent().get(0);
//        }
//        return q;
//    }
//
//}

