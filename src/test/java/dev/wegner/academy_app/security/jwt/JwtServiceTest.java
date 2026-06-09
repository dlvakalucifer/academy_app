package dev.wegner.academy_app.security.jwt;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JwtServiceTest {

    @Test
    void shouldGenerateToken() {

        JwtProperties properties = new JwtProperties("academy-super-secret-key-for-development-only", 60);

        JwtService service = new JwtService(properties);

        String token = service.generateToken("stefan");

        assertThat(token).isNotBlank();
    }

    @Test
    void shouldExtractUsername() {

        JwtProperties properties = new JwtProperties("academy-super-secret-key-for-development-only", 60);

        JwtService service = new JwtService(properties);

        String token = service.generateToken("stefan");

        assertThat(service.extractUsername(token)).isEqualTo("stefan");
    }

    @Test
    void shouldValidateToken() {

        JwtProperties properties = new JwtProperties("academy-super-secret-key-for-development-only", 60);

        JwtService service = new JwtService(properties);

        String token = service.generateToken("stefan");

        assertThat(service.isValid(token)).isTrue();
    }
}
