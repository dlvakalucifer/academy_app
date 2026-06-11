package dev.wegner.academy_app.info;

import dev.wegner.academy_app.security.jwt.JwtAuthenticationFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AcademyInfoController.class)
@AutoConfigureMockMvc(addFilters = false)
class AcademyInfoControllerTest
{
    @MockitoBean
    private AcademyInfoProvider academyInfoProvider;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    private CacheManager cacheManager;

    @Autowired
    private MockMvc mvc;

    @Test
    void shouldReturnAcademyInfo() throws Exception
    {
        when(academyInfoProvider.getAcademyInfo()).thenReturn(new AcademyInfo("academy_app", "1.0.0", "21", "4", "PostgreSQL", "MinIO", "Redis"));

        mvc.perform(get("/info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.application").value("academy_app"))
                .andExpect(jsonPath("$.version").value("1.0.0"))
                .andExpect(jsonPath("$.database").value("PostgreSQL"))
                .andExpect(jsonPath("$.storage").value("MinIO"))
                .andExpect(jsonPath("$.cache").value("Redis"));
    }
}