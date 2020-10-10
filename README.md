# Challenge-Api

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

## Detalhes


