# Sistema Financeiro + Chatbot Inteligente

Aplicação web de controle financeiro para **Pessoas Físicas (PF)** e **Pessoas Jurídicas (PJ)** integrada a um **chatbot inteligente** desenvolvido com **Java e Spring Boot**, capaz de orientar sobre o funcionamento do sistema (por meio de documentos pré-estabelecidos) e consultar os dados financeiros do próprio usuário para explicar contas, vencimentos e estatísticas mensais e anuais[cite: 1, 2, 3].

---

## 📋 Descrição do Projeto

O projeto combina um módulo de gestão financeira básica com um assistente conversacional na aplicação web[cite: 1, 2]. O objetivo é oferecer clareza sobre ganhos, gastos, receitas, despesas, fluxo de caixa e pendências ao longo do mês e do ano, ao mesmo tempo em que apoia o usuário com respostas baseadas em regras do sistema, documentos de ajuda e nos lançamentos registrados na plataforma[cite: 1, 2, 3].

### Principais Pilares
* **Controle Financeiro (PF e PJ):** Cadastro de usuários pessoa física ou jurídica, gestão de categorias e lançamentos (receitas/despesas, status, vencimentos, pagamentos e recorrência), dashboard de estatísticas mensais/anuais e fluxo de caixa[cite: 1, 2, 3].
* **Chatbot com Decisão Híbrida:** Responde a dúvidas sobre o uso da plataforma (consultando documentos pré-estabelecidos) e sobre a situação financeira do usuário autenticado (consultando os dados persistidos no banco), respeitando regras fixas do sistema[cite: 1, 2].
* **Stack Base:** Java, Spring Boot, Spring AI, MySQL e interface web[cite: 1].

---

## 🗂️ Estrutura Conceitual de Dados (MySQL)

O modelo relacional conta com quatro tabelas principais[cite: 3]:

* **`Users`**: Armazena dados de identificação (`PF` ou `PJ`), credenciais, renda/faturamento opcional, CEP opcional, status ativo e data de criação[cite: 2, 3].
* **`Categorias`**: Organiza classificações financeiras (receitas ou despesas) vinculadas ao usuário[cite: 3, 4].
* **`lancamentos`**: Registra entradas e saídas, valores decimais, datas de vencimento e pagamento, status, recorrência e vínculos com usuário e categoria[cite: 2, 3].
* **`mensagens_chat`**: Armazena o histórico das conversas por sessão, remetente, conteúdo e origem/fonte da resposta[cite: 3].

---

## ✅ Checklist — O Que Já Foi Feito

* [x] **Concepção e Resumo do Produto:** Definição do escopo inicial combinando estatísticas financeiras mensais/anuais e chatbot para dúvidas e explicação de contas[cite: 1].
* [x] **Levantamento e Refinamento de Requisitos:** Levantamento de requisitos funcionais e não funcionais, adaptação para manter o escopo enxuto (sem inflar com ERP fiscal/contábil complexo) e definição das decisões-chave do projeto[cite: 2].
* [x] **Definição de Diretrizes do Produto:**
  * [x] Suporte a **Pessoas (PF)** e **Empresas (PJ)**[cite: 2, 3].
  * [x] Chatbot com análise híbrida (documentos do sistema + dados financeiros do usuário).
  * [x] Documentos pré-estabelecidos ensinando como o sistema funciona.
  * [x] Canal de atendimento via **aplicação web**[cite: 1].
  * [x] Escolha inicial do **MySQL** como banco de dados relacional.
* [x] **Modelagem do Banco de Dados (Etapa 1):** Desenho do diagrama EER no MySQL Workbench com as tabelas `Users`, `Categorias`, `lancamentos` e `mensagens_chat` e seus relacionamentos[cite: 3].
* [x] **Fluxo de Decisão do Chatbot (Etapa 2):** Definição do fluxo desde o recebimento da mensagem até a resposta, combinando *System Prompt* (regras básicas), consulta aos documentos do manual e acesso aos dados financeiros do usuário autenticado.

---

