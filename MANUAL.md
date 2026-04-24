# Manual do Projeto Spring Boot To-Do

Este guia foi escrito para você que está começando agora com Spring Boot e Maven. A ideia é ler o manual e acompanhar o projeto ao mesmo tempo.

## 1. O que este projeto faz

Este é um aplicativo backend simples para gerenciar tarefas (To-Do). Ele expõe uma API REST que permite:

- listar tarefas
- buscar uma tarefa por ID
- criar uma tarefa
- atualizar uma tarefa
- excluir uma tarefa

O projeto usa:

- Spring Boot
- Spring Web
- Spring Data JPA
- banco de dados em memória H2
- Maven para construção e execução

## 2. Estrutura do projeto

No diretório raiz temos:

- `pom.xml` - configura o Maven e as dependências
- `src/main/java` - código Java do aplicativo
- `src/main/resources/application.properties` - configurações do Spring Boot
- `MANUAL.md` - este guia explicativo

Dentro de `src/main/java/com/example/todo` existem três pacotes principais:

1. `model` - contém a classe `Task`, que representa a entidade do banco de dados
2. `repository` - contém `TaskRepository`, a interface que fala com o banco
3. `service` - contém `TaskService`, a camada de regras de negócio
4. `controller` - contém `TaskController`, que cria os endpoints HTTP

## 3. Como o projeto inicia

O ponto de entrada está em:

- `src/main/java/com/example/todo/TodoApplication.java`

Essa classe tem a anotação `@SpringBootApplication`, que aciona o Spring Boot e configura a aplicação automaticamente.

### O que acontece aqui

```java
@SpringBootApplication
public class TodoApplication {
    public static void main(String[] args) {
        SpringApplication.run(TodoApplication.class, args);
    }
}
```

Quando você roda a aplicação, o Spring Boot faz duas coisas principais:

- lê as configurações em `application.properties`
- cria um servidor web embutido (Tomcat)

## 4. A entidade `Task`

Arquivo: `src/main/java/com/example/todo/model/Task.java`

Essa classe representa a tabela de tarefas no banco de dados. Os campos são:

- `id` — identificador gerado automaticamente
- `title` — título da tarefa
- `description` — descrição opcional
- `done` — se a tarefa foi concluída ou não

A anotação `@Entity` diz ao Spring Data JPA para mapear essa classe para uma tabela.

## 5. O repositório

Arquivo: `src/main/java/com/example/todo/repository/TaskRepository.java`

Essa interface estende `JpaRepository<Task, Long>`. Ela oferece métodos prontos como:

- `findAll()`
- `findById(id)`
- `save(task)`
- `deleteById(id)`

Você não precisa implementar nada aqui; o Spring cria uma implementação por você.

## 6. A camada de serviço

Arquivo: `src/main/java/com/example/todo/service/TaskService.java`

O serviço organiza a lógica de uso dos dados. Ele recebe o repositório por injeção e expõe métodos como:

- `findAll()`
- `findById(id)`
- `save(task)`
- `update(id, task)`
- `delete(id)`

## 7. O controlador REST

Arquivo: `src/main/java/com/example/todo/controller/TaskController.java`

O controlador cria os endpoints HTTP. Cada método tem uma anotação como `@GetMapping`, `@PostMapping`, etc.

Exemplos de endpoints:

- `GET /api/tasks` - lista todas as tarefas
- `POST /api/tasks` - cria uma nova tarefa
- `PUT /api/tasks/{id}` - atualiza uma tarefa
- `DELETE /api/tasks/{id}` - exclui uma tarefa

## 8. Configuração de CORS

Arquivo: `src/main/java/com/example/todo/config/CorsConfig.java`

Como o frontend Angular roda em uma porta diferente (4200), precisamos permitir requisições cross-origin. A classe `CorsConfig` configura isso.

## 9. Testando a API

Após iniciar o backend (`mvn spring-boot:run`), você pode testar os endpoints com ferramentas como:

