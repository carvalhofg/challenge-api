# Challenge-Api

Desafio de desenvolvimento de API REST.

API desenvolvida com Spring Boot.

Executar a API:

```
$ mvn install
$ mvn clean compile
$ mvn spring-boot:run
```

Executar os testes automatizados:

```
$ mvn clean package
```
Ou
```
$ mvn clean compile test
```

## Detalhes

### Banco de Dados

Os dados da API foram salvos em H2.

Duas Ordens de Serviço já estão cadastradas no banco.

Existem duas tabelas de usuários, uma chamada de Responsible, contendo os responsáveis pela ordem de serviço,
e outra chamada Customer, relacionada aos clientes.

Existem dois Responsáveis cadastrados -> Responsible1 e Responsible2.</br>
Existem dois Clientes cadastrados -> Customer1 e Customer2.

### Mapeamento dos Endpoints

Os Endpoints foram documentados no Swagger.

Para Acessar, basta subir a aplicação executando o comando 
```
$ mvn spring-boot:run
```
E acessar a url http://localhost:8080/swagger-ui.html

### Enum Status da Ordem

Existem 4 Status de uma Ordem de Serviço. Ela é atualizada via método PUT.

Quando uma Ordem é criada ela já vem com Status REGISTERED.</br>

Os 4 estados possíveis são:

+ REGISTERED;
+ RUNNING;
+ WAITINGFORPIECES;
+ FINISHED;


## Autenticação

A API usa o Spring Security para segurança e faz autenticação via token do tipo Bearer.

Os Responsáveis que possuem acesso na API. O Responsible1 e Responsible2 possuem as seguintes credenciais:

|  Responsável | Username | Password |
| ------ | ----------- | ----------- |
| Responsible1 | responsibleusr1 | 123456 |
| Responsible2 | responsibleusr2 | 123456 |

Para gerar o token de acesso aos endpoints com restrição de acesso, é necessário mandar uma requisição para o endpoint

`/auth`

passando no body, o username e password, como descrito na tabela acima.

Ao fazer isso, é retornado um responseBody com o valor do token.

Para acessar os endpoints que requerer autenticação, é necessário passar um header Authorization na requisição com o valor:

`Bearer codigoDoTokenGerado`

## Testes automatizados

Foram implementados testes automatizados utlizando MockMvc mapeando todos os endpoints da aplicação, ou seja, as classes Controllers. 

## Notas

É recomendado utilizar o swagger para executar as requisições, já que estão todas mapeadas.

Apenas o Responsável referente a ordem pode alterar seu Status e Deletá-la.

O enpoint GET /service-order retornar as ordens apenas referentes ao responsável autenticado (Logado).

O endpoint GET /service-order/all é publico, criado apenas para fins práticos. (Poderia ser implementado um perfil de Admin onde apenas este conseguiria ter acesso a esse endpoint)

Removido a necessiade de autenticação para criar order e followup por fins práticos.

## EndPoints
| Método | Caminho | Descrição | Requer Autenticação |
| ------ | ----------- | ----------- | ----------- |
| GET | /service-order/all | Retorna todas as ordens de serviço | Não |
| GET | /service-order| Retorna as ordens referentes ao Responsável Logado | Sim |
| POST | /auth | Autentica um usuário gerando um token do tipo Bearer | - |
| POST | /service-order| Cria uma ordem de serviço | Não |
| POST | /service-order/{id}/followup | Cria um registro de acompanhamento | Não |
| PUT | /service-order/{id}| Atualiza o Status de uma Ordem | Sim |
| DELETE | /service-order/{id} | Deleta uma Ordem | Sim |




