package dev.wegner.academy_app.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.wegner.academy_app.security.auth.LoginRequest;
import dev.wegner.academy_app.user_management.AcademyRole;
import dev.wegner.academy_app.user_management.AcademyUser;
import dev.wegner.academy_app.user_management.AcademyUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SecurityIntegrationTest
{
    @Autowired
    private AcademyUserRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    MockMvc mvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup()
    {
        repository.deleteAll();
        repository.save(AcademyUser.create("admin", encoder.encode("academy"), true, AcademyRole.ADMIN));
    }

    @Test
    void shouldAllowBasicAuth() throws Exception
    {
        mvc.perform(get("/students").with(httpBasic("admin", "academy")))
                .andExpect(status().isOk());
    }

    @Test
    void shouldAuthenticateWithJwt() throws Exception
    {
        var request = new LoginRequest("admin", "academy");
        var login = mvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        var token = objectMapper.readTree(login.getResponse()
                        .getContentAsString())
                .get("token")
                .asText();

        mvc.perform(get("/students").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void shouldRejectWrongPassword() throws Exception
    {
        mvc.perform(get("/students").with(httpBasic("admin", "wrong")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldRejectManipulatedJwt() throws Exception
    {
        mvc.perform(get("/students").header("Authorization", "Bearer invalid-token"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldRejectMissingAuthentication() throws Exception
    {
        mvc.perform(get("/students"))
                .andExpect(status().isUnauthorized());
    }
}
