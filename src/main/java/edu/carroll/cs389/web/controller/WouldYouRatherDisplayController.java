package edu.carroll.cs389.web.controller;


import edu.carroll.cs389.jpa.model.Question;
import edu.carroll.cs389.jpa.model.User;
import edu.carroll.cs389.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WouldYouRatherDisplayController {


    private final QuestionService questionService;


    private User currentUser = new User("loba", "Password");
    private Question currentQuestion;

    public WouldYouRatherDisplayController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/DisplayOptions")
    public String optionsGet(Model model) {
        Question randomQuestion = questionService.randomUnseenQuestion(currentUser);
        questionService.markQuestionAsSeen(currentUser, randomQuestion);


        if(randomQuestion != null) {
            model.addAttribute("optionA", randomQuestion.getOptionA());
            model.addAttribute("optionB", randomQuestion.getOptionB());
            currentQuestion = randomQuestion;
        }
        else{
            model.addAttribute("optionA", "no option");
            model.addAttribute("optionB", "no option");
        }

        return "/DisplayOptions";
    }

    @GetMapping("/voteA")
    public String voteForOptionA(Model model) {
        currentQuestion.voteForOptionA(currentUser);
        return "redirect:/results";
    }

    @GetMapping("/voteB")
    public String voteForOptionB(Model model) {
        currentQuestion.voteForOptionB(currentUser);
        return "redirect:/results";
    }

    @GetMapping("/results")
    public String showResults(Model model) {

        model.addAttribute("AVotes", currentQuestion.getVotesForOptionA().size());
        model.addAttribute("BVotes", currentQuestion.getVotesForOptionB().size());
        return "Results";
    }

}