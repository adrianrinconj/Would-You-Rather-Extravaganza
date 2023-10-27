package edu.carroll.cs389.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import edu.carroll.cs389.jpa.model.Question;
import edu.carroll.cs389.jpa.repo.QuestionRepository;
import edu.carroll.cs389.jpa.repo.UserRepository;
import edu.carroll.cs389.jpa.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Implementation of the Question Service.
 */
@Service
public class QuestionServiceImpl implements QuestionServiceInterface {
    private static final Logger log = LoggerFactory.getLogger(QuestionServiceImpl.class);


    private final QuestionRepository repository;
    private final UserServiceInterface userService;

    private final UserRepository userRepo;


    //Have nate check this!!!!
    //Do I do two of these for each question, if so, did I do it right???


    public QuestionServiceImpl(QuestionRepository repository, UserServiceInterface userService, UserRepository userRepo) {
        this.repository = repository;
        this.userService = userService;
        this.userRepo = userRepo;
    }

    @Override
    public Iterable<Question> getAllQuestions() {
        return repository.findAll();
    }

    @Override
    public void addQuestion(Question question) {
        if (uniqueQuestion(question)) {
            repository.save(question);
        }
    }

    @Override
    public boolean uniqueQuestion(Question newQuestion) {
        for (Question currentQuestion : getAllQuestions()) {
            if (Objects.equals(newQuestion.getOptionA(), currentQuestion.getOptionA()) || Objects.equals(newQuestion.getOptionB(), currentQuestion.getOptionB())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Question randomUnseenQuestion(User currentUser) {
        List<Question> allQ = repository.findAll();

        // Remove all questions the user has already seen
        allQ.removeAll(currentUser.getSeenQuestions());

        // If the user has seen all the questions, handle accordingly.
        if (allQ.isEmpty()) {
           return null;
        }

        // Randomly select a question from the remaining questions
        int idx = (int) (Math.random() * allQ.size());
        return allQ.get(idx);
    }

    @Override
    public Question getSeenQuestion(User currentUser, Question lastQuestion, Boolean gettingNext) {
        List<Question> seenQuestions = currentUser.getSeenQuestions();

        // If the user has not seen any of the questions, handle accordingly.
        if (seenQuestions.isEmpty()) {
            return null;
        }

        else if (lastQuestion == null || gettingNext == null) {
            int idx = (int) (Math.random() * seenQuestions.size()-1);
            return seenQuestions.get(idx);
        }


        else if (seenQuestions.contains(lastQuestion)) {
            int questionIndex = seenQuestions.indexOf(lastQuestion);
            if (gettingNext && questionIndex != seenQuestions.size()-1) {
                questionIndex += 1;
            }
            else if (!gettingNext && questionIndex != 0){
                questionIndex -= 1;
            }
            return seenQuestions.get(questionIndex);
        }
        else {
            return null;
        }
    }

    @Override
    public Question questionIdLookup(Long questionID) {
        for (Question a : getAllQuestions()) {
            if (Objects.equals(a.getId(), questionID)) {
                return a;
            }
        }
        return null;
    }

    @Override
    public void voteForOptionA(User user, Question question) {
        question.getVotesForOptionA().add(user);
        repository.save(question);
    }

    @Override
    public void voteForOptionB(User user, Question question) {
        question.getVotesForOptionB().add(user);
        repository.save(question);
    }

    @Override
    public void markQuestionAsSeen(User user, Question question) {
        if (question != null) {
            user.getSeenQuestions().add(question);
            userRepo.save(user);
        }
    }
}

