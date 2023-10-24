package me.khanhnguyen.mainservice.config.security;

import lombok.extern.slf4j.Slf4j;
import me.khanhnguyen.mainservice.constant.RestMapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * @author Nguyen K. Ngo
 * @since 24-Oct-2023
 */
@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    public SecurityConfig(CustomAuthenticationEntryPoint customAuthenticationEntryPoint) {
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
    }

    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedMethods(
                Arrays.asList(
                        HttpMethod.GET.name(),
                        HttpMethod.POST.name(),
                        HttpMethod.PUT.name(),
                        HttpMethod.DELETE.name()));

        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOrigins(RestMapping.REST_ORIGINS);
        corsConfiguration.setMaxAge(3600L);

        corsConfiguration.setAllowedHeaders(Arrays.asList("Cache-Control", "Content-Type"));

        UrlBasedCorsConfigurationSource corsSource = new UrlBasedCorsConfigurationSource();
        corsSource.registerCorsConfiguration("/**", corsConfiguration);

        return corsSource;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exh -> exh.authenticationEntryPoint(customAuthenticationEntryPoint))
                .sessionManagement(ssm -> ssm.sessionCreationPolicy(SessionCreationPolicy.NEVER))
                .authorizeHttpRequests(auz -> auz
                        .requestMatchers(RestMapping.WHITELIST).permitAll()
                        .anyRequest().authenticated())
//                .addFilterBefore(sessionFilterHandler, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults());
        return  http.build();
    }
}
