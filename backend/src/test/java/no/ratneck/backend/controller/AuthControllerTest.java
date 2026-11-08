package no.ratneck.backend.controller;


import no.ratneck.backend.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = AuthController.class, properties = "ADMIN_PASSWORD=test")
@Import(SecurityConfig.class)
public class AuthControllerTest {


    @Autowired
    private MockMvc mvc;

    @Test
    void given_no_session_when_get_status_returns_401()throws Exception{

        mvc.perform(get("/api/auth/status"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void given_authenticated_user_when_get_status_returns_200()throws Exception{
        mvc.perform(get("/api/auth/status"))
                .andExpect(status().isOk());
    }


}
