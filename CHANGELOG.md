# Histórico de Evolução do Sistema (Changelog)

Este documento registra todas as principais melhorias implementadas no projeto, explicando em detalhes o que foi modificado em cada etapa, as ferramentas utilizadas e o raciocínio por trás da solução.

---

## [Em Andamento] Etapa 1: Implementação de Segurança com JWT

**Objetivo:** Adicionar controle de acesso à aplicação para que usuários não autenticados não possam manipular ou visualizar as tarefas de outros usuários.

### O que está sendo feito:
A segurança do projeto foi dividida em duas grandes áreas: Backend (Spring Boot) e Frontend (Angular).

#### 1. Backend (Spring Boot)
Foi escolhida a abordagem _stateless_ (sem estado na sessão do servidor) utilizando JSON Web Tokens (JWT).

*   **Adição de Dependências:** Inclusão do `spring-boot-starter-security` para fornecer a infraestrutura de segurança do Spring, além da biblioteca `jjwt` para a geração e validação dos tokens.
*   **Novas Entidades e Repositórios:** 
    *   Criação do modelo `User` (`src/main/java/com/example/todo/model/User.java`) para armazenar as credenciais (username, password).
    *   Criação do `UserRepository` (`src/main/java/com/example/todo/repository/UserRepository.java`) para manipular os dados de usuários no banco de dados.
*   **Objetos de Transferência (DTOs):**
    *   Criação de `AuthRequest` e `AuthResponse` no pacote `dto/` para padronizar as requisições de login e o retorno do token e dados da sessão.
*   **Camada de Segurança:**
    *   **`SecurityConfig`:** Configuração central que desabilita o CSRF (comum para APIs baseadas em tokens), define quais rotas são públicas (como `/api/auth/**`) e quais exigem autenticação.
    *   **`UserDetailsServiceImpl`:** Implementação que indica ao Spring Security como buscar os dados do usuário no banco (`UserRepository`) durante a autenticação.
    *   **Filtros Customizados:** Implementação do `JwtAuthenticationFilter`, que intercepta as requisições HTTP para ler o token do cabeçalho `Authorization`, extrair o usuário e registrar a sessão de forma segura no contexto do Spring Security.
    *   **Utilitários JWT:** A classe `JwtUtil` gerencia a geração de tokens após o login e a extração do username durante as requisições.
*   **Novo Controller (Auth):** O `AuthController` foi criado para receber requisições de login (retornando o token JWT) e de cadastro de novos usuários.

#### 2. Frontend (Angular)
O frontend precisa passar a enviar a "chave" (o token) para conseguir se comunicar com a API.

*   **Novas Telas:** Componentes de Login e Cadastro para coletar os dados e enviá-los ao novo endpoint de `Auth` no backend.
*   **Gestão de Estado (AuthService):** Um serviço focado em armazenar e recuperar o token JWT (geralmente no `localStorage`) e que saiba dizer à aplicação se o usuário está ou não logado.
*   **Interceptors:** Configuração do `HttpInterceptor`. Ele pega toda requisição HTTP feita pelo `TaskService` e automaticamente anexa o cabeçalho `Authorization: Bearer <token>`, evitando trabalho repetitivo de anexar o token manualmente.
*   **Route Guards (`CanActivate`):** Proteção das rotas no nível do navegador. Se um usuário não logado tentar acessar diretamente `/tasks`, ele será redirecionado para a página inicial de login.

---

## Outras Melhorias
*   Criação do arquivo `docs/MELHORIAS_FUTURAS.md` para documentar ideias de evolução técnica do projeto.
