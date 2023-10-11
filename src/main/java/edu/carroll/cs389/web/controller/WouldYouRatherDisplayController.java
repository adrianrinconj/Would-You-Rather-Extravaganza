package edu.carroll.cs389.web.controller;

import edu.carroll.cs389.jpa.model.Question;
import edu.carroll.cs389.jpa.model.User;
import edu.carroll.cs389.service.QuestionServiceImpl;
import edu.carroll.cs389.service.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller responsible for displaying the "Would You Rather" game options and handling user votes.
 */
@Controller
public class WouldYouRatherDisplayController {

    private final QuestionServiceImpl questionService;
    private final UserServiceImpl userServiceImpl;

    private User currentUser;
    private Question currentQuestion;

    /**
     * Constructs the WouldYouRatherDisplayController with the provided question and user service implementations.
     *
     * @param questionService The question service implementation.
     * @param userServiceImpl The user service implementation.
     */
    public WouldYouRatherDisplayController(QuestionServiceImpl questionService, UserServiceImpl userServiceImpl) {
        this.questionService = questionService;
        this.userServiceImpl = userServiceImpl;
        if (userServiceImpl.userLookupUsername("Guest") == null) {
            userServiceImpl.addUser(new User("Guest", "Password"));
        }
        currentUser = userServiceImpl.userLookupUsername("Guest");
    }


    /**
     * Handles the GET request for displaying game options.
     *
     * @param model The model to be populated for the view.
     * @return The name of the options display view.
     */
    @GetMapping("/DisplayOptions")
    public String optionsGet(Model model) {
        Question randomQuestion = questionService.randomUnseenQuestion(currentUser);
        questionService.markQuestionAsSeen(currentUser, randomQuestion);

        if (randomQuestion != null) {
            model.addAttribute("optionA", randomQuestion.getOptionA());
            model.addAttribute("optionB", randomQuestion.getOptionB());
            currentQuestion = randomQuestion;
        } else {
            model.addAttribute("optionA", "no option");
            model.addAttribute("optionB", "no option");
        }

        return "/DisplayOptions";
    }

    /**
     * Handles the GET request for voting for option A.
     *
     * @param model The model to be populated for the view.
     * @return A redirect instruction to the results page.
     */
    @GetMapping("/voteA")
    public String voteForOptionA(Model model) {
        currentQuestion.voteForOptionA(currentUser);
        return "redirect:/results";
    }

    /**
     * Handles the GET request for voting for option B.
     *
     * @param model The model to be populated for the view.
     * @return A redirect instruction to the results page.
     */
    @GetMapping("/voteB")
    public String voteForOptionB(Model model) {
        currentQuestion.voteForOptionB(currentUser);
        return "redirect:/results";
    }

    /**
     * Handles the GET request for displaying the voting results.
     *
     * @param model The model to be populated for the view.
     * @return The name of the results view.
     */
    @GetMapping("/results")
    public String showResults(Model model) {
        model.addAttribute("AVotes", currentQuestion.getVotesForOptionA().size());
        model.addAttribute("BVotes", currentQuestion.getVotesForOptionB().size());
        return "Results";
    }
}
