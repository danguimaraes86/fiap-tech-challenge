# Tech Challenge

Bem-vindo(a) a documentação oficial da API desenvolvidas para o **Tech Challenge Fase 5**. Nesta documentação, você
encontrará detalhes abrangentes sobre a API, que foi desenvolvida como parte deste projeto.

## Integrantes

- Daniel Reis de Medeiros Guimarães
- Felipe Ferreira de Paula
- João Pedro Gomes
- Rafael Souza Araujo

## Tecnologias e Ferramentas

- Java 17
- Gradle
- Spring Web
- Postgres
- MongoDB
- Spring Data JPA
- Spring Data MongoDB
- Validation
- Lombok
- Postman
- IntelliJ
- Docker
- Git
- GitHub

## Como inicar o projeto

Para inicar o projeto, utilize o `gradle`. Caso não tenha o Gradle instalado, você pode utilizar o wrapper que
acompanha o projeto. Neste caso, entre na pasta raiz da Fase-05 e utilize o comando `./gradlew`.
É necessário estar com `Docker` rodando para conexão com banco de dados.

| Comando                | Descrição                           | Requisitos                  |
|------------------------|-------------------------------------|-----------------------------|
| `docker-compose up -d` | Subir os serviços de banco de dados | Docker instalado na máquina |
| `./gradlew bootRun`    | Realização dos Testes Unitários     | MongoDB rodando             |

## Testes

### Comandos

Para realizar os testes, utilize o `gradle`. Caso não tenha o Gradle instalado, você pode utilizar o wrapper que
acompanha o projeto. Neste caso, entre na pasta raiz da Fase-04 e utilize o comando `./gradlew`.

| Comando                     | Descrição                                                  | Requisitos                  |
|-----------------------------|------------------------------------------------------------|-----------------------------|
| `./gradlew test`            | Realização dos Testes Unitários                            | Nenhum                      |
| `docker-compose up -d`      | Subir os serviços necessários para os Testes de Integração | Docker instalado na máquina |

### Relatórios

Após a execução dos testes, os relatórios gerados pelo JaCoCo estarão disponíveis na pasta `fase-05/build/reports`.

## Endpoints

### Endpoints – Carrinho

| Método | Url                                       | Descrição                                                 |
|--------|-------------------------------------------|-----------------------------------------------------------|
| GET    | /carrinhos                                | Faz a busca de um carrinho pelo código de usuario         |                             
| POST   | /carrinhos/adicionarproduto               | Adiciona um novo produto ao carrinho                      |                             
| POST   | /carrinhos/finalizarcompra                | Efetua a comrpa dos produtos do cliente                   | 
| POST   | /carrinhos/novo                           | Cria um novo carrinho                                     | 

#### Exemplos de entrada

#### POST /carrinhos/adicionarproduto

```json
{
  "produtoId": "28ca6ff5-dada-4cc9-82fba3945edfa335",
  "quantidade": 5
}
```

### Endpoints – Produtos

| Método | Url                          | Descrição                                                                        |
|--------|------------------------------|----------------------------------------------------------------------------------|
| GET    | /produtos                    | Faz a busca de todos os produtos                                                 |                             
| GET    | /produtos/{id}               | Faz a busca de um produto a partir do seu id                                     |                             
| GET    | /produtos/busca              | Faz a busca de produtos pela descrição                                           | 
| POST   | /produtos/checarsetemestoque | Verifica se um determinado produto tem estoque                                   |    
| POST   | /produtos                    | Insere um novo produto no Eommerce                                               | 
| POST   | /produtos/estoque/{id}       | Faz a alteração de estoque de um produto, adicionando ou removendo mais produtos |                             

#### Exemplos de entrada

#### POST /produtos

```json
{
  "nome": "relogio",
  "preco": 10.0,
  "estoque": 0,
  "descricao": "relogio inteligente"
}
```

### Endpoints – Usuarios

| Método | Url             | Descrição                                            |
|--------|-----------------|------------------------------------------------------|
| GET    | /usuarios       | Faz a busca de todos os usuarios                     |                             
| GET    | /usuarios/busca | AFaz a busca de um usuario específico a partir do id |                             
| POST   | /usuarios/novo  | Cria um novo usuario                                 | 

#### Exemplos de entrada

#### POST /produtos

```json
{
  "email": "rafael@fiap.com.br",
  "role": "ADMIN",
  "nome": "Rafael",
  "password": "teste01"
}
```

                           



