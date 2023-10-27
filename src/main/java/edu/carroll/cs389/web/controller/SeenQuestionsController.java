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

/**
 * Controller responsible for handling the display of seen questions to the user.
 */
@Controller
public class SeenQuestionsController {
    private static final Logger logInfo = LoggerFactory.getLogger(SeenQuestionsController.class);

    private final UserServiceInterface userService;
    private final QuestionServiceInterface questionService;

    /**
     * Constructs a new {@code SeenQuestionsController} with the provided user and question services.
     *
     * @param userService     The user service implementation.
     * @param questionService The question service implementation.
     */
    public SeenQuestionsController(UserServiceInterface userService, QuestionServiceInterface questionService) {
        this.userService = userService;
        this.questionService = questionService;
    }

    /**
     * Displays the list of seen questions to the user.
     *
     * This method handles GET requests to the "/seenQuestions" endpoint. If the user is not logged in,
     * they are redirected to the login page. Otherwise, it retrieves the current question based on the
     * user's session and updates the model with the question's options for the view to display.
     *
     * @param model   The model to be populated for the view.
     * @param session The current HTTP session.
     * @return The name of the seen questions view or a redirect instruction.
     */
    @GetMapping("/seenQuestions")
    public String displaySeenQuestions(Model model, HttpSession session) {
        User currentUser = ensureLoggedIn(session);
        if (currentUser == null) {
            return "redirect:/";
        }

        Question currentQuestion = questionService.getSeenQuestion(currentUser,
                (Question) session.getAttribute("lastQuestion"),
                (Boolean) session.getAttribute("nextOrPrevious"));

        if (currentQuestion != null) {
            session.setAttribute("lastQuestion", currentQuestion);
            model.addAttribute("optionA", currentQuestion.getOptionA());
            model.addAttribute("optionB", currentQuestion.getOptionB());
        } else {
            model.addAttribute("optionA", "You have not seen any questions yet");
            model.addAttribute("optionB", "look through some questions and return");
        }
        return "/SeenQuestions";
    }

    /**
     * Handles the request to view the next seen question.
     *
     * This method handles POST requests to the "/seenQuestions" endpoint with the "direction" parameter set to "next".
     * It updates the session to indicate that the next question should be displayed when returning to the seen questions view.
     *
     * @param session The current HTTP session.
     * @return A redirect instruction to the seen questions view.
     */
    @PostMapping(value = "/seenQuestions", params = "direction=next")
    public String displayNextSeenQuestion(HttpSession session) {
        session.setAttribute("nextOrPrevious", true);
        return "redirect:/seenQuestions";
    }

    /**
     * Handles the request to view the previous seen question.
     *
     * This method handles POST requests to the "/seenQuestions" endpoint with the "direction" parameter set to "previous".
     * It updates the session to indicate that the previous question should be displayed when returning to the seen questions view.
     *
     * @param session The current HTTP session.
     * @return A redirect instruction to the seen questions view.
     */
    @PostMapping(value = "/seenQuestions", params = "direction=previous")
    public String displayPreviousSeenQuestion(HttpSession session) {
        session.setAttribute("nextOrPrevious", false);
        return "redirect:/seenQuestions";
    }

    /**
     * Ensures that the user is currently logged in.
     *
     * This helper method checks the session for the logged-in user's ID and retrieves the user object.
     * If the user is not logged in or the user object cannot be found, it returns null.
     *
     * @param session The current HTTP session.
     * @return The logged-in user object or null if the user is not logged in.
     */
    private User ensureLoggedIn(HttpSession session) {
        Long currentUserID = (Long) session.getAttribute("loggedUserID");
        if (currentUserID == null) {
            return null;
        }
        return userService.userLookupID(currentUserID);
    }
}
