package edu.carroll.cs389.web.controller;

import edu.carroll.cs389.jpa.model.Question;
import edu.carroll.cs389.jpa.model.User;
import edu.carroll.cs389.service.QuestionServiceInterface;
import edu.carroll.cs389.service.UserServiceInterface;
import edu.carroll.cs389.web.form.LoginForm;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller responsible for handling user login operations.
 */
@Controller
public class SeenQuestionsController {
    private static final Logger logInfo = LoggerFactory.getLogger((SeenQuestionsController.class));

    private final UserServiceInterface userService;

    private final QuestionServiceInterface questionService;

    /**
     * Constructs the SeenQuestionsController with the provided user service implementation.
     *
     * @param userService     The user service implementation.
     * @param questionService The question service implementation.
     */
    public SeenQuestionsController(UserServiceInterface userService, QuestionServiceInterface questionService) {
        this.userService = userService;
        this.questionService = questionService;
    }

    /**
     * Handles the GET request for the login page.
     *
     * @param model The model to be populated for the view.
     * @return The name of the login view.
     */
    @GetMapping("/seenQuestions")
    public String LoginGet(Model model, HttpSession session) {
        User currentUser = ensureLoggedIn(session);
        if (currentUser == null) {
            return "redirect:/";
        }

        Question currentQuestion = questionService.getSeenQuestion(currentUser,
                (Question) session.getAttribute("lastQuestion"),
                (Boolean) session.getAttribute("nextOrPrevious"));
        logInfo.debug("Question in list: "+currentUser.getSeenQuestions().contains(currentQuestion)+"\n index of question "+currentUser.getSeenQuestions().indexOf(currentQuestion)+"\n index of last question? "+currentUser.getSeenQuestions().indexOf((Question) session.getAttribute("lastQuestion"))+"\n last question exists? "+((Question) session.getAttribute("lastQuestion")));
        if (currentQuestion != null) {
            session.setAttribute("lastQuestion", currentQuestion);
            model.addAttribute("optionA", currentQuestion.getOptionA());
            model.addAttribute("optionB", currentQuestion.getOptionB());
        }
        else {
            model.addAttribute("optionA", "You have not seen any questions yet");
            model.addAttribute("optionB", "look through some questions and return");
        }
        return "/SeenQuestions";
    }

    @PostMapping(value = "/seenQuestions", params="direction=next")
    public String nextQuestion(HttpSession session) {
        session.setAttribute("nextOrPrevious", true);
        return "redirect:/seenQuestions";
    }

    @PostMapping(value = "/seenQuestions", params="direction=previous")
    public String previousQuestion(HttpSession session) {
        session.setAttribute("nextOrPrevious", false);
        return "redirect:/seenQuestions";
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
