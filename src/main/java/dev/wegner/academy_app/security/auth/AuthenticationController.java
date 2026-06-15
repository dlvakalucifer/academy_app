package dev.wegner.academy_app.security.auth;

import dev.wegner.academy_app.logging.LogCategories;
import dev.wegner.academy_app.security.jwt.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController
{

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthenticationController( AuthenticationManager authenticationManager, JwtService jwtService )
    {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public LoginResponse login( @RequestBody LoginRequest request )
    {
        try
        {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));

            String token = jwtService.generateToken(request.username());
            LogCategories.SECURITY.info("Token built successfully for user: {}", request.username());

            return new LoginResponse(token);
        } catch (BadCredentialsException e)
        {
            LogCategories.SECURITY.info("Bad credentials {}", request.username());
            throw new RuntimeException("Bad credentials", e);
        }
    }
}