package com.example.todo.controller;

import com.example.todo.model.Task;
import com.example.todo.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<Task> getAllTasks() {
        return taskService.findAll();
    }

    /**
     * GET /api/tasks/{id} - Busca uma tarefa por ID.
     * @param id ID da tarefa.
     * @return Tarefa se encontrada, ou 404 se não.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return taskService.findById(id)
                .map(ResponseEntity::ok)  // Retorna 200 com a tarefa
                .orElseGet(() -> ResponseEntity.notFound().build());  // Retorna 404
    }

    /**
     * POST /api/tasks - Cria uma nova tarefa.
     * @param task Dados da tarefa no corpo da requisição (JSON).
     * @return Tarefa criada com status 201.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task createTask(@RequestBody Task task) {
        return taskService.save(task);
    }

    /**
     * PUT /api/tasks/{id} - Atualiza uma tarefa existente.
     * @param id ID da tarefa.
     * @param task Novos dados da tarefa.
     * @return Tarefa atualizada ou 404 se não encontrada.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task) {
        return taskService.update(id, task)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * DELETE /api/tasks/{id} - Exclui uma tarefa.
     * @param id ID da tarefa.
     * @return 204 se excluída, ou 404 se não encontrada.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        if (taskService.findById(id).isPresent()) {
            taskService.delete(id);
            return ResponseEntity.noContent().build();  // 204 No Content
        }
        return ResponseEntity.notFound().build();  // 404 Not Found
    }
}
