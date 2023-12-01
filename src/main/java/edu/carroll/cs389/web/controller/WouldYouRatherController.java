package edu.carroll.cs389.web.controller;

import edu.carroll.cs389.jpa.model.Question;
import edu.carroll.cs389.jpa.model.User;
import edu.carroll.cs389.service.QuestionServiceImpl;
import edu.carroll.cs389.service.QuestionServiceInterface;
import edu.carroll.cs389.web.form.WouldYouRatherForm;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller responsible for handling the operations of the "Would You Rather" game.
 */
@Controller
public class WouldYouRatherController {

    private final QuestionServiceInterface questionService;

    /**
     * Constructs a new instance of {@code WouldYouRatherController}.
     *
     * @param questionService The question service implementation.
     */
    public WouldYouRatherController(QuestionServiceInterface questionService) {
        this.questionService = questionService;
    }

    /**
     * Handles GET requests to the game's entry page.
     *
     * This method is invoked when a user navigates to the "Would You Rather" game's entry page.
     * It adds an empty {@link WouldYouRatherForm} object to the model to capture the user's game options.
     *
     * @param model The {@link Model} object used to pass data to the view.
     * @return The name of the game's entry view.
     */
    @GetMapping("/entry")
    public String optionsGet(Model model) {
        model.addAttribute("wouldYouRatherForm", new WouldYouRatherForm());
        if (model.getAttribute("showPopUp") == null || model.getAttribute("AddSuccess") == null) {
            model.addAttribute("AddSuccess", true);
            model.addAttribute("showPopUp", false);
        }
        return "WouldYouRatherEntry";
    }

    @PostMapping(value = "/entry", params = "FeedbackClose=success")
    public String closePopUpSuccess(RedirectAttributes attrs) {
        attrs.addFlashAttribute("showPopUp",false);
        return "redirect:/entry";
    }

    @PostMapping(value = "/entry", params = "FeedbackClose=failure")
    public String closePopUpFailure(RedirectAttributes attrs) {
        attrs.addFlashAttribute("showPopUp",false);
        return "redirect:/entry";
    }

    /**
     * Handles POST requests for submitting game options.
     *
     * This method is invoked when a user submits their game options. It validates the input,
     * creates a new {@link Question} object with the provided options, and adds it to the question repository.
     * If there are validation errors, it returns the user to the entry page to correct their input.
     *
     * @param wouldYouRatherForm The form object containing the user's game options.
     * @param result             The {@link BindingResult} object used to report validation errors.
     * @param attrs              The {@link RedirectAttributes} object used to pass attributes on redirect.
     * @return The name of the next view or a redirect instruction.
     */
    @PostMapping("/entry")
    public String optionsPost(Model model, @Valid @ModelAttribute WouldYouRatherForm wouldYouRatherForm, BindingResult result, RedirectAttributes attrs) {

        if (result.hasErrors()) {
            return "WouldYouRatherEntry";
        }

        Question newEntry = new Question(wouldYouRatherForm.getOptionA(), wouldYouRatherForm.getOptionB());
        if (questionService.addQuestion(newEntry)) {
            attrs.addFlashAttribute("AddSuccess", true);
            attrs.addFlashAttribute("showPopUp", true);
        } else {
            attrs.addFlashAttribute("AddSuccess", false);
            attrs.addFlashAttribute("showPopUp", true);
        }

        return "redirect:/entry";
    }
}
