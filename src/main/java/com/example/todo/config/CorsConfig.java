package com.example.todo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuração de CORS (Cross-Origin Resource Sharing).
 * Permite que o frontend Angular (em localhost:4200) acesse a API.
 * @Configuration marca como classe de configuração Spring.
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")  // Aplica a todas as rotas /api/**
                .allowedOrigins("http://localhost:4200")  // Permite origem do frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Métodos HTTP permitidos
                .allowedHeaders("*")  // Permite todos os headers
                .allowCredentials(true)  // Permite cookies/credenciais
                .maxAge(3600);  // Cache da preflight por 1 hora
    }
}
