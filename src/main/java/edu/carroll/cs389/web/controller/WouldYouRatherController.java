package edu.carroll.cs389.web.controller;

import edu.carroll.cs389.web.form.WouldYouRatherForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class WouldYouRatherController {

    @GetMapping("/wouldYouRatherEntry")
    public String optionsGet(Model model) {
        model.addAttribute("wouldYouRatherForm", new WouldYouRatherForm());
        return "WouldYouRatherEntry";
    }

    @PostMapping("/wouldYouRatherEntry")
    public String optionsPost(@ModelAttribute WouldYouRatherForm wouldYouRatherForm, Model model) {
        model.addAttribute("wouldYouRatherForm", wouldYouRatherForm);

        return "DisplayOptions";
    }

}