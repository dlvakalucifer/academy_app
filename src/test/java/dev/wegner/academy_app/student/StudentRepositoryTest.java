package dev.wegner.academy_app.student;

import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.NONE
)
class StudentRepositoryTest {

    @MockitoBean
    private CacheManager cacheManager;

    @Autowired
    private StudentRepository repository;

    @Test
    void shouldSaveStudent() {

        var student = repository.save(
                Student.create("Stefan", "Wegner", "Stefan@Wegner.com"));

        assertThat(student.getId())
                .isNotNull();

        assertThat(student.getFirstName())
                .isEqualTo("Stefan");

        assertThat(student.getLastName())
                .isEqualTo("Wegner");

        assertThat(student.getEmail())
                .isEqualTo("Stefan@Wegner.com");
    }

    @Test
    void shouldFindAllStudents() {
        repository.save(Student.create("Anna", "Foo", "Anna@Foo.com"));
        repository.save(Student.create("Tom", "Bar", "Tom@Bar.com"));

        var students = repository.findAll();

        assertThat(students)
                .extracting(Student::getFirstName)
                .contains("Anna", "Tom");

        assertThat(students)
                .extracting(Student::getLastName)
                .contains("Foo", "Bar");

        assertThat(students)
                .extracting(Student::getEmail)
                .contains("Anna@Foo.com", "Tom@Bar.com");
    }

    @Test
    void shouldFindStudentByEmail() {

        repository.save(
                Student.create(
                        "Stefan",
                        "Wegner",
                        "Stefan@Wegner.com"
                ));

        var student =
                repository.findByEmail("Stefan@Wegner.com");

        assertThat(student)
                .isPresent();

        assertThat(student.get().getFirstName())
                .isEqualTo("Stefan");

        assertThat(student.get().getLastName())
                .isEqualTo("Wegner");

        assertThat(student.get().getEmail())
                .isEqualTo("Stefan@Wegner.com");
    }
}