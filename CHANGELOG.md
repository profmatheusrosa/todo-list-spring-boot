# Histórico de Evolução do Sistema (Changelog)

Este documento registra de forma organizada e didática todas as principais melhorias implementadas no projeto `todo-list-spring-boot`, explicando detalhadamente o que foi modificado, o raciocínio por trás de cada decisão e os conceitos envolvidos.

Este Changelog segue o padrão de organização **Keep a Changelog** (separando as alterações em `Added`, `Changed`, `Fixed` e `Security`), mas mantém uma abordagem altamente educativa para facilitar o entendimento de desenvolvedores em aprendizado.

---

## [1.1.0] - 2026-05-22 (Etapa 3: Tratamento de Erros e Validação - Backend)

**Objetivo:** Adicionar validações robustas de dados de entrada na API de Tarefas (Tasks) e implementar um mecanismo centralizado e didático de tratamento de erros no Spring Boot, substituindo telas e retornos genéricos do Spring por respostas padronizadas e limpas.

### Added (Adicionado)

*   **Validação de Entrada (`spring-boot-starter-validation`):**
    *   **O Conceito:** Validações de entrada evitam que dados inválidos ou malformados cheguem ao banco de dados, protegendo o sistema de estados inconsistentes.
    *   **O que foi feito:** Adicionada a dependência do starter de validação no `pom.xml`. No `TaskDTO`, adicionamos anotações declarativas como `@NotBlank` (para garantir que o título não seja nulo nem vazio) e `@Size(min = 3, max = 100)` (para restringir o tamanho do texto entre 3 e 100 caracteres).
*   **Decoplagem com DTO (`TaskDTO`):**
    *   **O Conceito:** DTO (*Data Transfer Object*) é um padrão de projeto para tráfego de dados. Em vez de expormos nossa Entidade do Banco (`Task`) diretamente na API, usamos o DTO. Isso evita vazamento de dados internos de infraestrutura, impede modificações acidentais e mantém a compatibilidade da API externa mesmo que mudemos a tabela de banco no futuro.
    *   **O que foi feito:** Criada a classe `TaskDTO` no pacote `dto/` com atributos idênticos aos da entidade, anotações de validação, além de conversores bidirecionais eficientes (`toEntity()` e construtor de cópia `TaskDTO(Task)`).
*   **Estrutura de Erro Padronizada (`ErrorResponse`):**
    *   **O Conceito:** O Spring Boot por padrão exibe a tela cinza genérica ("WhiteLabel Error Page") ou retornos JSON genéricos e confusos em caso de erros. Um objeto de resposta unificado fornece uma comunicação clara com o Frontend.
    *   **O que foi feito:** Criada a classe `ErrorResponse` no pacote `exception/` para retornar mensagens de erro ricas em JSON contendo:
        *   `timestamp`: Momento exato em que o erro aconteceu.
        *   `status`: Código de status HTTP numérico (ex: 400, 404).
        *   `error`: Nome do erro HTTP (ex: "Bad Request", "Not Found").
        *   `message`: Mensagem principal legível e amigável sobre o ocorrido.
        *   `details`: Uma lista de strings detalhando erros específicos (como erros de validação múltiplos de um formulário).
*   **Exceção Customizada (`ResourceNotFoundException`):**
    *   **O Conceito:** Ter exceções específicas do seu domínio de negócios (em vez de usar exceções genéricas de infraestrutura) torna o fluxo do código muito mais expressivo e fácil de debugar.
    *   **O que foi feito:** Criada a classe `ResourceNotFoundException` para indicar expressamente quando uma tarefa (ou qualquer outro recurso futuro) não for localizada no banco de dados.
*   **Mecanismo Global de Tratamento de Erros (`GlobalExceptionHandler`):**
    *   **O Conceito:** A anotação `@RestControllerAdvice` cria um "interceptador" global na API. Se qualquer endpoint lançar uma exceção mapeada, o Spring redireciona automaticamente para esta classe, que "trata" o erro e monta a resposta HTTP ideal. Isso funciona como uma rede de segurança centralizada, eliminando a necessidade de blocos repetitivos de `try-catch` em cada controller.
    *   **O que foi feito:** Criada a classe `GlobalExceptionHandler` interceptando:
        *   `MethodArgumentNotValidException` (erros disparados quando o `@Valid` falha): Retorna status `400 Bad Request` com os detalhes específicos de qual campo falhou.
        *   `ResourceNotFoundException` (recurso inexistente): Retorna status `404 Not Found` com a mensagem amigável criada no controller.
        *   `Exception` (qualquer erro genérico/inesperado): Captura e retorna `500 Internal Server Error`, mascarando o stacktrace técnico para o cliente externo por motivos de segurança.

### Changed (Modificado)

