import { Component, OnInit } from '@angular/core';
import { Task } from './task';
import { TaskService } from './task.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'Lista de Tarefas';
  tasks: Task[] = [];
  newTask: Task = { title: '', description: '', done: false } as Task;
  loading = false;
  isDarkMode = false;
  error = '';

  constructor(private taskService: TaskService) {}

  ngOnInit(): void {
    this.loadTasks();
    this.loadTheme();
  }

  loadTheme(): void {
    const savedTheme = localStorage.getItem('theme');
    if (savedTheme === 'dark') {
      this.isDarkMode = true;
      document.body.classList.add('dark-mode');
    }
  }

  toggleDarkMode(): void {
    this.isDarkMode = !this.isDarkMode;
    if (this.isDarkMode) {
      document.body.classList.add('dark-mode');
      localStorage.setItem('theme', 'dark');
    } else {
      document.body.classList.remove('dark-mode');
      localStorage.setItem('theme', 'light');
    }
  }

  loadTasks(): void {
    this.loading = true;
    this.error = '';
    this.taskService.getTasks().subscribe({
      next: tasks => {
        this.tasks = tasks;
        this.loading = false;
      },
      error: err => {
        this.error = 'Não foi possível carregar as tarefas.';
        console.error(err);
        this.loading = false;
      }
    });
  }

  addTask(): void {
    if (!this.newTask.title.trim()) {
      return;
    }

    this.taskService.createTask(this.newTask).subscribe({
      next: task => {
        this.tasks.push(task);
        this.newTask = { title: '', description: '', done: false } as Task;
      },
      error: err => {
        this.error = 'Erro ao criar tarefa.';
        console.error(err);
      }
    });
  }

  toggleDone(task: Task): void {
    const updatedTask: Task = { ...task, done: !task.done };
    this.taskService.updateTask(task.id!, updatedTask).subscribe({
      next: updated => {
        task.done = updated.done;
      },
      error: err => {
        this.error = 'Erro ao atualizar tarefa.';
        console.error(err);
      }
    });
  }

  deleteTask(task: Task): void {
    if (!task.id) {
      return;
    }

    this.taskService.deleteTask(task.id).subscribe({
      next: () => {
        this.tasks = this.tasks.filter(item => item.id !== task.id);
      },
      error: err => {
        this.error = 'Erro ao excluir tarefa.';
        console.error(err);
      }
    });
  }

  toggleExpand(task: Task): void {
    task.expanded = !task.expanded;
  }

  saveEdit(task: Task): void {
    if ((task as any).isEditing) {
      this.taskService.updateTask(task.id!, task).subscribe({
        next: updated => {
          (task as any).isEditing = false;
        },
        error: err => {
          this.error = 'Erro ao salvar alterações.';
          console.error(err);
        }
      });
    } else {
      (task as any).isEditing = true;
      // Foco opcional no input via template se necessário
    }
  }

  cancelEdit(task: Task): void {
    (task as any).isEditing = false;
    this.loadTasks();
  }
}
