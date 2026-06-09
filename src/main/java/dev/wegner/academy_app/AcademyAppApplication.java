package dev.wegner.academy_app;

import dev.wegner.academy_app.security.jwt.JwtProperties;
import dev.wegner.academy_app.storage.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

@EnableConfigurationProperties({ StorageProperties.class, JwtProperties.class })
@EnableCaching
@SpringBootApplication
public class AcademyAppApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(AcademyAppApplication.class, args);
    }
}

