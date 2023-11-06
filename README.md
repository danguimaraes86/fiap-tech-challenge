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

### Sistema de Notificação
Nossa solução para a implementar o sistema de notificação de forma mock foi criar um serviço `Schedule` do próprio Spring,
por meio da classe `ScheduleTasks` e dos métodos `notificarTicketsFlexiveis()` e `notificarTicketsFixo()`.
Pensamos neste sistema como um meio de simular a aplicação em produção e com uso de **serviços da AWS**, de produtos como **o ECS
e Lambda**.

### Endpoints – Condutor

| Método | Url                               | Descrição                                                      | Saída HttpStatus |
|--------|-----------------------------------|----------------------------------------------------------------|------------------|
| GET    | /condutor                         | Faz a busca de todos os condutores                             | OK (200)         |
| GET    | /condutor/{id}                    | Faz a busca por um condutor específico                         | OK (200)         |
| GET    | /condutor/tickets?cpf=48153023837 | Faz a busca do ticket de estacionamento do condutor específico | OK (200)         |
| POST   | /condutor                         | Cadastra um novo condutor                                      | OK (200)         |
| PUT    | /condutor/{cpf}                   | Alterar informações do condutor                                | OK (200)         |
| DELETE | /condutor/{cpf}                   | Excluir um condutor                                            | No Content (204) | 

### Exemplos de entrada

##### POST - /condutor
```json
{
  "nome": "Daniel", /* String required */
  "cpf": "99999999999", /* String required */
  "email": "teste@teste", /* String required */
  "celular": "(99) 98888-7777", /* String optional */
  "formaPagamento": "pix" /* String optional */
}
```

##### PUT - /condutor/{cpf}
```json
{
  "nome": "Daniel", /* String required */
  "cpf": "99999999999", /* String required */
  "email": "teste@teste", /* String required */
  "celular": "(99) 98888-7777", /* String optional */
  "formaPagamento": "pix" /* String optional */
}
```
### Endpoints - Veículos

| Método | Url              | Descrição                                                           | Saída HttpStatus |
|--------|------------------|---------------------------------------------------------------------|------------------|
| GET    | /veiculo         | Faz a busca de todos os veículos                                    | OK (200)         |
| GET    | /veiculo/{id}    | Faz a busca de um veículo específico a partir do id                 | OK (200)         |
| POST   | /veiculo         | Registra um novo veiculo                                            | OK (200)         |
| PUT    | /veiculo/{placa} | Faz alterações no registro de um veículo a partir da placa do mesmo | OK (200)         |
| DELETE | /veiculo/{placa} | Deleta um veículo a partir da sua placa                             | No Content (204) |

### Exemplos de entrada

##### POST - /veiculo
```json
{
  "headers": {
    "condutorCpf": "99999999999" /* String required */
  },
  "body": {
    "marca": "VW", /* String required */
    "modelo": "Polo", /* String required */
    "placa": "bbb2222" /* String required */
  }
}
```

##### PUT - /veiculo/{placa}
```json
{
  "headers": {
    "condutorCpf": "99999999999" /* String required */
  },
  "body": {
    "marca": "VW", /* String required */
    "modelo": "Polo", /* String required */
    "placa": "bbb2222" /* String required */
  }
}
```
### Endpoints – Tickets

| Método | Url                          | Descrição                                                                               | Saída HttpStatus |
|--------|------------------------------|-----------------------------------------------------------------------------------------|------------------|
| POST   | /ticket/registrarEntrada     | Faz o registro da entrada de um novo veículo                                            | OK (200)         |
| POST   | /ticket/registrarSaida       | Faz o registro da saída de um veículo                                                   | OK (200)         |
| GET    | /ticket                      | Faz a busca de todos os tickets                                                         | OK (200)         |
| GET    | /ticket/{uuid}               | Faz a busca de um ticket a partir do seu uuid                                           | OK (200)         |
| GET    | /ticket/emitirRescibo/{uuid} | Gera o ticket para que o cliente veja as informações entre o período de entrada e saída | OK (200)         |

### Exemplos de entrada

##### POST - /ticket/registrarEntrada
```json
{
  "condutorCpf": "99999999999", /* String required */
  "placaVeiculo": "bbb2222", /* String required */
  "tipoCobranca": "flexivel", /* String (flexivel ou fixo) optional */
  "permanencia": 3 /* Number optional */
}
```

##### PUT - /ticket/registrarSaida
```json
{
  "condutorCpf": "99999999999", /* String required */
  "placaVeiculo": "bbb2222", /* String required */
  "tipoCobranca": "flexivel", /* String (flexivel ou fixo) optional */
  "permanencia": 3 /* Number optional */
}
```



