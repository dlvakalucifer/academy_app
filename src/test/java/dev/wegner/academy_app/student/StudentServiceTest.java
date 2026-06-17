package dev.wegner.academy_app.student;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest
{
    @Mock
    private StudentRepository repository;

    @InjectMocks
    private StudentService service;

    @Test
    void shouldLoadStudents()
    {
        var pageable = PageRequest.of(0, 10);

        when(repository.findAll(pageable)).thenReturn(Page.empty());

        service.findAll(pageable);

        verify(repository).findAll(pageable);
    }

    @Test
    void shouldFindStudentById()
    {
        long mockId = 1L;
        var student = Student.create("Anna", "Foo", "anna@test.de");

        when(repository.findById(mockId)).thenReturn(Optional.of(student));

        var result = service.findById(mockId);

        verify(repository).findById(mockId);

        assertThat(result).isEqualTo(student);
    }

    @Test
    void shouldFindStudentByEmail()
    {
        var student = Student.create("Anna", "Foo", "anna@test.de");

        when(repository.findByEmail("anna@test.de")).thenReturn(Optional.of(student));

        var result = service.findByEmail("anna@test.de");

        assertThat(result).isEqualTo(student);

        verify(repository).findByEmail("anna@test.de");
    }

    @Test
    void shouldCreateStudent()
    {
        var request = new CreateStudentRequest("Anna", "Foo", "anna@test.de");
        var student = Student.create("Anna", "Foo", "anna@test.de");

        when(repository.save(any(Student.class))).thenReturn(student);

        var created = service.create(request);

        verify(repository).save(any(Student.class));

        assertThat(created).isEqualTo(student);
    }
}
