# Task Manager
Um web app simples de gerenciamento de tarefas pessoais, construído com **Spring Boot + Thymeleaf**.

## Descrição
Este projeto é um gerenciador de tarefas (to-do list) com autenticação simulada (sem Spring Security). Ele permite que um usuário veja, crie, atualize e remova tarefas.

As páginas são renderizadas diretamente no servidor usando Thymeleaf, sem frontend separado nem API REST.

## Funcionalidades
- Login básico com formulário (ainda sem validação real)
- Listagem de tarefas do usuário
- Criação de novas tarefas
- Edição e remoção de tarefas existentes
- Visualização de detalhes da tarefa

## Tecnologias usadas
- Java 21
- Spring Boot
- Thymeleaf
- Spring Data JPA
- MySQL
- Bean Validation

## Estrutura futura planejada
- Adicionar autenticação com Spring Security
- Integração com HTMX para interações dinâmicas
- Responsividade com CSS básico
