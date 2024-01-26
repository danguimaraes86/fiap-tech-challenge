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
- Spring Web Reactive
- MongoDB
- Spring Data MongoDB
- Spring Data Reactive MongoDB
- Validation (Bean Validation with Hibernate validator)
- Lombok
- Postman
- IntelliJ
- Docker
- Git
- GitHub

## Como inicar o projeto

Para inicar o projeto, utilize o `gradle`. Caso não tenha o Gradle instalado, você pode utilizar o wrapper que
acompanha o projeto. Neste caso, entre na pasta raiz da Fase-04 e utilize o comando `./gradlew`.
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
| `./gradlew integrationTest` | Realização dos Testes de Integração                        | Docker rodando com MongoDB  |
| `docker-compose up -d`      | Subir os serviços necessários para os Testes de Integração | Docker instalado na máquina |

### Relatórios

Após a execução dos testes, os relatórios gerados pelo JaCoCo estarão disponíveis na pasta `fase-04/build/reports`.

## Endpoints

### Endpoints – Videos

| Método | Url                                       | Descrição                                                 |
|--------|-------------------------------------------|-----------------------------------------------------------|
| GET    | /videos                                   | Retorna todos os video cadastrados                        |                             
| GET    | /videos/{id: ObjectId}                    | Retorna um vídeo com ID específico                        |                             
| POST   | /videos                                   | Cadastra um vídeo                                         | 
| PUT    | /videos/{id: ObjectId}                    | Atualiza um vídeo com ID específico                       | 
| DELETE | /videos/{id: ObjectId}                    | Deleta um vídeo com ID específico                         |                             
| GET    | /videos/busca                             | Retorna uma lista de vídeos com base nos filtros de busca |                             
| GET    | /videos/categoria/{codeCategoria: String} | Retorna uma lista de vídeos com base na categoria         |                             
| GET    | /videos/{id: ObjectId}/watch              | Retorna um vídeo em endpoint reativo para streaming       |                             

#### Exemplos de entrada

#### POST /videos

```json
{
  "$schema": "http://json-schema.org/draft-07/schema#",
  "title": "Video Schema Request Body",
  "type": "object",
  "properties": {
    "titulo": {
      "type": "string"
    },
    "descricao": {
      "type": "string"
    },
    "url": {
      "type": "string"
    }
  },
  "required": [
    "titulo",
    "descricao",
    "url"
  ]
}
```

#### PUT /video/{id}

```json
{
  "$schema": "http://json-schema.org/draft-07/schema#",
  "title": "Video Schema Request Body",
  "type": "object",
  "properties": {
    "titulo": {
      "type": "string"
    },
    "descricao": {
      "type": "string"
    },
    "url": {
      "type": "string"
    }
  },
  "required": [
    "titulo",
    "descricao",
    "url"
  ]
}
```

### Endpoints – Usuário

| Método | Url                                         | Descrição                                          | 
|--------|---------------------------------------------|----------------------------------------------------|
| GET    | /usuarios                                   | Retorna todos os video cadastrados                 |
| GET    | /usuarios/{id: ObjectId}                    | Retorna um Usuário com ID específico               |
| POST   | /usuarios                                   | Cadastra um Usuário                                |
| POST   | /usuarios/{id: ObjectId}/adicionarFavoritos | Cadastra os Favoritos de um Usuário                |
| GET    | /usuarios/{id: ObjectId}/recomendacoes      | Retorna uma lista de vídeos com base nos favoritos |

#### Exemplos de entrada

#### POST /usuarios

```json
{
  "$schema": "http://json-schema.org/draft-07/schema#",
  "title": "Usuário Schema Request Body",
  "type": "object",
  "properties": {
    "favoritos": {
      "type": "array",
      "items": {
        "type": "string"
      }
    },
    "nome": {
      "type": "string"
    }
  },
  "required": [
    "nome"
  ]
}
```

#### PUT /pessoa/{id}/adicionarFavoritos

```json
{
  "$schema": "http://json-schema.org/draft-07/schema#",
  "title": "Favorito Schema Request Body",
  "type": "object",
  "properties": {
    "favoritos": {
      "type": "array",
      "items": {
        "type": "string"
      }
    }
  },
  "required": [
    "favoritos"
  ]
}
```

### Endpoints – Estatísticas

| Método | Url           | Descrição                          | 
|--------|---------------|------------------------------------|
| GET    | /estatisticas | Retorna as estatísticas do sistema |

#### Exemplos de saída

#### GET /estatisticas

```json
{
  "$schema": "http://json-schema.org/draft-07/schema#",
  "title": "Estatística Schema Response",
  "type": "object",
  "properties": {
    "qntTotalVideos": {
      "type": "number"
    },
    "qntVideosFavoritos": {
      "type": "number"
    },
    "mediaVizualizacoes": {
      "type": "number"
    }
  }
}
```