*   **Ajustes no Fluxo do Controlador (`TaskController`):**
    *   **Didática:** Os endpoints agora operam com DTOs em vez das entidades de banco. Usamos a anotação `@Valid` nas entradas (POST e PUT) para que o Spring ative automaticamente o motor de validações do DTO antes de executar o método.
    *   **O que foi feito:** Substituição do tipo de retorno de `Task` para `TaskDTO`. O endpoint agora lança `ResourceNotFoundException` caso o registro não exista, permitindo o acionamento do tratador global de erros.
*   **Ajuste e Robustez de Testes (`TaskControllerIntegrationTest`):**
    *   **Didática:** Com a inclusão prévia da segurança com JWT, os testes de integração do controlador começaram a falhar com erro `403 Forbidden` (Acesso Proibido). O teste precisava simular um fluxo de usuário real para conseguir consumir as rotas protegidas da API.
    *   **O que foi feito:** Adicionada rotina de inicialização (`@BeforeEach`) que registra dinamicamente um usuário único e efetua login para extrair o token JWT. Esse token é injetado no cabeçalho `Authorization` de todas as chamadas de teste. Também criamos cenários robustos para testar a rejeição de tarefas inválidas (retorno 400 estruturado) e busca por IDs inexistentes (retorno 404 estruturado).

---

## [1.0.0] - Em Andamento (Etapa 1: Implementação de Segurança com JWT)

**Objetivo:** Adicionar controle de acesso à aplicação para que usuários não autenticados não possam manipular ou visualizar as tarefas de outros usuários.

### Added (Adicionado)

#### 1. Backend (Spring Boot)
*   **Abordagem de Segurança Stateless:**
    *   **O Conceito:** APIs modernas utilizam a autenticação baseada em tokens (JWT) para evitar que o servidor guarde o estado da sessão na memória. A cada requisição, o cliente envia seu token assinado criptograficamente, e o servidor valida sua autenticidade de forma independente.
    *   **O que foi feito:** Adicionadas as dependências do `spring-boot-starter-security` e as bibliotecas do `jjwt` no `pom.xml`.
*   **Modelo de Usuários e Persistência:**
    *   **O que foi feito:** Criação da classe `User` contendo dados de credenciais criptografadas e a respectiva interface `UserRepository` para realizar consultas no banco de dados.
*   **Objetos de Transferência (DTOs):**
    *   **O que foi feito:** Criação das classes `AuthRequest` (para coletar username/password no corpo do login) e `AuthResponse` (para devolver o token JWT gerado).
*   **Infraestrutura de Segurança Avançada:**
    *   **`SecurityConfig`:** Configuração central que desabilita o CSRF (desnecessário com autenticação JWT), define rotas públicas (como cadastro/login e Swagger) e bloqueia todas as outras para usuários não autenticados.
    *   **`UserDetailsServiceImpl`:** Conecta a autenticação do Spring Security com as buscas de usuário no nosso banco de dados.
    *   **`JwtAuthenticationFilter`:** Filtro customizado que roda antes de cada requisição chegar aos controladores. Ele lê o cabeçalho `Authorization`, valida a assinatura do token e, se tudo estiver correto, registra o usuário autenticado no contexto do Spring.
    *   **`JwtUtil`:** Classe utilitária focada nas funções criptográficas de gerar tokens expiráveis e extrair dados seguros a partir deles.
*   **Controller de Autenticação (`AuthController`):**
    *   **O que foi feito:** Endpoint para registrar novos usuários (criptografando a senha com `BCryptPasswordEncoder`) e para efetuar o login (gerando o JWT após autenticação bem-sucedida).

#### 2. Frontend (Angular)
*   **Telas de Entrada:**
    *   **O que foi feito:** Criação das telas e formulários didáticos de Login e Registro.
*   **Serviço de Autenticação (`AuthService`):**
    *   **O que foi feito:** Serviço central que salva o token no `localStorage` do navegador para manter o usuário logado e possui métodos para verificar o status da sessão.
*   **Interceptor de Requisições (`HttpInterceptor`):**
    *   **O Conceito:** Em vez de adicionar manualmente o cabeçalho do token JWT a cada chamada HTTP para a API, criamos um interceptor que funciona como um "pedágio". Ele captura qualquer requisição enviada pelo Angular e anexa automaticamente o cabeçalho `Authorization: Bearer <token>` de forma transparente.
*   **Proteção de Rotas com Route Guards (`CanActivate`):**
    *   **O Conceito:** Impede que usuários não autenticados visualizem as telas protegidas digitando o endereço diretamente na barra do navegador, redirecionando-os automaticamente para a tela de Login.

---

## [0.1.0] - 2026-05-22

### Added (Adicionado)
*   **Melhorias Futuras:** Criação do arquivo de mapeamento técnico `docs/MELHORIAS_FUTURAS.md` contendo a lista e o planejamento didático dos próximos passos e evoluções recomendadas para o projeto.
