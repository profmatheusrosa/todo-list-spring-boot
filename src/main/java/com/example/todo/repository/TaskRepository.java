package com.example.todo.repository;

import com.example.todo.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório para a entidade Task.
 * Estende JpaRepository, que fornece métodos CRUD prontos (save, findAll, findById, delete, etc.).
 * @Repository marca como componente Spring para injeção de dependência.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    // Não precisa implementar métodos; o Spring Data JPA faz isso automaticamente
}
