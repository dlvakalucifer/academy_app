package dev.wegner.academy_app.security.jwt;

import dev.wegner.academy_app.user_management.AcademyUserDetailsService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter
{

    public static final int MINUS_KEYWORD = 7;
    private final JwtService jwtService;
    private final AcademyUserDetailsService userDetailsService;

    public JwtAuthenticationFilter( JwtService jwtService, AcademyUserDetailsService userDetailsService )
    {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal( HttpServletRequest request, HttpServletResponse response, FilterChain filterChain ) throws ServletException, IOException
    {
        var authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer "))
        {
            filterChain.doFilter(request, response);
            return;
        }

        try
        {
            var token = authorizationHeader.substring(MINUS_KEYWORD);
            var username = jwtService.extractUsername(token);
            var user = userDetailsService.loadUserByUsername(username);

            if (jwtService.isValid(token))
            {
                var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
            }

        } catch (JwtException e)
        {
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
