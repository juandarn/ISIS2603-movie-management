# 🎬 CineConnect – Movie Management App (ISIS2603)

> Academic project developed for the course **Desarrollo de Software en Equipo (ISIS2603)**  
> Universidad de los Andes – 2025  
> Technologies: **Java Spring Boot + Angular + Jenkins + SonarQube + GitHub Actions**

---

## 🎯 Project Overview

**CineConnect** is a full-stack web application for managing movies, directors, and actors.  
It allows users to browse, add, edit, and remove movies, as well as visualize details about each production and its contributors.

The project demonstrates teamwork, software engineering best practices, and continuous integration in a realistic development environment.

---

## 🧩 Key Features

- 📽️ Manage movie records (create, read, update, delete).  
- 🎭 Manage directors, actors, and genres.  
- 🔍 Search and filter movies by title, genre, or year.  
- 🧱 Backend built with **Java Spring Boot**, including services, repositories, and entities.  
- 💾 Data persistence implemented using **JPA/Hibernate**.  
- 🌐 Frontend developed with **Angular**, integrated with REST APIs.  
- 🧪 Unit and integration tests for backend and frontend.  
- ⚙️ CI/CD pipelines implemented with **GitHub Actions** and **Jenkins**.  
- 📊 Code quality analyzed through **SonarQube**.

---

## 🧪 Quality & Testing

- **Backend tests:** JUnit + Mockito  
- **Frontend tests:** Jasmine + Karma  
- **Postman collections:** for validating API endpoints  
- **Jenkins + SonarQube:** automated builds and static code analysis  

Quality metrics tracked:
- Code coverage  
- Technical debt  
- Code smells  
- Maintainability rating  

---

## ⚙️ Technologies Used

| Category | Tools / Frameworks |
|-----------|--------------------|
| **Backend** | Java 17, Spring Boot, Maven, JPA/Hibernate |
| **Frontend** | Angular, TypeScript, Bootstrap |
| **Database** | H2 / PostgreSQL |
| **CI/CD** | GitHub Actions, Jenkins |
| **Code Quality** | SonarQube |
| **Version Control** | Git + GitHub |
| **Project Management** | GitHub Projects, Teamwork, Tándem |

---

## 🧭 Course Information

**Course:** Desarrollo de Software en Equipo – ISIS2603  
**Institution:** Universidad de los Andes  
**Semester:** 2025-1  

---

## 🚀 How to Run the Project

### 1️⃣ Run Backend
```bash
cd backend
mvn clean install
mvn spring-boot:run
````

### 2️⃣ Run Frontend

```bash
cd frontend
npm install
ng serve
```

Open 👉 `http://localhost:4200`
API runs on 👉 `http://localhost:8080`

---
