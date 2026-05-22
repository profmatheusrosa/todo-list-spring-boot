package com.example.todo;

import com.example.todo.dto.AuthRequest;
import com.example.todo.dto.TaskDTO;
import com.example.todo.exception.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String jwtToken;
    private HttpHeaders headers;

    @BeforeEach
    void setUp() {
        // Gera um nome de usuário único para cada execução de teste para evitar conflitos no banco H2
        String uniqueUsername = "user_" + UUID.randomUUID().toString().substring(0, 8);
        AuthRequest authRequest = new AuthRequest(uniqueUsername, "password123");

        // Registrar usuário
        restTemplate.postForEntity("http://localhost:" + port + "/api/auth/register", authRequest, String.class);

        // Login para obter o token
        ResponseEntity<Map> loginResponse = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/auth/login",
                authRequest,
                Map.class
        );

        jwtToken = (String) loginResponse.getBody().get("token");
        assertNotNull(jwtToken, "O token JWT não deve ser nulo.");

        // Configurar cabeçalho com o token
        headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    void getTasks_shouldReturn200() {
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<String> resp = restTemplate.exchange(
                "http://localhost:" + port + "/api/tasks",
                HttpMethod.GET,
                entity,
                String.class
        );
        assertEquals(200, resp.getStatusCodeValue());
    }

    @Test
    void createTask_withValidData_shouldReturn201() {
        TaskDTO newTask = new TaskDTO(null, "Tarefa de Teste", "Descrição da tarefa de teste", false);
        HttpEntity<TaskDTO> entity = new HttpEntity<>(newTask, headers);

        ResponseEntity<TaskDTO> resp = restTemplate.exchange(
                "http://localhost:" + port + "/api/tasks",
                HttpMethod.POST,
                entity,
                TaskDTO.class
        );

        assertEquals(201, resp.getStatusCodeValue());
        assertNotNull(resp.getBody());
        assertNotNull(resp.getBody().getId());
        assertEquals("Tarefa de Teste", resp.getBody().getTitle());
    }

    @Test
    void createTask_withInvalidData_shouldReturn400AndValidationDetails() {
        // Título em branco (violação do @NotBlank) e curto (violação do @Size min=3)
        TaskDTO invalidTask = new TaskDTO(null, "  ", "Descrição", false);
        HttpEntity<TaskDTO> entity = new HttpEntity<>(invalidTask, headers);

        ResponseEntity<ErrorResponse> resp = restTemplate.exchange(
                "http://localhost:" + port + "/api/tasks",
                HttpMethod.POST,
                entity,
                ErrorResponse.class
        );

        assertEquals(400, resp.getStatusCodeValue());
        assertNotNull(resp.getBody());
        assertEquals("Bad Request", resp.getBody().getError());
        assertEquals("Erro de validação nos dados enviados.", resp.getBody().getMessage());
        assertNotNull(resp.getBody().getDetails());
        assertTrue(resp.getBody().getDetails().size() > 0, "Deve conter detalhes das violações de validação.");
    }

    @Test
    void getTaskById_whenNotFound_shouldReturn404AndErrorResponse() {
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<ErrorResponse> resp = restTemplate.exchange(
                "http://localhost:" + port + "/api/tasks/9999", // ID inexistente
                HttpMethod.GET,
                entity,
                ErrorResponse.class
        );

        assertEquals(404, resp.getStatusCodeValue());
        assertNotNull(resp.getBody());
        assertEquals("Not Found", resp.getBody().getError());
        assertEquals("Tarefa com ID 9999 não foi encontrada.", resp.getBody().getMessage());
    }
}
