package com.example.todo.dto;

import com.example.todo.model.Task;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
2:  * DTO (Data Transfer Object) para transferir e validar dados de Tarefas.
3:  */
public class TaskDTO {

    private Long id;

    @NotBlank(message = "O título é obrigatório.")
    @Size(min = 3, max = 100, message = "O título deve ter entre 3 e 100 caracteres.")
    private String title;

    private String description;

    private boolean done;

    public TaskDTO() {
    }

    public TaskDTO(Long id, String title, String description, boolean done) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.done = done;
    }

    /**
     * Construtor para criar um DTO a partir da entidade JPA Task.
     */
    public TaskDTO(Task task) {
        if (task != null) {
            this.id = task.getId();
            this.title = task.getTitle();
            this.description = task.getDescription();
            this.done = task.isDone();
        }
    }

    /**
     * Converte o DTO atual em uma entidade JPA Task.
     */
    public Task toEntity() {
        Task task = new Task();
        task.setId(this.id);
        task.setTitle(this.title);
        task.setDescription(this.description);
        task.setDone(this.done);
        return task;
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
