# 🎫 Help Desk API

API REST de um sistema de Help Desk (chamados/tickets), desenvolvida com **Java 21** e **Spring Boot 3.5**. Projeto fullstack com autenticação JWT, controle de acesso por perfil e CRUD completo de chamados.

> 🔗 **Aplicação no ar:** [helpdesk_frontend](https://ygor-ccarvalho.github.io/helpdesk_frontend/)
> 🔗 **Repositório do Frontend:** [github.com/ygor-ccarvalho/helpdesk_frontend](https://github.com/ygor-ccarvalho/helpdesk_frontend)

---

## 📋 Sobre o projeto

Sistema de gestão de chamados técnicos onde **técnicos** e **clientes** são cadastrados e os chamados são abertos, atribuídos e acompanhados até a resolução.

Originalmente baseado no excelente curso do **Prof. Valdir Cezar**, o projeto foi **totalmente modernizado** por mim:

- ⬆️ Migração de **Java 11 → Java 21**
- ⬆️ Migração de **Spring Boot 2 → 3.5**
- 🔐 Reescrita completa da camada de segurança (Spring Security 6)
- ♻️ Aplicação de boas práticas atuais e correção de breaking changes
- 🌐 Deploy completo em produção (frontend + backend)

---

## 🚀 Tecnologias

- **Java 21**
- **Spring Boot 3.5**
- **Spring Security 6** + **JWT** (autenticação stateless)
- **Spring Data JPA**
- **PostgreSQL** (produção) · **MySQL** e **H2** (perfil de testes/dev)
- **Bean Validation**
- **Maven**
- **Lombok**

---

## 🔐 Perfis de acesso

| Perfil | Permissões |
|--------|------------|
| **ADMIN** | Acesso total (gerencia técnicos, clientes e chamados) |
| **TÉCNICO** | Gerencia e atende chamados |
| **CLIENTE** | Abre e acompanha os próprios chamados |

---

## 🧪 Credenciais de teste

Quer testar sem cadastrar nada? Use o usuário ADMIN padrão:

```
E-mail: ygor@mail.com
Senha:  123
```

> 💡 A aplicação está no ar — acesse o [frontend](https://ygor-ccarvalho.github.io/helpdesk_frontend/) e faça login com as credenciais acima.

---

## 📦 Como rodar localmente

### Pré-requisitos
- Java 21 instalado
- Maven 3.9+
- MySQL rodando (perfil `test`) — ou use o H2 em memória

### Passo a passo

```bash
# 1. Clone o repositório
git clone https://github.com/ygor-ccarvalho/helpdesk_backend.git

# 2. Entre na pasta
cd helpdesk_backend

# 3. Configure as variáveis de ambiente (ver abaixo)

# 4. Rode a aplicação
./mvnw spring-boot:run
```

A API estará disponível em: `http://localhost:8080`

### Perfis (Spring Profiles)
- `test` → MySQL / H2 (desenvolvimento local)
- `prod` → PostgreSQL (produção no Render)

```bash
# Rodar com perfil de testes
./mvnw spring-boot:run -Dspring-boot.run.profiles=test
```

---

## ⚙️ Variáveis de ambiente

```properties
# Banco (produção - PostgreSQL)
DATABASE_URL=jdbc:postgresql://localhost:5432/helpdesk
DB_USERNAME=seu_usuario
DB_PASSWORD=sua_senha

# JWT
JWT_SECRET=sua_chave_secreta_com_mais_de_64_caracteres
JWT_EXPIRATION=86400000
```

---

## 📚 Principais Endpoints

| Método | Endpoint | Descrição | Acesso |
|--------|----------|-----------|--------|
| `POST` | `/login` | Autenticação (retorna JWT) | Público |
| `GET` | `/tecnicos` | Lista técnicos | ADMIN |
| `POST` | `/tecnicos` | Cria técnico | ADMIN |
| `GET` | `/clientes` | Lista clientes | ADMIN/TÉCNICO |
| `POST` | `/clientes` | Cria cliente | ADMIN |
| `GET` | `/chamados` | Lista chamados | Autenticado |
| `POST` | `/chamados` | Abre chamado | Autenticado |
| `PUT` | `/chamados/{id}` | Atualiza chamado | ADMIN/TÉCNICO |

> 💡 Envie o token no header: `Authorization: Bearer <seu_token>`

---

## 🌐 Deploy

- **Hospedagem:** Render
- **URL da API:** [https://helpdesk-backen.onrender.com](https://helpdesk-backen.onrender.com)
- **Banco em produção:** PostgreSQL (Render)

> ⚠️ **Atenção:** o backend está em plano gratuito e **hiberna após inatividade**. A primeira requisição (login) pode levar **~40s** para "acordar". 😴

---

## 👤 Autor

**Ygor Carvalho**
- 💼 LinkedIn: [linkedin.com/in/ygorcarvalhodev](https://www.linkedin.com/in/ygorcarvalhodev/)
- 🐙 GitHub: [@ygor-ccarvalho](https://github.com/ygor-ccarvalho)

---

## 🙏 Créditos

Projeto baseado no curso de Help Desk do **Prof. Valdir Cezar**, modernizado e expandido como estudo prático.
