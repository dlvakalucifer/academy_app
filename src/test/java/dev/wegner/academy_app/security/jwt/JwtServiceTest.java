package dev.wegner.academy_app.security.jwt;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JwtServiceTest
{
    @Test
    void shouldGenerateToken()
    {
        var properties = new JwtProperties("academy-super-secret-key-for-development-only", 60);
        var service = new JwtService(properties);
        var token = service.generateToken("stefan");

        assertThat(token).isNotBlank();
    }

    @Test
    void shouldExtractUsername()
    {
        var properties = new JwtProperties("academy-super-secret-key-for-development-only", 60);
        var service = new JwtService(properties);
        var token = service.generateToken("stefan");

        assertThat(service.extractUsername(token)).isEqualTo("stefan");
    }

    @Test
    void shouldValidateToken()
    {
        var properties = new JwtProperties("academy-super-secret-key-for-development-only", 60);
        var service = new JwtService(properties);
        var token = service.generateToken("stefan");

        assertThat(service.isValid(token)).isTrue();
    }
}
