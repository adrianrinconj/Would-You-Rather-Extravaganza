package edu.carroll.cs389.web.controller;

import edu.carroll.cs389.service.UserServiceInterface;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RegisterController.class)
class RegisterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceInterface userService;

    @Test
    void registerPost_UniqueUser_ShouldRedirectToHome() throws Exception {
        when(userService.uniqueUser(any())).thenReturn(true);
        mockMvc.perform(post("/register")
                        .param("username", "testuser")
                        .param("rawPassword", "password"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
        verify(userService).addUser(any());
    }

    @Test
    void registerPost_NonUniqueUser_ShouldShowRegisterPage() throws Exception {
        when(userService.uniqueUser(any())).thenReturn(false);
        mockMvc.perform(post("/register")
                        .param("username", "testuser")
                        .param("rawPassword", "password"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("userExists"))
                .andExpect(model().attribute("userExists", true));
    }
}
