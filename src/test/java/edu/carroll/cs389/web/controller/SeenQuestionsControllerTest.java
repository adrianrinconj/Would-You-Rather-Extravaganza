package edu.carroll.cs389.web.controller;

import edu.carroll.cs389.jpa.model.Question;
import edu.carroll.cs389.jpa.model.User;
import edu.carroll.cs389.service.QuestionServiceInterface;
import edu.carroll.cs389.service.UserServiceInterface;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SeenQuestionsController.class)
class SeenQuestionsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceInterface userService;

    @MockBean
    private QuestionServiceInterface questionService;

    @Test
    void seenQuestionsGet_UserLoggedIn_ShouldShowSeenQuestions() throws Exception {
        User user = new User("user", "password");
        List<Question> seenQuestions = new ArrayList<>();
        seenQuestions.add(new Question("Option A", "Option B"));

        when(userService.userLookupID(1L)).thenReturn(user);

        HttpSession session = new MockHttpSession();
        session.setAttribute("loggedUserID", 1L);

        mockMvc.perform(get("/seenQuestions").session((MockHttpSession) session))
                .andExpect(status().isOk())
                .andExpect(view().name("seenQuestions"))
                .andExpect(model().attributeExists("seenQuestions"))
                .andExpect(model().attribute("seenQuestions", hasProperty("size", is(1))));
    }

    @Test
    void seenQuestionsGet_UserNotLoggedIn_ShouldRedirectToHome() throws Exception {
        mockMvc.perform(get("/seenQuestions"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
}
