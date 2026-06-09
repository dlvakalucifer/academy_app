package dev.wegner.academy_app.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("academy.storage")
public record StorageProperties(String endpoint, String accessKey, String secretKey, String bucket)
{
}
