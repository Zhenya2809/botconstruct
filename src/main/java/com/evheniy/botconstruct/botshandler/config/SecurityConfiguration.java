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
        private static final String CHATQUEUE = "/functional/active-queues";
        private static final String LOADMESSAGES = "/functional/messages/**";
        private static final String GETUSERNAME = "/functional/getusername/**";
        private static final String CREATEBOT = "/bot/**";
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
                  .requestMatchers(CHATQUEUE).permitAll()
                  .requestMatchers(LOADMESSAGES).permitAll()
                  .requestMatchers(GETUSERNAME).permitAll()
                  .requestMatchers(CREATEBOT).permitAll()
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