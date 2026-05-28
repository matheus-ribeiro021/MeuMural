package com.meumural.projetobackend.config;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.meumural.projetobackend.entity.Usuario;
import com.meumural.projetobackend.repository.UsuarioRepository;
import com.meumural.projetobackend.service.JwtTokenService;
import com.meumural.projetobackend.service.UserDetailsImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class UserAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (checkIfEndpointIsNotPublic(request)) {
            String token = recoveryToken(request);
            if (token != null && !token.isBlank()) {
                try {
                    String subject = jwtTokenService.getSubjectFromToken(token);
                    Usuario usuario = usuarioRepository.findByEmail(subject)
                            .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
                    UserDetailsImpl userDetails = new UserDetailsImpl(usuario);
                    Authentication authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } catch (Exception ex) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido ou expirado.");
                    return;
                }
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token ausente.");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private String recoveryToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    private boolean checkIfEndpointIsNotPublic(HttpServletRequest request) {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return false;
        }
        String requestURI = request.getRequestURI();
        return Arrays.stream(SecurityConfiguration.PUBLIC_MATCHERS).noneMatch(publicEndpoint ->
                requestURI.startsWith(publicEndpoint.replace("/**", ""))
        );
    }
}
