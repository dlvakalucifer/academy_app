package dev.wegner.academy_app.student;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
class StudentControllerTest
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