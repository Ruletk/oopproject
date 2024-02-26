package kz.aitu.se2311.oopproject.auth;

import kz.aitu.se2311.oopproject.auth.dto.requests.SignUpRequest;
import kz.aitu.se2311.oopproject.auth.dto.responses.JwtResponse;
import kz.aitu.se2311.oopproject.auth.services.AuthService;
import kz.aitu.se2311.oopproject.auth.services.JwtService;
import kz.aitu.se2311.oopproject.config.JwtAuthenticationFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@Import({JwtAuthenticationFilter.class, JwtService.class})
@AutoConfigureMockMvc(addFilters = false)
public class TestUserAuthentication {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthService authService;

    private void prepareSignUpRequestAndMockResponse(String username, String email, String password) {
        SignUpRequest request = new SignUpRequest(username, email, password);
        when(authService.signUp(request)).thenReturn(new JwtResponse("mockRefreshToken", "mockAccessToken"));
    }

    @Test
    public void testUserRegistrationValid() throws Exception {
        // Подготовка данных для теста
        String username = "test";
        String email = "test@example.com";
        String password = "VeRy_sEcReT_PaSsWoRd";

        prepareSignUpRequestAndMockResponse(username, email, password);

        // Выполняем запрос на авторизацию
        ResultActions result = mvc.perform(post("/api/v1/auth/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.ALL)
                .content("""

                        {
                          "username": "test",
                          "email": "test@example.com",
                          "password": "VeRy_sEcReT_PaSsWoRd"
                        }

                        """)
        );

        // Проверяем ожидаемый результат
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists())
                .andExpect(jsonPath("$.refreshToken").exists())
                .andExpect(jsonPath("$.accessToken").value("mockAccessToken"))
                .andExpect(jsonPath("$.refreshToken").value("mockRefreshToken"));
    }

    @Test
    public void testUserRegistrationNonValid() throws Exception {
        ResultActions result = mvc.perform(post("/api/v1/auth/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.ALL)
                .content("""

                        {
                          "usernamee": "test",
                          "email": "test@example.com",
                          "password": "VeRy_sEcReT_PaSsWoRd"
                        }

                        """)
        );

        result.andExpect(status().is(400))
                .andExpect(jsonPath("$.message").value("Invalid JSON structure"));
    }

    @Test
    public void testUserRegistrationExcludeUsername() throws Exception {
        ResultActions result = mvc.perform(post("/api/v1/auth/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.ALL)
                .content("""

                        {
                          "email": "test@example.com",
                          "password": "VeRy_sEcReT_PaSsWoRd"
                        }

                        """)
        );

        result.andExpect(status().is(400))
                .andExpect(jsonPath("$.message").value("Invalid JSON structure"));
    }

    @Test
    public void testUserRegistrationExcludeEmail() throws Exception {
        ResultActions result = mvc.perform(post("/api/v1/auth/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.ALL)
                .content("""

                        {
                          "username": "test",
                          "password": "VeRy_sEcReT_PaSsWoRd"
                        }

                        """)
        );

        result.andExpect(status().is(400))
                .andExpect(jsonPath("$.message").value("Invalid JSON structure"));
    }

    @Test
    public void testUserRegistrationExcludePassword() throws Exception {
        ResultActions result = mvc.perform(post("/api/v1/auth/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.ALL)
                .content("""

                        {
                          "username": "test",
                          "email": "test@example.com"
                        }

                        """)
        );

        result.andExpect(status().is(400))
                .andExpect(jsonPath("$.message").value("Invalid JSON structure"));
    }

    @Test
    public void testUserRegistrationNoData() throws Exception {
        ResultActions result = mvc.perform(post("/api/v1/auth/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.ALL)
                .content("")
        );

        result.andExpect(status().is(400));
    }
}
