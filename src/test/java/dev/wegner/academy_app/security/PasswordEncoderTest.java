package dev.wegner.academy_app.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

class PasswordEncoderTest
{

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    void shouldEncodePassword()
    {
        var password = "academy123";
        var hash = passwordEncoder.encode(password);

        assertThat(hash).isNotBlank();
        assertThat(hash).isNotEqualTo(password);
    }

    @Test
    void shouldVerifyPassword()
    {
        var password = "academy123";
        var hash = passwordEncoder.encode(password);

        boolean matches = passwordEncoder.matches(password, hash);
        assertThat(matches).isTrue();
    }

    @Test
    void shouldRejectWrongPassword()
    {
        var hash = passwordEncoder.encode("academy123");
        boolean matches = passwordEncoder.matches("wrongPassword", hash);

        assertThat(matches).isFalse();
    }

    @Test
    void shouldGenerateDifferentHashes()
    {
        var password = "academy123";
        var firstHash = passwordEncoder.encode(password);
        var secondHash = passwordEncoder.encode(password);

        assertThat(firstHash).isNotEqualTo(secondHash);
    }

    @DisplayName("DB-Seed Helper for Hashes")
    @Test
    void printHash()
    {
        System.out.println(passwordEncoder.encode("academy"));
    }
}