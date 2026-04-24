package com.example.todo.service;

import com.example.todo.model.Task;
import com.example.todo.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Serviço para lógica de negócio relacionada às tarefas.
 * @Service marca como componente Spring para injeção de dependência.
 */
@Service
public class TaskService {

    private final TaskRepository taskRepository;

    // Injeção do repositório via construtor
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Retorna todas as tarefas.
     */
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    /**
     * Busca uma tarefa por ID.
     */
    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    /**
     * Salva uma nova tarefa ou atualiza uma existente.
     */
    public Task save(Task task) {
        return taskRepository.save(task);
    }

    /**
     * Atualiza uma tarefa existente.
     * Retorna Optional vazio se a tarefa não for encontrada.
     */
    public Optional<Task> update(Long id, Task updatedTask) {
        return taskRepository.findById(id).map(task -> {
            task.setTitle(updatedTask.getTitle());
            task.setDescription(updatedTask.getDescription());
            task.setDone(updatedTask.isDone());
            return taskRepository.save(task);
        });
    }

    /**
     * Exclui uma tarefa por ID.
     */
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}
