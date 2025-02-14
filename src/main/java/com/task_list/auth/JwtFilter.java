package com.task_list.auth;

import com.task_list.jwt.JwtService;
import com.task_list.user.entity.MyUser;
import com.task_list.user.service.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final ApplicationContext pisina;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.substring(7);
        String emailUser = jwtService.extractUserEmail(token);


        if(emailUser != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = pisina.getBean(MyUserDetailsService.class).loadUserByUsername(emailUser);
            if(userDetails == null) {
                filterChain.doFilter(request, response);
                return;
            }
            if(jwtService.isValidToken(token, userDetails)){
                UsernamePasswordAuthenticationToken userAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(emailUser, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(userAuthenticationToken);
                filterChain.doFilter(request, response);
                return;
            }

            filterChain.doFilter(request, response);
            return;
        }
    }
}
