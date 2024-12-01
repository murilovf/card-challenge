# Projeto: Jogo de Cartas com Baralho Parametrizável

## Descrição do Desafio

O objetivo deste projeto é desenvolver uma aplicação Java Spring que permita criar e gerenciar um jogo de cartas. A aplicação é parametrizável, permitindo configurar o número de jogadores e a quantidade de cartas por mão. Após a distribuição das cartas, o sistema avalia e identifica a mão com a maior somatória de valores. Em caso de empate, a aplicação retorna os vencedores empatados.

### Regras do Jogo

1. **Valores das Cartas**:
    - A = 1
    - K = 13
    - Q = 12
    - J = 11
    - Outras cartas seguem seu valor numérico.

2. **Critério de Vitória**:
    - A mão com a maior somatória de valores é a vencedora.
    - Em caso de empate, são retornados todos os jogadores empatados.

3. **Exemplo**:
    - Jogador 1: [A,2,3,4,5] → Total: 15
    - Jogador 2: [K,Q,J,10,9] → Total: 55 **(Vencedor)**
    - Jogador 3: [8,9,2,A,J] → Total: 31
    - Jogador 4: [2,2,5,7,2] → Total: 18

---

## Funcionalidades Implementadas

- **Criação do jogo**: Gera um baralho e inicializa o jogo com jogadores e cartas conforme os parâmetros.
- **Adição de jogadores**: Permite adicionar novos jogadores ao jogo em andamento.
- **Consulta de jogadores**: Recupera a lista de jogadores de um jogo específico.
- **Finalização do jogo**: Calcula os vencedores com base nas somatórias das mãos.

---

## Endpoints Disponíveis

### 1. Criar Jogo

**URL:**  
`POST /game/create`

**Body (JSON):**
```json
{
    "cardsPerHand" : 3,
    "numberPlayers" : 10,
    "createPlayers" : false
}
```
**Descrição:**
Inicializa um novo jogo com o número especificado de jogadores e cartas por mão.
Com a opção de já criar os jogadores necessários para o jogo ou criar o jogo zerado (createPlayers)

---
### 2. Consultar Jogo por ID

**URL:**  
`GET /game/{gameId}`

**Descrição:**
Retorna os detalhes de um jogo existente pelo gameId
---
### 3. Listar Jogadores do Jogo

**URL:**  
`GET /player/list/{gameId}`

**Descrição:**
Recupera a lista de jogadores pertencentes ao jogo identificado pelo gameId.

---

### 4. Adicionar Jogador ao Jogo
**URL:**
`POST /player/create`

**Body (JSON):**
```json
{
    "name": "João",
    "gameId": "m8jicvtzq5bp"
}
```
**Descrição:**
Adiciona um novo jogador com o nome especificado ao jogo identificado pelo gameId.
---
### 5. Finalizar Jogo
**URL:**
`POST /game/{gameId}/finish`

**Descrição:**
Finaliza o jogo, calcula as somatórias das mãos dos jogadores e retorna os vencedores.

---

## Tecnologias Utilizadas

* **Java Spring Boot:** Framework principal para desenvolvimento da aplicação.
* **REST API:** Para a criação de uma interface de programação de aplicativos baseada em representação de estado.
* **Maven:** Para gerenciamento de dependências.
---
## Como Executar o Projeto
Clone o repositório:

```bash
git clone <url-do-repositório>
cd <nome-do-projeto>
```

Compile o projeto:

```bash
mvn clean install
```

Execute a aplicação no Docker:

```bash
docker-compose up --build
```

Acesse os endpoints da aplicação com o host:

```plaintext
http://localhost:8080
```