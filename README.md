<h1 align="center">Project: Creadit application system</h1>
<p align="center">Neste projeto, foi desenvolvida uma API Rest para um Sistema de Analise de créditos para uma empresa de empréstimos.</p>

---

<br>

<h2 align="center">📃 Sobre o Projeto</h2>

<p align="center">Foi desenvolvida uma aplicação em Kotlin com Spring Boot para fazer cadastro,
  atualização e deleção de clientes. Esses clientes podem fazer solicitação de créditos para uma empresa de empréstimos.
  A aplicação conta com testes individuais e funcionais, garantindo a qualidade e funcionalidade do mesmo.
</p>


<a href = "https://gist.github.com/cami-la/560b455b901778391abd2c9edea81286">Descrição do Projeto</a>

---

<div align="center">
<p>Diagrama UML Simplificado do projéto</p>
<picture><img
           height="400px"
           src="https://camo.githubusercontent.com/33acbb87760a5320ad4ad00fa6a49db79fc8c9142114d93c147e270b23236d48/68747470733a2f2f692e696d6775722e636f6d2f377068796131362e706e67"
           alt="Diagrama"
           />
</picture>
</div>

---

### 🚀 Como executar o projeto

É recomendado ter o <a href="https://docs.docker.com/get-started/overview/">Docker</a> instalado.

_Clonar e acessar a pasta do projeto_

```jsx
git clone git@github.com:TonyyCruz/credit-application-system.git && cd credit-application-system
```

_Subir a aplicação em docker_
```jsx
  docker-compose up -d --build
```

- Caso opte por rodar sem docker, abra o projeto com sua IDE de preferência, instale as dependências e inicie o projeto.

### Após isso, a aplicação já estará ativa!
  A aplicação estará em um contêiner chamado `credit-application-system_app`.

---

<h4>O Swagger estará visível nessa rota: <a href="http://localhost:8080/swagger-ui/index.html">Aqui!</a></h4>
<h4>O H2-database estará visível nessa rota: <a href="http://localhost:8080/swagger-ui/index.html">Aqui!</a></h4>

---

<details>
  <summary><strong>:computer: Regras de negócio</strong></summary><br/>
  <h3>Cliente:</h3>
  
  - Todos os campos são obrigatórios.
  - CPF deve ser válido.
  - Email deve ser válido.
  - Password deve ter entre 8 e 40 characteres.
    
  ---

  <h3>Crédito:</h3>
  
  - Todos os campos são obrigatórios.
  - Dia do primeiro pagamento deve ser uma data futura de no máximo três meses.
  - Numeo de pagamentos devem ser entre 1 e 48.
</details>
