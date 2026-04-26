# Exemplos de Requisições (curl)

Abaixo estão exemplos básicos para testar os endpoints da API. Substitua `localhost:8080` se usar outra porta.

1) Listar tarefas

```
curl -s -X GET http://localhost:8080/api/tasks
```

2) Criar uma tarefa

```
curl -s -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"title":"Comprar pão","completed":false}'
```

3) Obter tarefa por id

```
curl -s -X GET http://localhost:8080/api/tasks/1
```

4) Atualizar tarefa

```
curl -s -X PUT http://localhost:8080/api/tasks/1 \
  -H "Content-Type: application/json" \
  -d '{"title":"Comprar pão e leite","completed":true}'
```

5) Deletar tarefa

```
curl -s -X DELETE http://localhost:8080/api/tasks/1
```

Também é útil importar um collection do Postman para facilitar testes interativos.
