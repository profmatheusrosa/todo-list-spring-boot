# Arquitetura do Projeto — Visão Rápida

Esta seção explica onde olhar para aprender conceitos-chave implementados no projeto.

- `controller` — recebe requisições HTTP e transforma em chamadas de serviço. Bom ponto de partida para entender endpoints.
- `service` — contém lógica de negócio; ideal para ver validações e fluxos de aplicação.
- `repository` — interfaces Spring Data JPA; mostra consultas e persistência (H2).
- `model` — classes de domínio (ex.: `Task`) contendo campos e anotações JPA.

Fluxo típico de uma requisição `POST /api/tasks`:

1. `TaskController` recebe o JSON e converte em `Task`.
2. Controller chama `TaskService.create(...)`.
3. `TaskService` aplica regras e chama `TaskRepository.save(...)`.
4. `TaskRepository` persiste no H2 e retorna a entidade salva.

Arquivos importantes para estudo:

- `src/main/java/com/example/todo/controller/TaskController.java`
- `src/main/java/com/example/todo/service/TaskService.java`
- `src/main/java/com/example/todo/repository/TaskRepository.java`
- `src/main/java/com/example/todo/model/Task.java`

Sugestões de estudo progressivo:

1. Leia `TaskController` para mapear endpoints.
2. Siga a chamada até `TaskService` para entender a lógica.
3. Veja as anotações JPA em `Task` para entender o mapeamento.
