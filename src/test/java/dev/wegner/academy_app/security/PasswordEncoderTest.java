package dev.wegner.academy_app.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

class PasswordEncoderTest {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    void shouldEncodePassword() {

        String password = "academy123";

        String hash = passwordEncoder.encode(password);

        assertThat(hash).isNotBlank();

        assertThat(hash).isNotEqualTo(password);
    }

    @Test
    void shouldVerifyPassword() {

        String password = "academy123";

        String hash = passwordEncoder.encode(password);

        boolean matches = passwordEncoder.matches(password, hash);

        assertThat(matches).isTrue();
    }

    @Test
    void shouldRejectWrongPassword() {

        String hash = passwordEncoder.encode("academy123");

        boolean matches = passwordEncoder.matches("wrongPassword", hash);

        assertThat(matches).isFalse();
    }

    @Test
    void shouldGenerateDifferentHashes() {

        String password = "academy123";

        String hash1 = passwordEncoder.encode(password);

        String hash2 = passwordEncoder.encode(password);

        assertThat(hash1).isNotEqualTo(hash2);
    }

    @DisplayName("DB-Seed Helper for Hashes")
    @Test
    void printHash() {

        System.out.println(passwordEncoder.encode("academy"));
    }
}