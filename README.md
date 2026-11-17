# AgendaFlow API — Sistema de Agendamentos | Projeto 5/9

API REST educacional em **Java 21+, Spring Boot 3.5.7, PostgreSQL, Spring Data JPA e Flyway** para cadastro de profissionais e marcação/cancelamento de horários.

## Funcionalidades
- Cadastro e listagem de profissionais (nome e especialidade).
- Agendamento por profissional com nome/e-mail do cliente, início e término em ISO 8601 UTC (Instant).
- Rejeição de horários no passado, intervalos inválidos, duração superior a 8 horas e sobreposição de horários.
- Cancelamento lógico: agendamentos cancelados não bloqueiam horários futuros.
- Proteção contra marcações simultâneas: transação com **bloqueio pessimista da linha do profissional**, seguido de verificação de sobreposição. Esse mecanismo depende do PostgreSQL e exige testes de integração de concorrência.
- Testes unitários JUnit 5/Mockito, GitHub Actions e migrações Flyway.

## Rodar no Eclipse
Requisitos: JDK 21+, Maven 3.9+ e Docker Compose.

```bash
docker compose up -d
mvn clean test
mvn spring-boot:run
```

Importe via **File > Import > Maven > Existing Maven Projects**. API em `http://localhost:8080`, PostgreSQL na porta local **5436**. Variáveis opcionais: `DB_URL`, `DB_USER`, `DB_PASSWORD`. Credenciais Docker são apenas para desenvolvimento.

## Endpoints
| Método | Rota | Ação |
|---|---|---|
| POST | `/api/v1/professionals` | Cadastrar profissional |
| GET | `/api/v1/professionals` | Listar profissionais |
| POST | `/api/v1/appointments` | Agendar horário |
| GET | `/api/v1/appointments/{id}` | Consultar agendamento |
| GET | `/api/v1/professionals/{id}/appointments` | Agenda de um profissional |
| PATCH | `/api/v1/appointments/{id}/cancel` | Cancelar agendamento |

### Exemplo de cadastro de profissional
```json
{"name":"Ana Souza","specialty":"Dentista"}
```

### Exemplo de agendamento
```json
{"professionalId":1,"customerName":"João Lima","customerEmail":"joao@example.com","startsAt":"2027-03-20T14:00:00Z","endsAt":"2027-03-20T14:30:00Z"}
```

> Ajuste as datas para o futuro. A API usa instantes UTC; converta o horário local para ISO 8601 com offset antes de enviar.

## Regras de negócio
O intervalo é semiaberto `[início, fim)`: um agendamento que termina às 14:00 não conflita com outro que começa às 14:00. O lock pessimista por profissional serializa **criações** de horários daquele profissional. Cancelamentos e outras operações não usam o mesmo lock; uma solução de produção exigiria política uniforme de concorrência e testes integrados.

## Limitações
Este projeto **não tem autenticação nem autorização**, horários de funcionamento, feriados, fuso individual por profissional, notificações, reagendamento ou cobrança. Não exponha a API na internet sem segurança. Testes CI são unitários, não substituem testes de integração PostgreSQL/Testcontainers. Projeto de portfólio, não pronto para produção.

## Para entrevistas
Explique `@Transactional`, `@Lock(PESSIMISTIC_WRITE)`, consulta de sobreposição, `Instant` versus `LocalDateTime`, DTOs, Bean Validation, Flyway e cancelamento lógico.
