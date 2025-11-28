package com.meumural.projetobackend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired
    private UserAuthenticationFilter userAuthenticationFilter;

    public static final String [] ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED = {
            "/api/usuario/listar",
            "/api/usuario/criar",
            "/api/usuario/deletar",
            "/api/usuario/atualizarStatusUsuario",
            "/api/usuario/atualizar",
            "/api/usuario/buscarPorId",
            "/api/usuario/criarUsuario",

            "/api/arquivoPostagem/listar",
            "/api/arquivoPostagem/criar",
            "/api/arquivoPostagem/deletar",
            "/api/arquivoPostagem/atualizar",
            "/api/arquivoPostagem/buscar",

            "/api/grupo/listar",
            "/api/grupo/criar",
            "/api/grupo/deletar",
            "/api/grupo/atualizar",
            "/api/grupo/buscar",

            "/api/postagem/listar",
            "/api/postagem/criar",
            "/api/postagem/deletar",
            "/api/postagem/atualizar",
            "/api/postagem/buscar",

            "/api/arquivoPostagem/listar",
            "/api/arquivoPostagem/criar",
            "/api/arquivoPostagem/deletar",
            "/api/arquivoPostagem/atualizar",
            "/api/arquivoPostagem/buscar",

            "/api/usuarioGrupo/listar",
            "/api/usuarioGrupo/criar",
            "/api/usuarioGrupo/deletar",
            "/api/usuarioGrupo/atualizar",
            "/api/usuarioGrupo/buscar",

            "/web/download/app",
            "/h2-console",
            // 🔓 Swagger/OpenAPI UI
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html"
    };

    // Endpoints que requerem autenticação para serem acessados
    public static final String [] ENDPOINTS_WITH_AUTHENTICATION_REQUIRED = {

    };

    // Endpoints que só podem ser acessador por usuários com permissão de cliente
    public static final String [] ENDPOINTS_USUARIO = {

    };

    // Endpoints que só podem ser acessador por usuários com permissão de administrador
    public static final String [] ENDPOINTS_RESPONSAVELEQUIPE = {

    };

    public static final String [] ENDPOINTS_LIDERCOMITE = {

    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() //adicionado para funcionamento do swagger
                        .requestMatchers(ENDPOINTS_USUARIO).hasRole("USUARIO")
                        .requestMatchers(ENDPOINTS_RESPONSAVELEQUIPE).hasRole("RESPONSAVELEQUIPE")
                        .requestMatchers(ENDPOINTS_LIDERCOMITE).hasRole("LIDERCOMITE")
                        .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_REQUIRED).authenticated()
                        .anyRequest().denyAll()
                )
                .addFilterBefore(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
