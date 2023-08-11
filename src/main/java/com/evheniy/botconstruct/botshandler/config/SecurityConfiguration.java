    package com.evheniy.botconstruct.botshandler.config;

    import jakarta.servlet.Filter;
    import lombok.RequiredArgsConstructor;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.security.authentication.AuthenticationProvider;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
    import org.springframework.security.config.http.SessionCreationPolicy;
    import org.springframework.security.web.SecurityFilterChain;
    import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
    import org.springframework.web.cors.CorsConfiguration;
    import org.springframework.web.cors.CorsConfigurationSource;
    import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

    import java.util.Arrays;

    @Configuration
    @EnableWebSecurity
    @RequiredArgsConstructor
    public class SecurityConfiguration {
        private final JwtAuthenticationFilter jwtAuthFilter;
        private final AuthenticationProvider authenticationProvider;
        private static final String REGISTRATION_ENDPOINT = "/api/v1/auth/register";
        private static final String AUTHORIZATION_ENDPOINT = "/api/v1/auth/authenticate";
        private static final String WEBSOCKET_ENDPOINT = "/**";
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
          httpSecurity
                  .cors().and()
                  .csrf()
                  .disable()
                  .authorizeHttpRequests()
                  .requestMatchers(REGISTRATION_ENDPOINT).permitAll()
                  .requestMatchers(AUTHORIZATION_ENDPOINT).permitAll()
                  .requestMatchers(WEBSOCKET_ENDPOINT).permitAll()
                  .anyRequest()
                  .authenticated()
                  .and()
                  .sessionManagement()
                  .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                  .and()
                  .authenticationProvider(authenticationProvider)
                  .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
            return httpSecurity.build();
        }

    }