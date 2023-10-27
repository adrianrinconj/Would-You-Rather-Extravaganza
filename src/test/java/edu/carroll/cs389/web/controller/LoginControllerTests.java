package edu.carroll.cs389.web.controller;

import edu.carroll.cs389.web.controller.LoginController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import edu.carroll.cs389.jpa.model.User;
import edu.carroll.cs389.service.UserServiceInterface;
import edu.carroll.cs389.web.form.LoginForm;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LoginControllerTests {

    @InjectMocks
    private LoginController controller;

    @Mock
    private UserServiceInterface userService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testLoginGet() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("Login"))
                .andExpect(model().attributeExists("loginForm"));
    }

    @Test
    public void testLoginPostWithValidData() throws Exception {
        LoginForm form = new LoginForm();
        form.setUsername("Guest1");
        form.setRawPassword("Password");

        User user = new User("Guest1", "Password");
        when(userService.loginValidation("Guest1", "Password")).thenReturn(user);

        MockHttpSession session = new MockHttpSession();

        mockMvc.perform(post("/")
                        .flashAttr("loginForm", form)
                        .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/entry"))
                .andExpect(request().sessionAttribute("loggedUserID", user.getId()));
    }

    @Test
    public void testLoginPostWithInvalidData() throws Exception {
        LoginForm form = new LoginForm();
        form.setUsername("InvalidUser");
        form.setRawPassword("InvalidPassword");

        mockMvc.perform(post("/")
                        .flashAttr("loginForm", form))
                .andExpect(status().isOk())
                .andExpect(view().name("Login"))
                .andExpect(model().attributeExists("loginForm"))
                .andExpect(model().hasErrors());
    }
}