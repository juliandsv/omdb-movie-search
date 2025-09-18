# OMDb Movie Search 🎬

Spring Boot web application that integrates the **OMDb API** to search, view and save movies.  
Includes **Spring Security** for authentication and role-based access, persistence with **MySQL**, and a frontend built with **Thymeleaf**.

---
## 🇪🇸 Descripción en Español
Aplicación web desarrollada en **Spring Boot** que consume la **API de OMDb** para buscar, visualizar y guardar películas.  
Incluye **Spring Security** para autenticación y control de acceso basado en roles, persistencia con **MySQL** y una interfaz construida con **Thymeleaf**.  
---

## ✨ Features
- 🔎 Search movies by title (OMDb API integration)  
- 📖 View detailed info (plot, actors, year, poster…)  
- 👤 User registration & login (Spring Security)  
- 🔒 Authentication and role-based access control  
- 💾 Save movies to your personal list  
- 🤝 Share movies with other registered users  
- 🎨 Responsive UI with Thymeleaf templates  

---

## 🧰 Tech Stack
- Java 17  
- Spring Boot 3 (Web, Data JPA, Security)  
- MySQL (persistence)  
- Thymeleaf (frontend)  
- OMDb API  

---

## ⚙️ Setup (local)
1. Copy `src/main/resources/application-example.properties` → `src/main/resources/application.properties`.
2. Add your OMDb API key:
   ```properties
   omdb.api.base=https://www.omdbapi.com/
   omdb.api.key=YOUR_API_KEY
   ```

---


## ▶️ Run
```bash
mvn spring-boot:run
```
