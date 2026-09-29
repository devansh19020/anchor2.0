# ANCHOR — AI Codebase Intelligence Platform (Frontend)

ANCHOR is a developer-centric AI Codebase Intelligence Platform built with React, Vite, and modern CSS. It integrates with Spring Boot microservices via an API Gateway to provide semantic code search, asynchronous repository indexing, and Retrieval-Augmented Generation (RAG) Q&A grounded in source code.

---

## 🚀 Key Features

- **Developer-Focused UI**: Built with a sleek dark theme, high contrast typography, precise technical metadata, and subtle node-graph visual identity.
- **Microservices API Gateway Integration**: Centralized API client targeting `http://localhost:8080` (configurable via `VITE_API_BASE_URL`).
- **User Authentication**: Secure JWT handling, session persistence (`sessionStorage`), and auto-clearing on 401 Unauthorized errors.
- **Asynchronous Repository Indexing**: Live polling (`2.5s` interval) for repository status (`IMPORTING` → `INDEXING` → `READY` / `FAILED`).
- **Codebase Q&A Interface**: Interactive chat panel with source file grounding chips, multiline questions (Shift+Enter), suggested questions, copy-to-clipboard actions, and user-scoped local conversation persistence.

---

## 📋 System Prerequisites

- **Node.js**: `v18.0.0` or higher (Recommended `v20+`)
- **Package Manager**: `npm` or `pnpm`
- **Backend API Gateway**: Running on `http://localhost:8080`

### Backend Services & Ports Overview
| Service | Port | Endpoint Prefix | Purpose |
|---|---|---|---|
| **API Gateway** | `8080` | `/api/**` | Gateway routing & identity header injection |
| **User Service** | `8081` | `/api/users/**` | Registration, login, user profile verification |
| **Repo Service** | `8082` | `/api/repositories/**` | Repository import, listing, status polling |
| **AI Service** | `8083` | `/api/chat/**` | RAG codebase chat with Gemini & ChromaDB |

---

## 🛠️ Installation & Setup

1. **Navigate to the frontend directory:**
   ```bash
   cd frontend
   ```

2. **Install dependencies:**
   ```bash
   npm install
   ```

3. **Configure environment variables:**
   Create a `.env` file in the `frontend` root directory:
   ```env
   VITE_API_BASE_URL=http://localhost:8080
   ```

4. **Start the development server:**
   ```bash
   npm run dev
   ```

5. **Build for production:**
   ```bash
   npm run build
   ```

---

## 🔄 End-to-End Workflow

1. **Authentication**: Register a new user (`/register`) or Sign In (`/login`). The Bearer JWT is attached to protected requests.
2. **Repository Import**: Enter a public GitHub repository URL (e.g. `https://github.com/spring-projects/spring-petclinic`).
3. **Async Indexing**: The backend publishes a Kafka event. The frontend polls `/api/repositories/{id}/status` until status reaches `READY`.
4. **Codebase Q&A**: Ask questions about architecture, classes, and code flows. Source references are displayed underneath answers.

---

## 🔐 Security & Data Isolation

- **Token Storage**: JWT tokens are kept in `sessionStorage` (cleared on logout or browser session end).
- **User Scoping**: Local repository metadata and chat histories are strictly scoped by user ID in `localStorage`.
- **Identity Headers**: Identity headers (`X-User-Id`) are injected by the API Gateway; the client never fakes identity headers.
