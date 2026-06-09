package dev.wegner.academy_app.student;

import dev.wegner.academy_app.security.SecurityConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(SecurityConfiguration.class)
@WebMvcTest(StudentController.class)
public class StudentControllerSecurityTest
{

    @MockitoBean
    private CacheManager cacheManager;

    @MockitoBean
    private StudentRepository repository;

    @MockitoBean
    private StudentService service;

    @Autowired
    private MockMvc mvc;

    @Test
    void shouldRejectAnonymousUser() throws Exception
    {
        mvc.perform(get("/students"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldAllowLecturer() throws Exception
    {
        mvc.perform(get("/students").with(user("lecturer").roles("LECTURER")))
                .andExpect(status().isOk());
//        mvc.perform(get("/students").with(httpBasic("lecturer", "academy"))).andExpect(status().isOk());
    }

    @Test
    void shouldRejectStudent() throws Exception
    {
        mvc.perform(get("/students").with(user("student").roles("STUDENT")))
                .andExpect(status().isForbidden());
    }
}
