package com.example.todo.controller;

import com.example.todo.dto.TaskDTO;
import com.example.todo.model.Task;
import com.example.todo.service.TaskService;
import com.example.todo.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador REST para gerenciar tarefas (Tasks).
 * Expõe endpoints HTTP para operações CRUD.
 * @RestController indica que os métodos retornam dados JSON diretamente.
 * @RequestMapping define o caminho base para todos os endpoints.
 */
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    // Injeção de dependência do serviço via construtor (recomendado pelo Spring)
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * GET /api/tasks - Lista todas as tarefas.
     * @return Lista de tarefas em JSON.
     */
    @GetMapping
    public List<TaskDTO> getAllTasks() {
        return taskService.findAll().stream()
                .map(TaskDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * GET /api/tasks/{id} - Busca uma tarefa por ID.
     * @param id ID da tarefa.
     * @return Tarefa se encontrada, ou lança ResourceNotFoundException se não.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {
        Task task = taskService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa com ID " + id + " não foi encontrada."));
        return ResponseEntity.ok(new TaskDTO(task));
    }

    /**
     * POST /api/tasks - Cria uma nova tarefa com validação.
     * @param taskDTO Dados da tarefa no corpo da requisição (JSON).
     * @return Tarefa criada com status 201.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDTO createTask(@Valid @RequestBody TaskDTO taskDTO) {
        Task task = taskDTO.toEntity();
        Task savedTask = taskService.save(task);
        return new TaskDTO(savedTask);
    }

    /**
     * PUT /api/tasks/{id} - Atualiza uma tarefa existente com validação.
     * @param id ID da tarefa.
     * @param taskDTO Novos dados da tarefa.
     * @return Tarefa atualizada ou lança ResourceNotFoundException se não encontrada.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @Valid @RequestBody TaskDTO taskDTO) {
        Task updatedTask = taskService.update(id, taskDTO.toEntity())
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa com ID " + id + " não foi encontrada."));
        return ResponseEntity.ok(new TaskDTO(updatedTask));
    }

    /**
     * DELETE /api/tasks/{id} - Exclui uma tarefa.
     * @param id ID da tarefa.
     * @return 204 se excluída, ou lança ResourceNotFoundException se não encontrada.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        if (taskService.findById(id).isPresent()) {
            taskService.delete(id);
            return ResponseEntity.noContent().build();  // 204 No Content
        }
        throw new ResourceNotFoundException("Tarefa com ID " + id + " não foi encontrada.");
    }
}
