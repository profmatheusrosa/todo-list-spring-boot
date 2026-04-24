# Todo App

Uma aplicação simples de lista de tarefas (todo) com backend em Spring Boot e frontend em Angular. **Ideal para aprender Spring Boot do zero!**

Este projeto demonstra conceitos fundamentais do Spring Boot, como:
- Criação de APIs REST
- Uso do Spring Data JPA com banco H2
- Injeção de dependência
- Configuração de CORS
- Estrutura em camadas (Controller, Service, Repository)

Para um guia passo a passo, consulte o [`MANUAL.md`](MANUAL.md).

## Pré-requisitos

- Java 23
- Maven
- Node.js
- npm

## Como executar

### Backend (Spring Boot)

1. Navegue até o diretório raiz do projeto.
2. Execute o comando: `mvn spring-boot:run`
3. O backend estará rodando em `http://localhost:8080`

### Frontend (Angular)

1. Navegue até o diretório `frontend/`.
2. Instale as dependências: `npm install`
3. Execute o comando: `npm start`
4. O frontend estará rodando em `http://localhost:4200`

## Estrutura do Projeto

- `src/`: Código fonte do backend Java
- `frontend/`: Código fonte do frontend Angular
- `MANUAL.md`: Documentação adicional do projeto

## Funcionalidades

- Criar, listar, atualizar e deletar tarefas (CRUD)
- Interface web responsiva

Para mais detalhes, consulte o `MANUAL.md`.