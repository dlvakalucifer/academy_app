package dev.wegner.academy_app.student;

import dev.wegner.academy_app.security.jwt.JwtAuthenticationFilter;
import dev.wegner.academy_app.security.jwt.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    private Cache cache;

    @MockitoBean
    private StudentRepository repository;

    @MockitoBean
    private StudentService service;

    @MockitoBean
    private StudentMetrics metrics;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    void setup() {
        when(cacheManager.getCache("students"))
                .thenReturn(cache);
    }

    @Test
    void shouldReturnStudents() throws Exception
    {
        var anna = Student.create("Anna", "Foo", "Anna@Foo.com");
        var tom = Student.create("Tom", "Bar", "Tom@Bar.com");

        var page = new PageImpl<>(List.of(anna, tom));

        when(service.findAll(any(Pageable.class))).thenReturn(page);

        mvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].firstName").value("Anna"))
                .andExpect(jsonPath("$.content[0].lastName").value("Foo"))
                .andExpect(jsonPath("$.content[0].email").value("Anna@Foo.com"))
                .andExpect(jsonPath("$.content[1].firstName").value("Tom"))
                .andExpect(jsonPath("$.content[1].lastName").value("Bar"))
                .andExpect(jsonPath("$.content[1].email").value("Tom@Bar.com"));
    }

    @Test
    void shouldReturnStudentById() throws Exception
    {
        var student = Student.create("Anna", "Foo", "anna@foo.com");

        when(service.findById(1L)).thenReturn(student);

        mvc.perform(get("/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Anna"))
                .andExpect(jsonPath("$.lastName").value("Foo"))
                .andExpect(jsonPath("$.email").value("anna@foo.com"));
    }

    @Test
    void shouldReturnStudentByEmail() throws Exception
    {
        var student = Student.create("Anna", "Foo", "anna@foo.com");

        when(service.findByEmail("anna@foo.com")).thenReturn(student);

        mvc.perform(get("/students/by-email").param("email", "anna@foo.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Anna"))
                .andExpect(jsonPath("$.lastName").value("Foo"))
                .andExpect(jsonPath("$.email").value("anna@foo.com"));
    }

    @Test
    void shouldCreateStudent() throws Exception
    {
        var student = Student.create("Anna", "Foo", "anna@foo.com");
        var request = new CreateStudentRequest("Anna", "Foo", "anna@foo.com");

        when(service.create(request)).thenReturn(student);

        mvc.perform(post("/students").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "firstName":"Anna",
                                  "lastName":"Foo",
                                  "email":"anna@foo.com"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Anna"))
                .andExpect(jsonPath("$.lastName").value("Foo"))
                .andExpect(jsonPath("$.email").value("anna@foo.com"));

        verify(metrics).studentCreated();
    }

    @Test
    void shouldDeleteStudent() throws Exception
    {
        mvc.perform(delete("/students/1"))
                .andExpect(status().isNoContent());

        verify(service).delete(1L);
    }

    @Test
    void shouldRejectInvalidStudent() throws Exception
    {
        mvc.perform(post("/students").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "firstName":"",
                                  "lastName":"",
                                  "email":"not-an-email"
                                }
                                """))
                .andExpect(status().isBadRequest());
    }
}