package edu.carroll.cs389.web.controller;

import edu.carroll.cs389.jpa.model.Question;
import edu.carroll.cs389.jpa.model.User;
import edu.carroll.cs389.service.QuestionServiceInterface;
import edu.carroll.cs389.service.UserServiceInterface;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller responsible for displaying the "Would You Rather" game options and handling user votes.
 */
@Controller
public class WouldYouRatherDisplayController {
    private final QuestionServiceInterface questionService;
    private final UserServiceInterface userService;
    private static final Logger logInfo = LoggerFactory.getLogger(WouldYouRatherDisplayController.class);

    /**
     * Constructs a {@link WouldYouRatherDisplayController} with the provided question and user service implementations.
     *
     * @param questionService The question service implementation.
     * @param userService     The user service implementation.
     */
    public WouldYouRatherDisplayController(QuestionServiceInterface questionService, UserServiceInterface userService) {
        this.questionService = questionService;
        this.userService = userService;
        if (userService.userLookupUsername("Guest") == null) {
            userService.addUser(new User("Guest", "Password"));
        }
    }

    /**
     * Handles GET requests to the options display page.
     *
     * Displays a random, unseen "Would You Rather" question to the user.
     * If the user is not logged in, they are redirected to the login page.
     *
     * @param model   The {@link Model} object to pass data to the view.
     * @param session The {@link HttpSession} object to get the logged-in user's information.
     * @return The name of the options display view.
     */
    @GetMapping("/DisplayOptions")
    public String optionsGet(Model model, HttpSession session) {
        User currentUser = ensureLoggedIn(session);
        if (currentUser == null) {
            return "redirect:/";
        }
        Question randomQuestion = questionService.randomUnseenQuestion(currentUser);
        questionService.markQuestionAsSeen(currentUser, randomQuestion);

        if (randomQuestion != null) {
            logInfo.info("Random question is not null; both optionA and optionB are filled");
            model.addAttribute("currentQuestion", randomQuestion);
            model.addAttribute("optionA", randomQuestion.getOptionA());
            model.addAttribute("optionB", randomQuestion.getOptionB());
        } else {
            logInfo.debug("Out of questions");
            model.addAttribute("currentQuestion", null);
            model.addAttribute("optionA", "no option");
            model.addAttribute("optionB", "no option");
        }

        return "/DisplayOptions";
    }

    /**
     * Handles POST requests for voting for option A.
     *
     * @param session The {@link HttpSession} object to get the logged-in user's information.
     * @param id      The id of the question being voted on.
     * @return A redirect instruction to the results page.
     */
    @PostMapping(value = "/DisplayOptions", params = "vote=optionA")
    public String voteForOptionA(HttpSession session, @RequestParam(value = "id", required = false) Long id) {
        User currentUser = ensureLoggedIn(session);
        if (currentUser == null) {
            return "redirect:/";
        }

        Question votedQuestion = questionService.questionIdLookup(id);
        if (votedQuestion != null) {
            session.setAttribute("lastQuestionID", id);
            questionService.voteForOptionA(currentUser, votedQuestion);
            return "redirect:/results";
        }
        return "/DisplayOptions";
    }

    /**
     * Handles POST requests for voting for option B.
     *
     * @param session The {@link HttpSession} object to get the logged-in user's information.
     * @param id      The id of the question being voted on.
     * @return A redirect instruction to the results page.
     */
    @PostMapping(value = "/DisplayOptions", params = "vote=optionB")
    public String voteForOptionB(HttpSession session, @RequestParam(value = "id", required = false) Long id) {
        User currentUser = ensureLoggedIn(session);
        if (currentUser == null) {
            return "redirect:/";
        }

        Question votedQuestion = questionService.questionIdLookup(id);
        if (votedQuestion != null) {
            session.setAttribute("lastQuestionID", id);
            questionService.voteForOptionB(currentUser, votedQuestion);
            return "redirect:/results";
        }
        return "redirect:/DisplayOptions";
    }

    /**
     * Handles GET requests to the results page, showing the voting results of the last voted question.
     *
     * @param model   The {@link Model} object to pass data to the view.
     * @param session The {@link HttpSession} object to get the id of the last voted question.
     * @return The name of the results view.
     */
    @GetMapping("/results")
    public String showResults(Model model, HttpSession session) {
        Question votedQuestion = questionService.questionIdLookup((Long) session.getAttribute("lastQuestionID"));
        model.addAttribute("AVotes", votedQuestion.getVotesForOptionA().size());
        model.addAttribute("BVotes", votedQuestion.getVotesForOptionB().size());
        return "Results";
    }

    /**
     * Ensures that a user is logged in by checking the user's session.
     * If a user is logged in, their user information is retrieved and returned.
     * If no user is logged in, this method returns null.
     *
     * @param session The {@link HttpSession} object to get the logged-in user's information.
     * @return The {@link User} object representing the currently logged-in user,
     *         or null if no user is logged in.
     */
    private User ensureLoggedIn(HttpSession session) {
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
