package dev.wegner.academy_app.student;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StudentTest
{

    @Test
    void shouldCreateStudent()
    {
        var student = Student.create("Stefan", "Wegner", "Stefan@Wegner.com");

        assertThat(student.getFirstName()).isEqualTo("Stefan");
        assertThat(student.getLastName()).isEqualTo("Wegner");
        assertThat(student.getEmail()).isEqualTo("Stefan@Wegner.com");
    }
}