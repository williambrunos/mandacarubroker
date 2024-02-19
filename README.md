# Documentação da API MandaCaru Broker

A API MandaCaru Broker fornece endpoints para gerenciar ações (stocks) em um sistema de corretagem de valores.

## Recursos Disponíveis

### Recurso: Stocks

Endpoints para operações CRUD em ações.

#### 1. Listar todas as ações

Retorna uma lista de todas as ações disponíveis no sistema.

- **URL:**
````bash
GET /stocks 
````

- **Códigos de Resposta**
  - 200 OK: Retorna a lista de ações com sucesso.

#### 2. Obter uma ação por ID

Retorna os detalhes de uma ação específica com base no seu ID.

- **URL**

```bash
GET /stocks/{id}
```
- **Parâmetros de Caminho (Path Parameters)**
  - `id` (string): O ID único da ação a ser recuperada.
    - O parâmetro `id` deve ser aquele gerado automaticamente pelo sistema ao criar uma ação.

- **Códigos de Resposta**
  - 200 OK: A ação foi encontrada e detalhes são retornados.
  - 404 Not Found: A ação com o ID fornecido não foi encontrada.

#### 3. Criar uma nova ação

Cria uma nova ação com os dados fornecidos.

- **URL**
````bash
POST /stocks 
````

- **Corpo da Requisição (Request Body)**
  - `name` (string, obrigatório): O nome da ação.
    - O nome da ação deve respeitar aos padrões de nomenclatura de ativos da B3.
  - `price` (double, obrigatório): O preço da ação.
    - O valor do preço da ação deve ser um número real não negativo.

- **Códigos de Resposta**
  - 201 Created: A ação foi criada com sucesso.
  - 400 Bad Request: Os dados da requisição são inválidos.

#### 4. Atualizar uma ação existente

Atualiza os detalhes de uma ação existente com base no seu ID.

- **URL**

````bash
PUT /stocks/{id}
````

- **Parâmetros de Caminho (Path Parameters)**
  - `id` (string): O ID único da ação a ser atualizada.
    - O parâmetro `id` deve ser aquele gerado automaticamente pelo sistema ao criar uma ação.


- **Corpo da Requisição (Request Body)**
  - `name` (string, opcional): O novo nome da ação.
    - O nome da ação deve respeitar aos padrões de nomenclatura de ativos da B3.
  - `price` (double, opcional): O novo preço da ação.
    - O valor do preço da ação deve ser um número real não negativo.

- **Códigos de Resposta**
  - 201 Created: A ação foi atualizada com sucesso.
  - 400 Bad Request: Os dados da requisição são inválidos.
  - 404 Not Found: A ação com o ID fornecido não foi encontrada.

#### 5. Excluir uma ação existente

Exclui uma ação existente com base no seu ID.

- **URL**

````bash
DELETE /stocks/{id}
````

- **Parâmetros de Caminho (Path Parameters)**
  - `id` (string): O ID único da ação a ser excluída.
    - O parâmetro `id` deve ser aquele gerado automaticamente pelo sistema ao criar uma ação.

- **Códigos de Resposta**
  - 200 OK: A ação foi excluída com sucesso.
  - 404 Not Found: A ação com o ID fornecido não foi encontrada.

## Como executar a aplicação localmente

### 1. Realize um clone do projeto

Em sua pasta local, realize um clone do projeto utilizando o comando abaixo:

````bash
git clone https://github.com/williambrunos/mandacarubroker.git
cd mandacarubroker
````

### 2. Crie e execute a instância docker com PostgreSQL

Crie e execute a instância docker com o banco de dados postgres com o comando abaixo na raiz do projeto:

````bash
docker compose up -d
````

Aguarde a criação da instância docker.

### 3. Execute a aplicação com SpringBoot

Execute a aplicação do broker no arquivo ``MandacarubrokerApplication.java``