- **Postman** (interface gráfica, mais fácil para iniciantes)
- **curl** (linha de comando)
- **Insomnia** (similar ao Postman)

### Testando com Postman

1. Baixe e instale o Postman em https://www.postman.com/
2. Abra o Postman e crie uma nova coleção chamada "Todo API"
3. Adicione os seguintes requests:

#### 1. Listar tarefas (GET)
- Método: GET
- URL: `http://localhost:8080/api/tasks`
- Clique em "Send"

#### 2. Criar tarefa (POST)
- Método: POST
- URL: `http://localhost:8080/api/tasks`
- Headers: Adicione `Content-Type: application/json`
- Body: Selecione "raw" e JSON:
```json
{
  "title": "Aprender Spring Boot",
  "description": "Estudar este projeto",
  "done": false
}
```
- Clique em "Send"

#### 3. Buscar tarefa por ID (GET)
- Método: GET
- URL: `http://localhost:8080/api/tasks/1` (substitua 1 pelo ID real)
- Clique em "Send"

#### 4. Atualizar tarefa (PUT)
- Método: PUT
- URL: `http://localhost:8080/api/tasks/1` (substitua 1 pelo ID)
- Headers: `Content-Type: application/json`
- Body: JSON com dados atualizados
- Clique em "Send"

#### 5. Excluir tarefa (DELETE)
- Método: DELETE
- URL: `http://localhost:8080/api/tasks/1` (substitua 1 pelo ID)
- Clique em "Send"

### Testando com curl

Exemplos no terminal:

```bash
# Listar tarefas
curl http://localhost:8080/api/tasks

# Criar tarefa
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"title": "Aprender Spring Boot", "description": "Estudar este projeto", "done": false}'

# Buscar tarefa por ID
curl http://localhost:8080/api/tasks/1

# Atualizar tarefa
curl -X PUT http://localhost:8080/api/tasks/1 \
  -H "Content-Type: application/json" \
  -d '{"title": "Aprender Spring Boot", "description": "Projeto concluído", "done": true}'

# Excluir tarefa
curl -X DELETE http://localhost:8080/api/tasks/1
```

## 10. Banco de dados H2

O projeto usa H2 em memória. Para acessar o console H2:

1. Inicie a aplicação
2. Abra `http://localhost:8080/h2-console`
3. Use JDBC URL: `jdbc:h2:mem:testdb`
4. Usuário: `sa`, Senha: (vazio)

## 11. Próximos passos para aprender

- Adicione validação com `@Valid` e `BindingResult`
- Implemente autenticação com Spring Security
- Adicione testes unitários com JUnit e Mockito
- Integre com um banco real como PostgreSQL
- Adicione documentação da API com Swagger

Este projeto é um ponto de partida. Explore o código, faça modificações e experimente!

O método `update` primeiro busca a tarefa existente e só então altera os campos.

### Por que usar `TaskService`?

Essa é uma boa prática:

- separa o controlador da lógica do banco
- facilita testes e manutenção
- evita colocar lógica de atualização diretamente no controller

## 7. O controlador REST

Arquivo: `src/main/java/com/example/todo/controller/TaskController.java`

O controlador define as rotas da API. Ele usa `@RestController` e `@RequestMapping("/api/tasks")`.

As rotas são:

- `GET /api/tasks` — lista todas as tarefas
- `GET /api/tasks/{id}` — busca uma tarefa pelo ID
- `POST /api/tasks` — cria uma nova tarefa
- `PUT /api/tasks/{id}` — atualiza uma tarefa existente
- `DELETE /api/tasks/{id}` — exclui uma tarefa

Exemplo de criação de tarefa via JSON:

```json
{
  "title": "Estudar Spring Boot",
  "description": "Ler o manual e testar endpoints",
  "done": false
}
```

## 8. Como rodar o projeto

No terminal, dentro da pasta `c:\desenvolvimento\projeto-spring`, execute:

