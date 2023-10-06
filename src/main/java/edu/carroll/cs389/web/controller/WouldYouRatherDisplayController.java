package edu.carroll.cs389.web.controller;
import edu.carroll.cs389.jpa.model.Question;
import edu.carroll.cs389.service.QuestionService;
import edu.carroll.cs389.web.form.WouldYouRatherForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WouldYouRatherDisplayController {



    private final QuestionService questionService;

    public WouldYouRatherDisplayController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/DisplayOptions")
    public String optionsGet(Model model) {
        model.addAttribute("wouldYouRatherForm", new WouldYouRatherForm());
        Question randomQuestion = questionService.randomQuestion();


        if(randomQuestion != null) {
            model.addAttribute("optionA", randomQuestion.getOptionA());
            model.addAttribute("optionB", randomQuestion.getOptionB());
        }
        else{
            model.addAttribute("optionA", "no option");
            model.addAttribute("optionB", "no option");
        }

        return "/DisplayOptions";
    }

}