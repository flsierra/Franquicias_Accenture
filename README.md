# 🚀 Franquicia API – Spring WebFlux + AWS Deployment Guide
Esta es una prueba tecnica a nivel Backend para la empresa Accenture que simula la gestión de Franquicias, Sucursales y Productos, donde según una serie de requerimientos se diseñaron distintos endpoints para dar solución según lo solicitado.

## Guía rápida para levantar la API de Franquicias diseñada con (Spring Web Flux) y MySql con Docker

## Requisitos
* Docker y Docker Compose Instalados

## Variables de entrono 
Definidas en Franquicias_Accenture/src/main/resources/application.yml y provistas por docker-compose.yml;
* ${SPRING_R2DBC_USERNAME}: root
* ${SPRING_R2DBC_PASSWORD}: ""
*  SPRING_R2DBC_URL: r2dbc:mysql://mysql:3306/Franquicias_Accenture
*  ${PORT:8080}
   
## Inicio rápido App
* docker compose build
* docker compose up -d

## URLs
* Backend API (Docker): http://localhost:8080
* Swagger (Docker): http://localhost:8080/swagger-ui.html
* Backen API (Nube): http://ebcreatefranquicias-env-env.eba-2tuq8fwu.us-east-2.elasticbeanstalk.com/webjars/swagger-ui/index.html#/
  
## 📁 Estructura del Proyecto

```text
Franquicias_Accenture/
├── Dockerfile
├── docker-compose.yml
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── Application/
│   │   │   │   ├── Dtos/
│   │   │   │   ├── Exceptions/
│   │   │   │   └── Services/
│   │   │   ├── Domain/
│   │   │   │   ├── Model/
│   │   │   │   └── Repository/
│   │   │   ├── Infrastructure/
│   │   │   │   ├── Persistence/
│   │   │   │   └── Repository/
│   │   │   └── Interfaces/
│   │   └── resources/
│   │       └── application.yml
│   │
│   └── test/
│       └── java/
│           ├── FranquiciaServicesTest.java
│           ├── ProductoServicesTest.java
│           └── SucursalServicesTest.java
```
# 🛠 Tecnologías

- ☕ Java 17
- 🌱 Spring Boot 3  
- ⚡ Spring WebFlux  
- 🔄 Spring Data R2DBC  
- 🐬 MySQL  
- 📦 Maven  
- 📘 Swagger / OpenAPI  
- 🧪 Mockito  
- 🐳 Docker  
- ☁️ AWS Elastic Beanstalk  
- 🗄 Amazon RDS MySQL  
