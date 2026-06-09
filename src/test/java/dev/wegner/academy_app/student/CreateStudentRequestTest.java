package dev.wegner.academy_app.student;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CreateStudentRequestTest {

    @Test
    void shouldCreateRequest() {

        var request = new CreateStudentRequest("Stefan", "Wegner", "Stefan@Wegner.com");

        assertThat(request.firstName())
                .isEqualTo("Stefan");

        assertThat(request.lastName())
                .isEqualTo("Wegner");

        assertThat(request.email())
                .isEqualTo("Stefan@Wegner.com");
    }
}
