package com.example.todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal da aplicação Spring Boot.
 * A anotação @SpringBootApplication ativa a configuração automática do Spring,
 * incluindo o servidor web embutido (Tomcat) e a configuração do banco de dados.
 */
@SpringBootApplication
public class TodoApplication {

    public static void main(String[] args) {
        // Inicia a aplicação Spring Boot
        SpringApplication.run(TodoApplication.class, args);
    }
}
