# Class Manager API 🎓

Um sistema de back-end robusto construído com **Spring Boot** para o controle de alunos, aulas particulares e pagamentos (sistema de *tickets*). 

## 🏗 Arquitetura
A API segue um padrão de camadas:
- **Controllers**: Expõe os endpoints REST (`/students`, `/classes`).
- **Services**: Onde vive toda a regra de negócio do app (ex: validar se a aula já foi dada, atualizar tickets de alunos ao registrar o pagamento ou aula concluída).
- **Repositories**: Comunicação com o banco de dados usando Spring Data JPA.
- **Entities & DTOs**: Entidades não são expostas direto, usamos Data Transfer Objects (DTO) usando records e classes isoladas.
- **Exceptions**: Tratamento global de erros para dar retornos de JSON padronizados (com status code certo `404`, `422`, etc).

## 🚀 Como Funciona a Regra de Negócio?

### 1. Sistema de Alunos e Tickets
Cada aluno, ao ser criado, inicia com `0` tickets de aulas.
O professor usa o endpoint de **Pagamentos** para dizer que o aluno pagou por mais X aulas. Isso adicionará recursos ao `ticket` do Aluno.
Ex: *Joãozinho pagou por 4 aulas. O ticket atual do Joãozinho passa para 4.*

### 2. Ciclo de Vida da Aula (Class Session)
Quando o professor cria uma aula, ele atrela aluno(s) a ela.
Estados possíveis:
- `SCHEDULED`: Aula agendada, não debita e não afeta nada.
- `COMPLETED`: Quando a aula é dada, a API automaticamente debita **1 ticket** do saldo de todo aluno atrelado à essa aula. E atenção: Aulas concluídas são bloqueadas para novas alterações!
- `CANCELED`: Aula cancelada. Não gasta tickets, mas só pode ser feita caso a aula ainda não estivesse classificada como `COMPLETED`.

## 📦 Testando as Rotas Básicas

Abaixo as principais rotas disponíveis no sistema usando Insomnia, Postman ou similar. Assumindo que a API roda em `http://localhost:8080`.

### 👨‍🏫 Ações do Professor/Usuário

Primeiro, é necessário ter um professor no banco para atrelar alunos a ele.
> No momento, isso ocorre ou configurando um endpoint base para `User` futuramente, ou diretamente popular o banco de dados. Como combinado, a estrutura usa senhas com Hash BCrypt. 
Assuma no momento que exista um Usuário de `ID 1` no seu DB.

### 🧑‍🎓 Estudantes `/students`

**Criar um Aluno**
- **POST** `http://localhost:8080/students?userId=1` (ID do usuário na prop da URL)
- **Body JSON**:
```json
{
	"name": "Maria Sousa",
	"defaultPrice": 120.00
}
```

**Listar todos os Alunos**
- **GET** `http://localhost:8080/students`

**Registrar Pagamento de Aula (Deposita Tickets)**
- **POST** `http://localhost:8080/students/1/payments`
- **Body JSON**:
```json
{
	"classesPaid": 5
}
```
*Isso vai incrementar 5 tickets no aluno de ID 1.*

### 📅 Aulas `/classes`

**Criar uma Aula Agendada**
- **POST** `http://localhost:8080/classes`
- **Body JSON**:
```json
{
	"date": "2026-04-12",
	"startTime": "14:30:00",
	"userId": 1,
	"studentIds": [1]
}
```

**Listar Histórico de Aulas**
- **GET** `http://localhost:8080/classes`

**Concluir uma Aula (Debita o ticket)**
- **PUT** `http://localhost:8080/classes/1/status?status=COMPLETED`
> Se você listar o aluno dnv, notará que o `ticket` dele reduziu em 1!
