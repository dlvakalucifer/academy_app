package dev.wegner.academy_app.user_management;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @Test
    void shouldCreateUser() {

        var user = AcademyUser.create("admin", "secretHash", true, AcademyRole.ADMIN);

        assertThat(user.getUsername()).isEqualTo("admin");

        assertThat(user.getPasswordHash()).isEqualTo("secretHash");

        assertThat(user.isEnabled()).isTrue();

        assertThat(user.getRole()).isEqualTo(AcademyRole.ADMIN);
    }
}