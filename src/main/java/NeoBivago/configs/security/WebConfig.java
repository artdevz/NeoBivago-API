package NeoBivago.configs.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import NeoBivago.configs.jwt.JwtFilter;

@Configuration
@EnableWebSecurity
public class WebConfig {
    
    private final JwtFilter jwtF;

    private final AuthenticationProvider authProvider;
    
    public WebConfig(JwtFilter jwtF, AuthenticationProvider authProvider) {
        this.jwtF = jwtF;
        this.authProvider = authProvider;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("DELETE", "GET", "HEAD", "OPTIONS", "PATCH", "POST", "PUT", "TRACE"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        
        return source;

    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
        .cors(c -> c.configurationSource(corsConfigurationSource()))
        .csrf(csrf -> csrf.disable())
        .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(request -> request
            
            .requestMatchers("/**").permitAll()
            // .requestMatchers( HttpMethod.POST, "/user", "/auth/login").permitAll()
            // .requestMatchers(HttpMethod.GET, "/room/filter").permitAll()
            // .requestMatchers(HttpMethod.GET, "/user", "/hotel", "/room", "/reservation").hasAuthority("USER")
            .anyRequest().authenticated())

        .authenticationProvider(authProvider)
        .addFilterBefore(jwtF, UsernamePasswordAuthenticationFilter.class);        

        return http.build();

    }    

}
