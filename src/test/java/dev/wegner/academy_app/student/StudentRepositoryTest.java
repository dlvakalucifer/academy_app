package dev.wegner.academy_app.student;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Testcontainers
class StudentRepositoryTest
{
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:17");
    
    @MockitoBean
    private CacheManager cacheManager;

    @Autowired
    private StudentRepository repository;

    @Test
    void shouldSaveStudent()
    {
        var student = repository.save(Student.create("Stefan", "Wegner", "Stefan@Wegner.com"));

        assertThat(student.getId()).isNotNull();
        assertThat(student.getFirstName()).isEqualTo("Stefan");
        assertThat(student.getLastName()).isEqualTo("Wegner");
        assertThat(student.getEmail()).isEqualTo("Stefan@Wegner.com");
    }

    @Test
    void shouldFindAllStudents()
    {
        repository.save(Student.create("Anna", "Foo", "Anna@Foo.com"));
        repository.save(Student.create("Tom", "Bar", "Tom@Bar.com"));

        var students = repository.findAll();

        assertThat(students).extracting(Student::getFirstName)
                .contains("Anna", "Tom");
        assertThat(students).extracting(Student::getLastName)
                .contains("Foo", "Bar");
        assertThat(students).extracting(Student::getEmail)
                .contains("Anna@Foo.com", "Tom@Bar.com");
    }

    @Test
    void shouldFindStudentById()
    {
        var saved = repository.save(
                Student.create("Stefan", "Wegner", "Stefan@Wegner.com"));

        var student = repository.findById(saved.getId());

        assertThat(student).isPresent();
        assertThat(student.get().getEmail())
                .isEqualTo("Stefan@Wegner.com");
    }

    @Test
    void shouldFindStudentByEmail()
    {
        repository.save(Student.create("Stefan", "Wegner", "Stefan@Wegner.com"));

        var student = repository.findByEmail("Stefan@Wegner.com");

        assertThat(student).isPresent();
        assertThat(student.get()
                .getFirstName()).isEqualTo("Stefan");
        assertThat(student.get()
                .getLastName()).isEqualTo("Wegner");
        assertThat(student.get()
                .getEmail()).isEqualTo("Stefan@Wegner.com");
    }

    @Test
    void shouldDeleteStudent()
    {
        var saved = repository.save(
                Student.create("Stefan", "Wegner", "Stefan@Wegner.com"));

        repository.deleteById(saved.getId());

        assertThat(repository.findById(saved.getId()))
                .isEmpty();
    }

    @Test
    void shouldReturnStudentsPage()
    {
        repository.save(Student.create("Anna", "Foo", "anna@foo.com"));
        repository.save(Student.create("Tom", "Bar", "tom@bar.com"));
        repository.save(Student.create("Max", "Baz", "max@baz.com"));

        var page = repository.findAll(PageRequest.of(0, 2));

        assertThat(page.getContent())
                .hasSize(2);

        assertThat(page.getTotalElements())
                .isEqualTo(3);
    }

    @Test
    void shouldReturnSortedStudents()
    {
        repository.save(Student.create("Tom", "Bar", "tom@bar.com"));
        repository.save(Student.create("Anna", "Foo", "anna@foo.com"));

        var students = repository.findAll(
                Sort.by("firstName"));

        assertThat(students)
                .extracting(Student::getFirstName)
                .containsExactly("Anna", "Tom");
    }
}