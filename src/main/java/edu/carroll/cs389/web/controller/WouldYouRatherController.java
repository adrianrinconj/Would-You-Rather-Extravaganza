package edu.carroll.cs389.web.controller;

import edu.carroll.cs389.jpa.model.Question;
import edu.carroll.cs389.service.QuestionServiceImpl;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import edu.carroll.cs389.web.form.WouldYouRatherForm;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller responsible for handling "Would You Rather" game operations.
 */
@Controller
public class WouldYouRatherController {

    private final QuestionServiceImpl questionService;

    /**
     * Constructs the WouldYouRatherController with the provided question service implementation.
     *
     * @param questionService The question service implementation.
     */
    public WouldYouRatherController(QuestionServiceImpl questionService) {
        this.questionService = questionService;
    }

    /**
     * Handles the GET request for the game's entry page.
     *
     * @param model The model to be populated for the view.
     * @return The name of the game's entry view.
     */
    @GetMapping("/wouldYouRatherEntry")
    public String optionsGet(Model model) {
        model.addAttribute("wouldYouRatherForm", new WouldYouRatherForm());
        return "WouldYouRatherEntry";
    }

    /**
     * Handles the POST request for submitting game options.
     *
     * @param wouldYouRatherForm The form containing game options.
     * @param result             The binding result containing validation errors.
     * @param attrs              The redirect attributes.
     * @return The name of the next view or a redirect instruction.
     */
    @PostMapping("/wouldYouRatherEntry")
    public String optionsPost(@Valid @ModelAttribute WouldYouRatherForm wouldYouRatherForm, BindingResult result, RedirectAttributes attrs) {

        if (result.hasErrors()) {
            return "WouldYouRatherEntry";
        }

        Question newEntry = new Question(wouldYouRatherForm.getOptionA(), wouldYouRatherForm.getOptionB());
        questionService.addQuestion(newEntry);

        return "redirect:/WouldYouRatherEntry";
    }
}
