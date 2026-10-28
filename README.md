# Trabalho_RAFA

Projeto acadêmico da disciplina **GQS — A3 2026.2**. A proposta atual é uma agenda para organizar compromissos, com modelos de usuário e compromisso e uma conexão preparada para SQLite.

## Funcionalidades e componentes implementados

- **Agenda**: mantém compromissos em uma lista em memória, permite adicionar e remover compromissos e disponibiliza a lista. Ao consultar uma agenda vazia, informa isso no console.
- **Compromisso**: representa um compromisso com ID, título, data e hora (`LocalDateTime`) e local. Possui construtores, getters e setters, formata a data como `dd/MM/yyyy HH:mm` e fornece uma representação textual.
- **Usuario**: representa um usuário com nome, e-mail e senha, com construtores, getters e setters.
- **DatabaseConnection**: tenta abrir uma conexão JDBC com um banco SQLite no arquivo `agenda.db`.
- **App**: ponto de entrada atual, que imprime `Hello World!`.

> **Estado atual:** a agenda ainda não persiste compromissos no banco de dados. A classe de conexão SQLite está disponível, mas não há criação de tabelas nem operações de leitura e gravação implementadas. Também não há fluxo de cadastro ou autenticação de usuários.

## Tecnologias

- Java 27
- Maven
- SQLite JDBC (`org.xerial:sqlite-jdbc:3.53.4.0`)
- JUnit Jupiter 5.11.0 para testes

## Como executar

É necessário ter o JDK 27 e o Maven instalados. Na pasta do projeto, compile e empacote com:

```bash
mvn package
```

Para executar a aplicação, abra o projeto na IDE e execute `br.com.agenda.App`. O método `main` atual imprime `Hello World!`.

> O `pom.xml` ainda não configura um comando Maven para iniciar a aplicação.

## Como executar os testes

```bash
mvn test
```

O teste existente é um teste inicial de exemplo (`AppTest`) que verifica uma condição sempre verdadeira; ainda não cobre o comportamento da agenda, dos modelos ou da conexão com o banco.

## Estrutura do projeto

```text
src/
├── main/java/br/com/agenda/
│   ├── Agenda.java
│   ├── App.java
│   ├── Compromisso.java
│   ├── DatabaseConnection.java
│   └── Usuario.java
└── test/java/br/com/agenda/
    └── AppTest.java
```
### Ultima alteração:
- Kaique Prado
