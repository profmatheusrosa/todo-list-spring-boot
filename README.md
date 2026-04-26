# Todo App

Uma aplicação simples de lista de tarefas (todo) com backend em Spring Boot e frontend em Angular. Este repositório foi organizado para ser didático — ideal para quem está aprendendo Spring Boot e integração com um frontend moderno.

Principais objetivos didáticos:
- Mostrar uma API REST em Spring Boot
- Demonstrar uso do Spring Data JPA com banco em memória (H2)
- Explicar estrutura em camadas (Controller → Service → Repository)
- Mostrar integração com um frontend Angular simples

Guia rápido (Quick Start)

1. Backend: na raiz do projeto execute `mvn spring-boot:run` — API em `http://localhost:8080`
2. Frontend: entre em `frontend/`, rode `npm install` e `npm start` — app em `http://localhost:4200`

Para instruções detalhadas, exemplos de requisição e visão da arquitetura, veja os documentos em `docs/`:

- `docs/USO.md` — passo-a-passo para rodar e testar o projeto
- `docs/EXEMPLOS.md` — exemplos `curl` e formato de payloads
- `docs/ARQUITETURA.md` — explicação das pastas, classes e fluxo

## Pré-requisitos

- Java 23 (ou versão compatível)
- Maven
- Node.js e npm

## Endpoints principais

- `GET /api/tasks` — lista tarefas
- `GET /api/tasks/{id}` — obtém tarefa por id
- `POST /api/tasks` — cria tarefa (JSON)
- `PUT /api/tasks/{id}` — atualiza tarefa
- `DELETE /api/tasks/{id}` — remove tarefa

Veja exemplos práticos em `docs/EXEMPLOS.md`.

## Estrutura do repositório

- `src/main/java/.../controller` — pontos de entrada HTTP (REST controllers)
- `src/main/java/.../service` — lógica de negócio
- `src/main/java/.../repository` — acesso a dados (Spring Data JPA)
- `frontend/` — aplicação Angular usada como cliente

## Como contribuir

1. Abra uma issue descrevendo a sugestão.
2. Crie um branch `feature/descritivo` e envie um PR com mudanças pequenas e explicativas.
3. Adicione exemplos ou melhorias na pasta `docs/` quando possível.

---

Se quiser, posso automaticamente adicionar os arquivos `docs/USO.md`, `docs/EXEMPLOS.md` e `docs/ARQUITETURA.md` com conteúdo didático em português — quer que eu os crie agora?