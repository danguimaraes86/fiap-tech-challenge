# Tech Challenge 

Bem-vindo(a) a documentação oficial da API desenvolvidas para o Tech Challenge fase 2. Nesta documentação, você encontrará detalhes abrangentes sobre a API, que foi desenvolvida como parte deste projeto. Esta API será responsável por gerenciar dados de consumo
elétrico de eletrodomésticos, que através de cálculos disponibilizará informações e alertas aos usuários. Essa ponte entre API e usuário será intermediada por um portal que vai consumir esta API e apresentar as informações devidas. No entanto nesta etapa os entregáveis possuem apenas funcionalidades CRUD, e relacionamentos entre entidades aprendidas no decorrer da fase 2 do curso.

## Tecnologias e Ferramentas
- Java 17
- Maven
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

### Endpoints – Eletrodomésticos

| Método | Url | Descrição | Modelo de Requisição válido |
| ------ | --- | ----------- | ------------------------- |
| POST    | /eletrodomestico | Cadastra um eletrodoméstico | [JSON](#createEletro)|
| PUT   | /eletrodomestico/{id} | Atualiza um eletrodoméstico com Id específico | [JSON](#updateEletro) |
| GET    | /eletrodomestico/{id} | Retorna um eletrodoméstico com Id específico | |
| GET    | /eletrodomestico | Retorna todos os eletrodomésticoscadastrados com filtros específicos | |
| DELETE    | /eletrodomestico/{id} | Deleta um eletrodoméstico com Id específico | |

### Exemplos de entrada

##### <a id="createEletro"> POST - /eletrodomestico</a>
```json
{
 "nome": "Geladeira",
 "potencia": "150W",
 "modelo": "XYZ",
 "fabricacao": "2022-01-15",
 "usuarioId": 1,
 "enderecoId": "1",
 "consumidoresIds": [1, 2]
}

{
 "nome": "Geladeira",
 "potencia": "150W",
 "modelo": "XYZ",
 "fabricacao": "2022-01-15",
 "usuarioId": 1,
 "enderecoId": "1",
 "consumidoresIds": []
}
```

##### <a id="updateEletro">PUT - /eletrodomestico/{id}</a>
```json
{
 "nome": "Geladeira",
 "potencia": "300W",
 "modelo": "XYZ",
 "fabricacao": "2022-01-15",
 "enderecoId": "2"
}
```
### Endpoints – Usuário

| Método | Url | Descrição | Modelo de Requisição válido |
| ------ | --- | ----------- | ------------------------- |
| POST    | /usuario | Cadastra um usuário | [JSON](#createPessoa)|
| PUT   | /usuario/{id} | Altera a senha do usuário com id específico | [JSON](#updatepessoa) |

### Exemplos de entrada

##### <a id="createPessoa"> POST - /pessoa</a>
```json
{
 "email": "email@email.com",
 "senha": "123456"
}
```

##### <a id="updatepessoa">PUT - /pessoa/{id}</a>
```json
{
 "senha": "novaSenha"
}
```
### Endpoints – Consumidor

| Método | Url | Descrição | Modelo de Requisição válido |
| ------ | --- | ----------- | ------------------------- |
| POST    | /consumidor | Cadastra uma consumidor | [JSON](#createConsumidor)|
| PUT   | /consumidor/{id} | Atualiza um consumidor com Id específico | [JSON](#updateConsumidor) |
| GET    | /consumidor/{id} | Retorna um consumidor com Id específico | |
| GET    | /consumidor | Retorna todos os cosumidores cadastrados com filtros definidos | |
| DELETE    | /consumidor/{id} | Deleta um consumidor com Id específico | |

### Exemplos de entrada

##### <a id="createConsumidor"> POST - /pessoa</a>
```json
{
 "nome": "Julio Furch",
 "dataNascimento": "2003-05-02",
 "sexo": "Masculino",
 "parentesco": "Filho",
 "eletrodomesticosIds": [1,2],
 "usuarioId": "1"
}
{
 "nome": "Julio Furch",
 "dataNascimento": "2003-05-02",
 "sexo": "Masculino",
 "parentesco": "Filho",
 "usuarioId": "1"
}

```

##### <a id="updateConsumidor">PUT - /pessoa/{id}</a>
```json
{
 "nome": "Rodrigo Fernandez",
 "dataNascimento": "2003-05-02",
 "sexo": "Masculino",
 "parentesco": "Sobrinho"
}
```


### Endpoints – Endereços

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



