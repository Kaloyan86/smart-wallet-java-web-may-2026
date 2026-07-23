package app.web.user;

import app.model.dto.user.UserDto;
import app.service.user.AuthenticationUserDetails;
import app.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.UUID;

import static app.util.user.UserFactory.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@ActiveProfiles("test")
@WebMvcTest(UserController.class)
public class UserControllerApiTest {

    @MockitoBean
    private UserService userService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void getAllUsers_whenUserIsAdmin_thenReturnStatus200andUsersView() throws Exception {

        //Given
        when(userService.getAllUsers()).thenReturn(
                List.of(getUserDto(), getUserDto(), getUserDto()));

        List<UserDto> users = userService.getAllUsers();
        MockHttpServletRequestBuilder request = get("/users").with(user(getAdminUser()));

        //When & Then

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("users"))
                .andExpect(MockMvcResultMatchers.model().attribute("users", users));
    }

    @Test
    public void getProfile_shouldReturnProfileView_andStatus200() throws Exception {

        //Given
        UserDto userDto = getUserDto();
        when(userService.getById(any())).thenReturn(userDto);

        MockHttpServletRequestBuilder request = get("/users/{id}/profile",  userDto.getId())
                .with(user(getUserPrincipal()));

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("profile-menu"))
                .andExpect(MockMvcResultMatchers.model().attribute("user", userDto));
    }

    @Test
    public void putSwitchProfile_shouldReturnStatus3xx_andRedirectToUsers() throws Exception {

        AuthenticationUserDetails userPrincipal = getUserPrincipal();
        MockHttpServletRequestBuilder request = put("/users/{id}/status",  userPrincipal.getId())
                .with(user(getUserPrincipal()))
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/users"));
    }
}
