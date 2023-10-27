package edu.carroll.cs389.web.controller;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import edu.carroll.cs389.service.QuestionServiceInterface;
import edu.carroll.cs389.service.UserServiceInterface;
import edu.carroll.cs389.jpa.model.User;
import edu.carroll.cs389.jpa.model.Question;

@WebMvcTest(WouldYouRatherDisplayController.class)
public class WouldYouRatherDisplayControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuestionServiceInterface questionService;

    @MockBean
    private UserServiceInterface userService;

    // Voting for Option A Tests

    @Test
    void voteForOptionA_UserNotLoggedIn_ShouldRedirect() throws Exception {
        mockMvc.perform(post("/DisplayOptions")
                        .param("vote", "optionA")
                        .param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    void voteForOptionA_UserLoggedInAndValidQuestion_ShouldRedirectToResults() throws Exception {
        User user = new User("testUser", "password");
        user.setId(1L);
        Question question = new Question("Option A", "Option B");
        question.setId(1L);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("loggedUserID", 1L);

        when(userService.userLookupID(1L)).thenReturn(user);
        when(questionService.questionIdLookup(1L)).thenReturn(question);

        mockMvc.perform(post("/DisplayOptions")
                        .param("vote", "optionA")
                        .param("id", "1")
                        .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/results"));
    }

    // Voting for Option B Tests

    @Test
    void voteForOptionB_UserNotLoggedIn_ShouldRedirect() throws Exception {
        mockMvc.perform(post("/DisplayOptions")
                        .param("vote", "optionB")
                        .param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    void voteForOptionB_UserLoggedInAndValidQuestion_ShouldRedirectToResults() throws Exception {
        User user = new User("testUser", "password");
        user.setId(1L);
        Question question = new Question("Option A", "Option B");
        question.setId(1L);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("loggedUserID", 1L);

        when(userService.userLookupID(1L)).thenReturn(user);
        when(questionService.questionIdLookup(1L)).thenReturn(question);

        mockMvc.perform(post("/DisplayOptions")
                        .param("vote", "optionB")
                        .param("id", "1")
                        .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/results"));
    }

    // Displaying Results Tests

    @Test
    void showResults_UserNotLoggedIn_ShouldRedirect() throws Exception {
        mockMvc.perform(get("/results"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    void showResults_UserLoggedInAndValidQuestion_ShouldShowResults() throws Exception {
        User user = new User("testUser", "password");
        user.setId(1L);
        Question question = new Question("Option A", "Option B");
        question.setId(1L);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("loggedUserID", 1L);
        session.setAttribute("lastQuestionID", 1L);

        when(userService.userLookupID(1L)).thenReturn(user);
        when(questionService.questionIdLookup(1L)).thenReturn(question);

        mockMvc.perform(get("/results").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("Results"))
                .andExpect(model().attributeExists("AVotes", "BVotes"));
    }
}
