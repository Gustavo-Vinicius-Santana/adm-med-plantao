# Projeto Med - API Java Spring
API projetada para atender as demandas de um curso de medicina. Feita com Spring boot e tendo como banco de dados o Postgres.

### Requesitos para rodar a aplicação:
- java
- docker

## Passo a Passo para Rodar o Projeto

### 1. Clonar o repositório
```bash
git clone https://github.com/Gustavo-Vinicius-Santana/adm-med-plantao.git
cd projeto-med
```

### 2. Rode o banco de dados no docker
- No diretorio do projeto rode o comando docker:

```bash
docker compose up -d
```

### 3. Inicie a aplicação
- No diretorio do projeto, rode o comando:
```bash
./mvnw spring-boot:run
```

### 4. Teste os end-points:
- Procure pelo arquivo do insomnia no repositorio do projeto.
- Abra o insomnia e teste as requisições do projeto
