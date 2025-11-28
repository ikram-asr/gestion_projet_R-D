package com.rnd.gateway.config;

import com.rnd.gateway.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {
    
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    
    @Value("${auth.service.url:http://localhost:8081}")
    private String authServiceUrl;
    
    @Value("${validation.service.url:http://localhost:8082}")
    private String validationServiceUrl;
    
    @Value("${project.service.url:http://localhost:8083}")
    private String projectServiceUrl;
    
    @Value("${finance.service.url:http://localhost:8084}")
    private String financeServiceUrl;
    
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth-service", r -> r
                        .path("/api/auth/**")
                        .uri(authServiceUrl))
                .route("validation-service", r -> r
                        .path("/api/validations/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter.apply(new JwtAuthenticationFilter.Config())))
                        .uri(validationServiceUrl))
                .route("project-service", r -> r
                        .path("/api/projects/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter.apply(new JwtAuthenticationFilter.Config())))
                        .uri(projectServiceUrl))
                .route("finance-service", r -> r
                        .path("/api/finance/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter.apply(new JwtAuthenticationFilter.Config())))
                        .uri(financeServiceUrl))
                .route("health", r -> r
                        .path("/health")
                        .uri("http://localhost:8081"))
                .build();
    }
}

