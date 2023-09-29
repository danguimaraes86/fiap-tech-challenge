# Tech Challenge 

Bem-vindo(a) a documentação oficial da API desenvolvidas para o Tech Challenge fase 3.

## Tecnologias e Ferramentas
- Java 17
- Gradle
- H2 Database
- Spring Data JPA
- Validation (Bean Validation with Hibernate validator)
- Lombok
- Spring Boot DevTools
- Spring Web
- Postman
- IntelliJ
- Git
- GitHub

## Explore

### Api – Eletrodomésticos

| Método | Url | Descrição | Modelo de Requisição válido |
| ------ | --- | ----------- | ------------------------- |
| POST    | /eletrodomestico | Cadastra um eletrodoméstico | [JSON](#createEletro)|
| PUT   | /eletrodomestico/{id} | Atualiza um eletrodoméstico com Id específico | [JSON](#updateEletro) |
| GET    | /eletrodomestico/{id} | Retorna um eletrodoméstico com Id específico | |
| GET    | /eletrodomestico | Retorna todos os eletrodomésticos cadastrados | |
| DELETE    | /eletrodomestico/{id} | Deleta um eletrodoméstico com Id específico | |

### Exemplos de entrada

##### <a id="createEletro"> POST - /eletrodomestico</a>
```json
{
 "nome": "Geladeira",
 "potencia": "500w",
 "modelo": "Eletrolux",
 "fabricacao": "2019-12-09"
}
```

##### <a id="updateEletro">PUT - /eletrodomestico/{id}</a>
```json
{
 "nome": "Fogão",
 "potencia": "3000w",
 "modelo": "Brastemp",
 "fabricacao": "2019-12-09"
}
```
### Api – Pessoas

| Método | Url | Descrição | Modelo de Requisição válido |
| ------ | --- | ----------- | ------------------------- |
| POST    | /pessoa | Cadastra uma pessoa | [JSON](#createPessoa)|
| PUT   | /pessoa/{id} | Atualiza uma pessoa com Id específico | [JSON](#updatepessoa) |
| GET    | /pessoa/{id} | Retorna uma pessoa com Id específico | |
| GET    | /pessoa | Retorna todos as pessoas cadastrados | |
| DELETE    | /pessoa/{id} | Deleta uma pessoa com Id específico | |

### Exemplos de entrada

##### <a id="createPessoa"> POST - /pessoa</a>
```json
{
 "nome": "Marcos Leonardo",
 "dataNascimento": "2003-05-02",
 "sexo": "Masculino",
 "parentesco": "Filho"
}
```

##### <a id="updatepessoa">PUT - /pessoa/{id}</a>
```json
{
 "nome": "Rodrigo Fernandez",
 "dataNascimento": "2003-05-02",
 "sexo": "Masculino",
 "parentesco": "Irmão"
}
```
### Api – Endereços

| Método | Url | Descrição | Modelo de Requisição válido |
| ------ | --- | ----------- | ------------------------- |
| POST    | /enderecos | Cadastra um endereço | [JSON](#createEndereco)|
| PUT   | /enderecos/{id} | Atualiza um endereço com Id específico | [JSON](#updateEndereco) |
| GET    | /enderecos/{id} | Retorna um endereço com Id específico | |
| GET    | /enderecos | Retorna todos os endereços cadastrados | |
| DELETE    | /enderecos/{id} | Deleta um endereço com Id específico | |

### Exemplos de entrada

##### <a id="createEndereco"> POST - /enderecos</a>
```json
{
 "nomeInstalacao": "Casa de Campo",
 "rua": "Rua Princesa Isabel",
 "numero": "70",
 "bairro": "Urbano Caldera",
 "cidade": "Santos",
 "estado": "São Paulo"
}
```

##### <a id="updateEndereco">PUT - /enderecos/{id}</a>
```json
{
 "nomeInstalacao": "Escritório",
 "rua": "Rua Edson Arantes",
 "numero": "100",
 "bairro": "Urbano Caldera",
 "cidade": "Santos",
 "estado": "São Paulo"
}
```



