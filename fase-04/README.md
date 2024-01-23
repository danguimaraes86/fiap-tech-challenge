# Tech Challenge

Bem-vindo(a) a documentação oficial da API desenvolvidas para o **Tech Challenge Fase 4**. Nesta documentação, você
encontrará detalhes abrangentes sobre a API, que foi desenvolvida como parte deste projeto.
Esta API deve armazenar vídeos para um serviço de streaming, permitir que usuários favoritem alguns vídeos e recebam
recomendações com base nas categorias dos seus favoritos. Adicionalmente, temos um endpoint com algumas estatísticas
sobre os vídeos armazenados.

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

## Explore

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
| GET    | /videos/{id}/watch                        | Retorna um vídeo em endpoint reativo para streaming       |                             

### Exemplos de entrada

##### POST /videos

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

##### PUT /video/{id}

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

| Método | Url                               | Descrição                            | 
|--------|-----------------------------------|--------------------------------------|
| GET    | /usuarios                         | Retorna todos os video cadastrados   |
| GET    | /usuarios/{id}                    | Retorna um Usuário com ID específico |
| POST   | /usuarios/                        | Cadastra um Usuário                  |
| POST   | /usuarios/{id}/adicionarFavoritos | Cadastra os Favoritos de um Usuário  |

### Exemplos de entrada

##### POST /usuarios

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

##### PUT /pessoa/{id}/adicionarFavoritos

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

### Exemplos de entrada

##### GET /estatisticas

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