## 🧩 Módulos — Checklist Detalhado (Feito vs. A Fazer)

### 1. Engenharia de Requisitos e Modelagem
* [x] Definir visão geral, público-alvo (PF/PJ) e requisitos funcionais/não funcionais[cite: 2].
* [x] Modelar tabelas e relacionamentos no MySQL Workbench (`Users`, `Categorias`, `lancamentos`, `mensagens_chat`)[cite: 3].
* [ ] Aplicar ajustes finos finais no modelo físico/EER (ex.: obrigatoriedade `NOT NULL` em campos essenciais e revisão de chaves/nomes conforme o mapeamento JPA)[cite: 4].
* [ ] Criar o script SQL inicial (`DDL`) e, se desejado, o diagrama de casos de uso consolidado.

### 2. Backend (Java + Spring Boot)
* [x] Estruturar a base conceitual da arquitetura em camadas e o fluxo de decisão do chatbot.
* [ ] Inicializar o projeto Spring Boot com as dependências necessárias (Web, Data JPA, Security, Driver MySQL, Spring AI etc.).
* [ ] Configurar conexão com o MySQL e variáveis de ambiente (`application.properties` / `application.yml`).
* [ ] Implementar entidades JPA, DTOs, repositories, services e controllers.
* [ ] Implementar autenticação e autorização (protegendo rotas e garantindo isolamento dos dados por usuário).

### 3. Módulo Financeiro (PF / PJ)
* [ ] Implementar cadastro, atualização, consulta e desativação/exclusão de usuários PF e PJ[cite: 2, 3].
* [ ] Implementar CRUD de categorias de receitas e despesas[cite: 3, 4].
* [ ] Implementar CRUD de lançamentos financeiros (valores com precisão decimal, vencimento, pagamento, status e recorrência)[cite: 2, 3].
* [ ] Implementar endpoints/serviços para o **Dashboard** (estatísticas mensais e anuais de ganhos/arrecadado vs. gastos/perdido) e **Fluxo de Caixa** (entradas, saídas diárias e previsão de saldo)[cite: 1, 2].

### 4. Módulo do Chatbot (Regras + Manual + Dados do Usuário)
* [x] Desenhar o fluxo de decisão e separação entre dúvidas do sistema e dados financeiros.
* [ ] Escolher e configurar o provedor de IA (API gratuita ou modelo local compatível com o projeto).
* [ ] Preparar os documentos pré-estabelecidos do manual do sistema e configurar o carregamento/consulta da base de conhecimento.
* [ ] Criar as ferramentas/consultas do módulo financeiro para o bot ler apenas os dados do usuário autenticado.
* [ ] Definir e testar o *System Prompt* com as regras básicas de comportamento, segurança e limites.
* [ ] Persistir e recuperar o histórico de conversas em `mensagens_chat`[cite: 3].

### 5. Frontend Web e Integração
* [ ] Construir interface web com navegação lateral, cadastro/login, dashboard, telas de finanças e janela/área do chatbot[cite: 1, 2].
* [ ] Integrar frontend às APIs REST do backend financeiro e do chatbot.
* [ ] Exibir estados de carregamento, mensagens de erro amigáveis e histórico da conversa.

### 6. Qualidade, Testes e Portfólio
* [ ] Testar fluxos financeiros (PF/PJ, cálculos mensais/anuais, contas pendentes/pagas) e cenários do chatbot (dúvida do manual, consulta financeira, pergunta fora do escopo).
* [ ] Validar segurança e isolamento entre usuários.
* [ ] Documentar instruções de execução local no `README.md` (variáveis de ambiente, banco de dados e comandos para rodar) e adicionar capturas de tela/demonstração para o portfólio.

---

## 🚀 Próximos Passos

1. Finalizar os ajustes do modelo no MySQL e gerar o script de criação das tabelas[cite: 3, 4].
2. Subir o esqueleto do projeto Spring Boot conectado ao MySQL.
3. Implementar o CRUD financeiro básico e, em seguida, plugar o Spring AI com os documentos do manual e as consultas aos dados do usuário.
