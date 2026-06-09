package dev.wegner.academy_app.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "academy.security.jwt")
public record JwtProperties(String secret, long expirationMinutes)
{
}