package edu.carroll.cs389.web.controller;


import edu.carroll.cs389.jpa.model.Question;
import edu.carroll.cs389.service.QuestionService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import edu.carroll.cs389.web.form.WouldYouRatherForm;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class WouldYouRatherController {



    private final QuestionService questionService;

    public WouldYouRatherController(QuestionService questionService) {
        this.questionService = questionService;
    }


    @GetMapping("/")

    public String optionsGet(Model model) {
        model.addAttribute("wouldYouRatherForm", new WouldYouRatherForm());
        return "WouldYouRatherEntry";
    }

    @PostMapping("/")
    public String optionsPost(@Valid @ModelAttribute WouldYouRatherForm wouldYouRatherForm, BindingResult result, RedirectAttributes attrs ) {

        if (result.hasErrors()) {
            return "WouldYouRatherEntry";
        }

        Question newEntry = new Question(wouldYouRatherForm.getOptionA(), wouldYouRatherForm.getOptionB());
        questionService.addQuestion(newEntry);
        return "redirect:/DisplayOptions";
    }
}