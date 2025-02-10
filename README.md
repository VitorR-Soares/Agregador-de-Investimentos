<p align="center" width="100%">
    <img width="50%" src="https://groupcaliber.com.br/wp-content/uploads/2024/11/B3_Logo-1200-700.webp"> 
</p>


<h3 align="center">
  Agregador de Investimentos 
</h3>

<p align="center">

  <img alt="License: MIT" src="https://img.shields.io/badge/license-MIT-%2304D361">
  <img alt="Language: Java" src="https://img.shields.io/badge/language-java-green">
  <img alt="Version: 1.0" src="https://img.shields.io/badge/version-1.0-yellowgreen">
  <img alt="Concluído" src="https://img.shields.io/badge/concluído-OK-green">

</p>

# Descrição
Projeto Pessoal com objetivo de praticar habilidades relacionadas à Aplicações CRUD, Testes Unitários, Relacionamentos entre Entidades e Consumo de API externa com Open Feign através da construção de uma API de um Agregador de Investimentos
## Índice

- [Funcionalidades](#funcionalidades)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Instalação](#instalação)
- [Utilização](#utilização)
- [API Endpoints](#api-endpoints)

## Funcionalidades (em desenvolvimento)

- Criar, Listar, Deletar e Atualizar Usuários
- Criar e Listar Contas
- Cadastrar Ações em uma Conta
- Listar Ações em uma Conta

## Tecnologias Utilizadas

- Java
- Spring Boot
- MySQL como banco de dados
- Docker para conteinerização
- Para analisar o banco de dados, utilizei MySQL Workbench
- Para consumir os endpoints da aplicação, utilizei Insomnia

## Instalação

1. Clone o Repositório

```bash
git clone https://github.com/VitorR-Soares/Agregador-de-Investimentos.git
```

2. Instale as dependências com Maven
3. Garanta que possua o Docker Instalado
4. Acesse seu token no site da Brapi e o coloque no application.properties

## Utilização

1. Inicie o contâiner docker no console, dentro da pasta 'docker
```markdown
  docker compose up
```
2. Inicie a aplicação com Maven
3. A API estará acessível no http://localhost:8080


## API Endpoints
A API disponibiliza os seguintes endpoints:

```markdown
POST / v1/user/create - recebe um json com username, email e password e retorna o usuário criado
GET / v1/user/findbyId - recebe o id do usuário na URL e retorna o usuário encontrado
GET / v1/user/list - retorna a lista de usuários cadastrados
PUT / v1/user/update - recebe o id do usuário na URL e email e password no JSON do corpo da requisição e retorna um RespónseStatus 204
DELETE / v1/user/delete - recebe o id do usuário na URL e retorna um ResponseStaus 204
POST / v1/user/account/create - recebe o id da conta na URL e description, street e number no JSON no corpo da requisição
GET / v1/user/account/list - recebe o id do usuário na URL e retorna suas contas cadastradas
POST / v1/stock/create - recebe o id e description no JSON do corpo da requisição
POST / v1/account - recebe o id da conta pela URL e o stockId e qtd pelo corpo da requisição e retorna um ResponseStatus 204
GET / v1/account - recebe o Id da conta na URL e retorna as ações compradas, quantidade e valor total
```


:mag: Baixe o projeto e teste você mesmo na prática.

### Entre em contato! Estou à disposição

Acesse meu [Linkedin](https://www.linkedin.com/in/vitorr-soares/) 

Para mais projetos, meu [GitHub](https://github.com/VitorR-Soares/)
