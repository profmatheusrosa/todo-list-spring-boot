# Guia de Uso — Todo App

Este guia mostra, passo a passo, como executar e testar a aplicação localmente.

1) Backend (Spring Boot)

- Requisitos: Java, Maven
- No diretório raiz do projeto, execute:

```
mvn spring-boot:run
```

- A API ficará disponível em `http://localhost:8080`.

2) Frontend (Angular)

- Requisitos: Node.js, npm
- Entre na pasta `frontend/` e instale dependências:

```
cd frontend
npm install
npm start
```

- O frontend abrirá em `http://localhost:4200`.

3) Testando a API

- Use o navegador, o cliente web (frontend) ou `curl`/Postman. Exemplos em `docs/EXEMPLOS.md`.

3.1) Documentação interativa (Swagger / OpenAPI)

- Após iniciar o backend (`mvn spring-boot:run`), a documentação OpenAPI estará disponível em:

- `http://localhost:8080/swagger-ui.html`
- ou `http://localhost:8080/swagger-ui/index.html`

- O endpoint raw do OpenAPI fica em: `http://localhost:8080/v3/api-docs`

4) Banco de dados

- O projeto usa H2 (em memória) por padrão. Não há necessidade de configuração para testes locais.

5) Dicas didáticas

- Leia os controllers em `src/main/java/.../controller` para ver os endpoints.
- Verifique `TaskService` para entender regras de negócio.
- Verifique `TaskRepository` para exemplos de uso do Spring Data JPA.

Se quiser, eu posso adicionar comandos `make` ou scripts npm para rodar backend+frontend juntos.

6) Script de execução (Windows PowerShell)

- Existe um script `scripts/run-all.ps1` que abre duas janelas PowerShell: uma para o backend e outra para o frontend.
- Execute a partir da raiz do repositório:

```
.\scripts\run-all.ps1
```
