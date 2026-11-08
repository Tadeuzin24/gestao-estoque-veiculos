# Sistema Automotivo – Gestão de Estoque de Veículos

## 1. Sobre o projeto

Sistema desenvolvido em Java com Spring Boot para gerenciar o estoque de veículos de uma concessionária.

A aplicação permite cadastrar marcas, modelos e veículos, consultar registros, atualizar informações, excluir veículos e realizar filtros por diferentes características.

O projeto foi desenvolvido como atividade acadêmica da disciplina de Programação Orientada a Objetos.

## 2. Tecnologias utilizadas

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- IntelliJ IDEA
- Git e GitHub

## 3. Funcionalidades

### Marcas
- Cadastrar marcas.
- Listar marcas.
- Consultar marca por ID.
- Atualizar marcas.
- Excluir marcas sem modelos associados.

### Modelos
- Cadastrar modelos vinculados a marcas.
- Listar modelos.
- Consultar modelo por ID.
- Filtrar modelos por marca.
- Atualizar e excluir modelos sem veículos associados.

### Veículos
- Cadastrar veículos.
- Listar veículos.
- Consultar veículo por ID.
- Atualizar informações.
- Excluir veículos.
- Filtrar por marca, modelo, preço, ano e status.
- Combinar diferentes filtros em uma mesma consulta.

## 4. Estrutura do projeto

```text
gestao-veiculos/
├── src/
│   ├── main/
│   │   ├── java/br/com/gestaoveiculos/
│   │   │   ├── controller/
│   │   │   ├── dto/
│   │   │   ├── entity/
│   │   │   ├── repository/
│   │   │   └── service/
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── .gitignore
├── mvnw
├── mvnw.cmd
└── pom.xml
```

## 5. Banco de dados

O projeto utiliza MySQL com o banco de dados `gestao_veiculos`.

As principais tabelas são:

- `marcas`
- `modelos`
- `veiculos`

Os relacionamentos são realizados por meio de chaves estrangeiras.

## 6. Como executar o projeto

### Pré-requisitos

- Java JDK 17 ou superior compatível com a configuração do projeto.
- MySQL Server.
- Git.

### Configuração do banco

Crie o banco de dados:

```sql
CREATE DATABASE gestao_veiculos;
```

Configure um usuário com permissões sobre esse banco.

### Configuração das credenciais

O arquivo `application.properties` utiliza variáveis de ambiente para as credenciais:

```properties
spring.datasource.username=${DB_USERNAME:app_veiculos}
spring.datasource.password=${DB_PASSWORD}
```

No PowerShell, configure:

```powershell
$env:DB_USERNAME = "app_veiculos"
$env:DB_PASSWORD = "SUA_SENHA"
```

Substitua `SUA_SENHA` pela senha configurada no MySQL.

### Inicialização

Na pasta principal do projeto, execute:

```powershell
.\mvnw.cmd spring-boot:run
```

A aplicação será iniciada na porta 8080.

## 7. Endpoints da API

### Marcas

| Método | Endpoint |
|---|---|
| POST | `/api/marcas` |
| GET | `/api/marcas` |
| GET | `/api/marcas/{id}` |
| PUT | `/api/marcas/{id}` |
| DELETE | `/api/marcas/{id}` |

### Modelos

| Método | Endpoint |
|---|---|
| POST | `/api/modelos` |
| GET | `/api/modelos` |
| GET | `/api/modelos/{id}` |
| GET | `/api/modelos/marca/{marcaId}` |
| PUT | `/api/modelos/{id}` |
| DELETE | `/api/modelos/{id}` |

### Veículos

| Método | Endpoint |
|---|---|
| POST | `/api/veiculos` |
| GET | `/api/veiculos` |
| GET | `/api/veiculos/{id}` |
| PUT | `/api/veiculos/{id}` |
| DELETE | `/api/veiculos/{id}` |

### Filtros de veículos

O endpoint `/api/veiculos` permite utilizar os seguintes parâmetros:

- `marcaId`
- `modeloId`
- `precoMin`
- `precoMax`
- `anoInicial`
- `anoFinal`
- `status`

Exemplo:

```text
GET /api/veiculos?marcaId=2&precoMin=100000&precoMax=130000&anoInicial=2020&anoFinal=2024&status=DISPONIVEL
```

Os status disponíveis são:

- `DISPONIVEL`
- `VENDIDO`
- `DESCONTINUADO`

## 8. Conceitos de Programação Orientada a Objetos

O projeto utiliza conceitos de Programação Orientada a Objetos:

- **Classes e objetos:** representação de marcas, modelos e veículos.
- **Encapsulamento:** atributos privados e métodos de acesso.
- **Herança:** as entidades herdam identificador e mapeamento da classe abstrata `EntidadeBase`.
- **Abstração:** utilização de uma classe abstrata como base para as entidades.

## 9. Arquitetura

O sistema utiliza uma organização em camadas:

- Controller: recebe as requisições HTTP.
- Service: concentra as regras de negócio.
- Repository: realiza o acesso aos dados.
- Entity: representa as entidades persistidas.
- DTO: organiza os dados recebidos e retornados pela API.

## 10. Autor

Projeto acadêmico desenvolvido por João Victor Tadeu Galvão Barbosa.