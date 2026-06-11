package dev.wegner.academy_app.security;


import dev.wegner.academy_app.security.jwt.JwtAuthenticationFilter;
import dev.wegner.academy_app.security.jwt.JwtProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(JwtProperties.class)
public class SecurityConfiguration
{

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfiguration( JwtAuthenticationFilter jwtAuthenticationFilter )
    {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain( HttpSecurity http )
    {
        return http.csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth.requestMatchers("/auth/login", "/actuator/health", "/actuator/prometheus", "/info")
                        .permitAll()
                        .requestMatchers("/students", "/students/**")
                        .hasAnyRole("ADMIN", "LECTURER")
                        .requestMatchers("/storage", "/storage/**")
                        .hasAnyRole("ADMIN", "LECTURER")
                        .anyRequest()
                        .authenticated())
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    AuthenticationManager authenticationManager( AuthenticationConfiguration configuration )
    {
        return configuration.getAuthenticationManager();
    }
}
