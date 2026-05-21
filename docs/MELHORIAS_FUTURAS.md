# Sugestões de Melhorias Futuras

Este documento lista diversas ideias de próximos passos de aprendizado e melhorias que podem ser adicionadas ao projeto `todo-list-spring-boot` para evoluir as habilidades de desenvolvimento, divididas por áreas.

## [x] 1. Segurança (Backend e Frontend) - *Altamente Recomendado*
Atualmente, qualquer pessoa pode acessar as tarefas.
*   **Backend (Spring Boot):** Adicionar o `spring-boot-starter-security`. Implementar autenticação baseada em JWT (JSON Web Tokens). Criar rotas de login e registro, e proteger o `TaskController` para que apenas usuários autenticados possam ver/editar tarefas. Fazer com que cada tarefa pertença a um usuário específico.
*   **Frontend (Angular):** Criar componentes de Login e Registro. Implementar um *HttpInterceptor* para adicionar o token JWT em cada requisição. Usar *Route Guards* (`CanActivate`) para proteger a tela de tarefas caso o usuário não esteja logado.

## [ ] 2. Evolução de Banco de Dados (Backend)
O banco H2 é ótimo para testes, mas não para produção.
*   **Troca de Banco:** Substituir o H2 por um banco relacional real, como **PostgreSQL** ou **MySQL**.
*   **Migrations:** Ao invés de deixar o Hibernate criar as tabelas automaticamente (`spring.jpa.hibernate.ddl-auto=update`), aprender a usar ferramentas de versionamento de banco de dados como **Flyway** ou **Liquibase**.

## [ ] 3. Tratamento de Erros e Validação (Backend)
*   **Validações:** Adicionar a dependência `spring-boot-starter-validation`. Usar anotações como `@NotBlank`, `@Size`, e `@NotNull` na classe `Task` (ou melhor, criar DTOs - *Data Transfer Objects*) para garantir que uma tarefa não seja salva sem título, por exemplo.
*   **Tratamento Global de Exceções:** Criar uma classe anotada com `@ControllerAdvice` para capturar erros (como "Tarefa não encontrada" ou erros de validação) e retornar mensagens de erro padronizadas e amigáveis (JSON) em vez da página de erro padrão do Spring.

## [ ] 4. Paginação, Filtros e Ordenação (Fullstack)
Se a aplicação crescer para milhares de tarefas, ela pode ficar lenta.
*   **Backend:** Modificar o `TaskRepository` e `TaskController` para aceitar um objeto `Pageable` do Spring Data. Implementar endpoints que retornem páginas de tarefas (ex: 10 por página) em vez da lista inteira.
*   **Frontend:** Criar controles de paginação ("Próxima página" e "Página anterior") e adicionar a possibilidade de ordenar as tarefas por data de criação ou filtrar apenas as "concluídas".

## [ ] 5. DevOps e Infraestrutura
*   **Docker:** Criar um `Dockerfile` para rodar o backend Java e outro para o frontend Angular (usando Nginx ou servidor simples). 
*   **Docker Compose:** Criar um arquivo `docker-compose.yml` que suba o banco de dados (ex: Postgres), o backend e o frontend com apenas um comando (`docker-compose up`).

## [ ] 6. Testes Automatizados Avançados
*   **Backend:** O projeto já possui testes de integração básicos. O próximo passo é aprender a usar **Mockito** para fazer testes unitários isolados da camada `TaskService`. Também explorar **Testcontainers** para testar contra um banco de dados real (como PostgreSQL) que sobe no Docker durante a execução dos testes.
*   **Frontend:** Escrever testes unitários para o `app.component.ts` e serviços usando as ferramentas padrão do Angular (Jasmine/Karma).
