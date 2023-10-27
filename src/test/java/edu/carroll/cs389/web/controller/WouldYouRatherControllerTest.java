package edu.carroll.cs389.web.controller;

import edu.carroll.cs389.service.QuestionServiceInterface;
import org.springframework.context.annotation.Import;
import edu.carroll.cs389.web.form.WouldYouRatherForm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WouldYouRatherController.class)
class WouldYouRatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuestionServiceInterface questionService;

    @Test
    void optionsGet_ShouldShowEntryPage() throws Exception {
        mockMvc.perform(get("/wouldYouRatherEntry"))
                .andExpect(status().isOk())
                .andExpect(view().name("WouldYouRatherEntry"))
                .andExpect(model().attributeExists("wouldYouRatherForm"));
    }

    @Test
    void optionsPost_ValidData_ShouldRedirect() throws Exception {
        mockMvc.perform(post("/wouldYouRatherEntry")
                        .flashAttr("wouldYouRatherForm", new WouldYouRatherForm()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wouldYouRatherEntry"));

        // Here, you should also verify that the questionService.addQuestion method was called.
        verify(questionService).addQuestion(any());
    }

    @Test
    void optionsPost_InvalidData_ShouldShowEntryPage() throws Exception {
        // Sending an empty form to trigger validation errors
        mockMvc.perform(post("/wouldYouRatherEntry"))
                .andExpect(status().isOk())
                .andExpect(view().name("WouldYouRatherEntry"))
                .andExpect(model().attributeExists("wouldYouRatherForm"))
                .andExpect(model().hasErrors());

        // Here, you should also verify that the questionService.addQuestion method was NOT called.
    }
}
