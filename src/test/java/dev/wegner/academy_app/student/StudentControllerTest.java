package dev.wegner.academy_app.student;

import dev.wegner.academy_app.security.jwt.JwtAuthenticationFilter;
import dev.wegner.academy_app.security.jwt.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
@AutoConfigureMockMvc(addFilters = false)
class StudentControllerTest
{
    @MockitoBean
    JwtService jwtService;

    @MockitoBean
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    private CacheManager cacheManager;

    @MockitoBean
    private StudentRepository repository;

    @MockitoBean
    private StudentService service;

    @Autowired
    private MockMvc mvc;

    @Autowired
    RequestMappingHandlerMapping mappings;

    @Test
    void shouldReturnStudents() throws Exception
    {
        when(service.findAll()).thenReturn(List.of(Student.create("Anna", "Foo", "Anna@Foo.com"), Student.create("Tom", "Bar", "Tom@Bar.com")));

        mvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Anna"))
                .andExpect(jsonPath("$[0].lastName").value("Foo"))
                .andExpect(jsonPath("$[0].email").value("Anna@Foo.com"))
                .andExpect(jsonPath("$[1].firstName").value("Tom"))
                .andExpect(jsonPath("$[1].lastName").value("Bar"))
                .andExpect(jsonPath("$[1].email").value("Tom@Bar.com"));
    }
}