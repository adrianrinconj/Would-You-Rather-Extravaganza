package edu.carroll.cs389.web.controller;

import edu.carroll.cs389.jpa.model.Question;
import edu.carroll.cs389.jpa.model.User;
import edu.carroll.cs389.service.QuestionServiceImpl;
import edu.carroll.cs389.service.QuestionServiceInterface;
import edu.carroll.cs389.service.UserServiceImpl;
import edu.carroll.cs389.service.UserServiceInterface;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.repository.query.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;

/**
 * Controller responsible for displaying the "Would You Rather" game options and handling user votes.
 */
@Controller
public class WouldYouRatherDisplayController {
    private final QuestionServiceInterface questionService;
    private final UserServiceInterface userService;
    private static final Logger logInfo = LoggerFactory.getLogger((WouldYouRatherDisplayController.class));

    /**
     * Constructs the WouldYouRatherDisplayController with the provided question and user service implementations.
     *
     * @param questionService The question service implementation.
     * @param userService The user service implementation.
     */
    public WouldYouRatherDisplayController(QuestionServiceInterface questionService, UserServiceInterface userService) {
        this.questionService = questionService;
        this.userService = userService;
        if (userService.userLookupUsername("Guest") == null) {
            userService.addUser(new User("Guest", "Password"));
        }
    }


    /**
     * Handles the GET request for displaying game options.
     *
     * @param model The model to be populated for the view.
     * @return The name of the options display view.
     */
    @GetMapping("/DisplayOptions")
    public String optionsGet(Model model, HttpSession session) {
        User currentUser = ensureLoggedIn(session);
        if (currentUser == null) {
            return "redirect:/login";
        }
        Question randomQuestion = questionService.randomUnseenQuestion(currentUser);
        questionService.markQuestionAsSeen(currentUser, randomQuestion);

        if (randomQuestion != null) {
            model.addAttribute("currentQuestion",randomQuestion);
            logInfo.info("randomQuestion is not null; both optionA and optionB are filled");

            model.addAttribute("optionA", randomQuestion.getOptionA());
            model.addAttribute("optionB", randomQuestion.getOptionB());
        } else {
            //logging
            logInfo.debug("one of the questions has no options");

            model.addAttribute("optionA", "no option");
            model.addAttribute("optionB", "no option");
        }

        return "/DisplayOptions";
    }


    /**
     * Handles the GET request for voting for option B.
     *
     * @param session The current session of the user accessing the page
     * @return A redirect instruction to the results page.
     */
    @PostMapping(value = "/DisplayOptions", params="vote=optionA")
    public String voteForOptionA(HttpSession session, @RequestParam Long id) {
        User currentUser = ensureLoggedIn(session);
        if (currentUser == null) {
            return "redirect:/login";
        }
        Question votedQuestion = questionService.questionIdLookup(id);
        if (votedQuestion != null) {
            session.setAttribute("lastQuestionID", id );
            votedQuestion.voteForOptionA(currentUser);
            return "redirect:/results";
        }
        return "/DisplayOptions";
    }

    @PostMapping(value = "/DisplayOptions", params="vote=optionB")
    public String voteForOptionB(HttpSession session, @RequestParam Long id) {
        User currentUser = ensureLoggedIn(session);
        if (currentUser == null) {
            return "redirect:/login";
        }

        Question votedQuestion = questionService.questionIdLookup(id);
        
        if (votedQuestion != null) {
            session.setAttribute("lastQuestionID", id );
            votedQuestion.voteForOptionB(currentUser);
            return "redirect:/results";
        }
        return "/DisplayOptions";
    }

    /**
     * Handles the GET request for displaying the voting results.
     *
     * @param model The model to be populated for the view.
     * @return The name of the results view.
     */
    @GetMapping("/results")
    public String showResults(Model model, HttpSession session) {
        Question votedQuestion = questionService.questionIdLookup((Long) session.getAttribute("lastQuestionID"));
        model.addAttribute("AVotes", votedQuestion.getVotesForOptionA().size());
        model.addAttribute("BVotes", votedQuestion.getVotesForOptionB().size());
        return "Results";
    }

    private User ensureLoggedIn (HttpSession session) {
        Long currentUserID = (Long) session.getAttribute("loggedUserID");
        if (currentUserID == null) {
            return null;
        }
        User currentUser = userService.userLookupID(currentUserID);
        if (currentUser == null) {
            return null;
        }
        return currentUser;
    }
}