```bash
mvn clean install
mvn spring-boot:run
```

Se tudo estiver certo, a aplicação vai subir em `http://localhost:8080`.

## 9. Como testar os endpoints

Use o navegador, o Postman, o Insomnia ou o `curl`.

### Listar tarefas

```bash
curl http://localhost:8080/api/tasks
```

### Criar tarefa

```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d "{ \"title\": \"Aprender Spring\", \"description\": \"Fazer projeto\", \"done\": false }"
```

### Atualizar tarefa

```bash
curl -X PUT http://localhost:8080/api/tasks/1 \
  -H "Content-Type: application/json" \
  -d "{ \"title\": \"Aprender Spring Boot\", \"description\": \"Atualizar o projeto\", \"done\": true }"
```

### Excluir tarefa

```bash
curl -X DELETE http://localhost:8080/api/tasks/1
```

## 10. O arquivo de configuração

Arquivo: `src/main/resources/application.properties`

Aqui estão as configurações mais importantes:

- `spring.h2.console.enabled=true` — habilita o console web do H2
- `spring.datasource.url=jdbc:h2:mem:todo-db` — banco em memória
- `spring.jpa.hibernate.ddl-auto=update` — cria e atualiza tabelas automaticamente
- `spring.jpa.show-sql=true` — mostra as consultas SQL no terminal

## 11. Dicas para aprender acompanhando o projeto

Leia o manual e verifique cada arquivo junto com o código:

1. Abra `TaskController` e veja como os endpoints chamam o serviço
2. Abra `TaskService` e entenda como os dados são gravados
3. Abra `TaskRepository` e reconheça que ele é apenas uma interface
4. Abra `Task` e observe os campos e anotações JPA

Se quiser, faça mudanças pequenas como:

- adicionar um campo `deadline`
- criar filtro por status `done`
- adicionar validação básica de campos

## 12. Próximos passos

Quando já entender a estrutura, experimente:

- adicionar um frontend leve com Thymeleaf
- criar testes de integração para os endpoints
- usar um banco permanente como PostgreSQL

## 13. Frontend Angular básico

Para quem quer aprender também a parte visual, criei um frontend em Angular dentro da pasta `frontend`.

### Estrutura Angular

- `frontend/package.json` — dependências do Node e scripts
- `frontend/angular.json` — configuração do Angular CLI
- `frontend/tsconfig.json` — configuração do compilador TypeScript
- `frontend/src/` — código da aplicação Angular
- `frontend/src/app/` — componente e serviço principal

### O que o frontend faz

O aplicativo Angular conversa com o backend em `http://localhost:8080/api/tasks` e permite:

- listar tarefas
- criar tarefa
- marcar como concluída
- excluir tarefa

### Como rodar o frontend

1. Abra um terminal na pasta `c:\desenvolvimento\projeto-spring\frontend`
2. Execute:

```bash
npm install
npm start
```

3. O Angular deve abrir a página em `http://localhost:4200`

> Se você ainda não tem um repositório Git inicializado, não há um branch real criado. A pasta `frontend` funciona como a nova área do projeto para o frontend Angular.

### Como usar o frontend no dia a dia

- Garanta que o backend esteja rodando em `http://localhost:8080`
- Abra o frontend em `http://localhost:4200`
- Use o formulário para adicionar tarefas
- Clique em `Concluir` para marcar como pronto
- Clique em `Excluir` para apagar tarefas

### Como explorar o código Angular

1. Abra `frontend/src/app/app.component.ts` e veja a lógica de carregamento e atualização das tarefas
2. Abra `frontend/src/app/task.service.ts` para ver as chamadas HTTP ao backend
3. Abra `frontend/src/app/app.component.html` para ver a interface com o formulário e a lista

---

Esse manual foi pensado para você acompanhar em tempo real. Abra um arquivo, leia a seção correspondente e verifique o código logo em seguida.
