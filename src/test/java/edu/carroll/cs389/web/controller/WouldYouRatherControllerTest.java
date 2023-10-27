package edu.carroll.cs389.web.controller;

//import static org.hamcrest.Matchers.equalTo;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import edu.carroll.cs389.web.controller.WouldYouRatherController;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//@SpringBootTest
//@WebMvcTest(WouldYouRatherController.class)
//public class WouldYouRatherControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//
//    // this unit test checks if the @GetMapping url is correct
//    @Test
//    public void WouldYouRatherControllerTest() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/your-endpoint"))
//                .andExpect(MockMvcResultMatchers.status().isOk)
//                .andExpect(MockMvcResultMatchers.content().string("Expected Response"))
//    }
//}

import edu.carroll.cs389.jpa.model.Question;
import edu.carroll.cs389.service.QuestionServiceImpl;
import edu.carroll.cs389.web.form.WouldYouRatherForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class WouldYouRatherControllerTest {

    @Mock
    private QuestionServiceImpl questionService;

    private WouldYouRatherController controller;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        controller = new WouldYouRatherController(questionService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testOptionsGet() throws Exception {
        mockMvc.perform(get("/Entry"))
                .andExpect(status().isOk())
                .andExpect(view().name("WouldYouRatherEntry"))
                .andExpect(model().attributeExists("wouldYouRatherForm"));
    }

//    @Test
//    public void testOptionsPost() throws Exception {
//        WouldYouRatherForm form = new WouldYouRatherForm();
//        form.setOptionA("Option A");
//        form.setOptionB("Option B");
//
//        mockMvc.perform(post("/Entry")
//                        .flashAttr("wouldYouRatherForm", form))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/DisplayOptions"))
//                .andExpect(MockMvcResultMatchers.flash().attribute("message", "Successfully added question"));
//
//        // Verify that the questionService's addQuestion method was called with the correct parameters
//        Question expectedQuestion = new Question("Option A", "Option B");
////        when(questionService.addQuestion(expectedQuestion)).thenReturn(expectedQuestion);

}