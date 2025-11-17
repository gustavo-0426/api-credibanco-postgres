<br>

# 🚀 api-credibanco-postgres

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.java.net/projects/jdk/21/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.1-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Docker](https://img.shields.io/badge/Docker-Ready-blue.svg)](https://www.docker.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

**API REST** para gestión de operaciones bancarias (tarjetas, productos, transacciones y clientes) con autenticación y autorización basada en roles. Implementa Spring Security, JPA/Hibernate y documentación automática con Swagger.

## 📋 Tabla de Contenidos

- [🚀 Características](#características)
- [🏗️ Arquitectura](#arquitectura)
- [📋 Requisitos Previos](#requisitos-previos)
- [⚡ Inicio Rápido (5 minutos)](#inicio-rapido)
- [🔐 Seguridad y Autenticación](#seguridad)
- [📚 API Documentation](#api-documentation)
- [🧪 Testing](#testing)
- [📞 Contacto](#contacto)

---
<br>

## <a id="características"></a>🚀 Características

- ✅ **Spring Boot 3.4.1** + **Java 21** con arquitectura en capas (Controller → Service → Repository)
- � **Spring Security** con autenticación HTTP Basic y autorización basada en roles (admin, test)
- 💾 **Persistencia JPA/Hibernate** con PostgreSQL
- 🃏 **Gestión de Tarjetas**: generación, activación, bloqueo y recarga de saldo
- 💳 **Productos bancarios** y **transacciones** con validaciones de negocio
- 👥 **Usuarios y roles** almacenados en BD con contraseñas hasheadas (BCrypt)
- 🐳 **Docker Compose** configurado para orquestación de servicios (app + PostgreSQL + pgAdmin)
- � **Documentación automática** con Swagger/OpenAPI
- 🧪 **Tests unitarios** con JUnit, Mockito y H2 (in-memory)
- 🔧 **Configuración externalizada** mediante variables de entorno
- 📦 **Dockerfile** optimizado con multi-stage build

---
<br>

## <a id="arquitectura"></a>🏗️ Arquitectura

### Modelo de Capas

```
┌─────────────────────────────────────┐
│   Cliente (Postman/Frontend)        │
└──────────────┬──────────────────────┘
               │ HTTP Request + Auth
               ▼
┌─────────────────────────────────────┐
│   Spring Security Filter Chain      │
│   - HTTP Basic Authentication       │
│   - Role-based Authorization        │
└──────────────┬──────────────────────┘
               │
               ▼
┌─────────────────────────────────────┐
│   Controllers (@RestController)     │
│   - CardController                  │
│   - ProductController               │
│   - TransactionController           │
│   - CustomerController              │
└──────────────┬──────────────────────┘
               │
               ▼
┌─────────────────────────────────────┐
│   Services (Lógica de Negocio)      │
│   - Validaciones                    │
│   - Procesamiento                   │
└──────────────┬──────────────────────┘
               │
               ▼
┌─────────────────────────────────────┐
│   Repositories (Spring Data JPA)    │
│   - ICardRepository                 │
│   - IProductRepository              │
│   - ITransactionRepository          │
│   - ICustomerRepository             │
└──────────────┬──────────────────────┘
               │
               ▼
┌─────────────────────────────────────┐
│   PostgreSQL Database               │
└─────────────────────────────────────┘
```

### Modelo de Datos

**Entidades principales:**

- **Customer**: Usuarios con username, password (BCrypt) y roles
- **Product**: Productos bancarios
- **Card**: Tarjetas vinculadas a productos con saldo y estado
- **TransactionManager**: Registro de transacciones

---
<br>

## <a id="requisitos-previos"></a>📋 Requisitos Previos

- **Spring Boot 3.4.1**
- **Java 21**
- **Maven 3.8+**
- **Docker** y **Docker Compose**
- **Git**

---
<br>

## <a id="inicio-rapido"></a>⚡ Inicio Rápido (5 minutos)

### 1️⃣ Variables de Entorno

Crear y configurar el archivo de variables de entorno:
```bash
cp docker-compose/env.example docker-compose/.env
```

### 2️⃣ Ejecutar Aplicación con Docker Compose

#### Construir y ejecutar:

```bash
docker-compose -f docker-compose/compose.yml up -d
```

#### Verificar contenedores activos:
```bash
docker-compose -f docker-compose/compose.yml ps
```

#### Ver logs en tiempo real:
```bash
docker-compose -f docker-compose/compose.yml logs -f
```

---
<br>

## <a id="seguridad"></a>🔐 Seguridad y Autenticación

### Autenticación

El proyecto utiliza **HTTP Basic Authentication**:

```bash
# Ejemplo de request autenticado
curl -X GET http://localhost:9091/v1/card \
  -H "Authorization: Basic base64(username:password)"
```

### Roles y Permisos

| Endpoint | Método | Roles Permitidos |
|----------|--------|------------------|
| `/v1/card/**` | POST, DELETE | `admin` |
| `/v1/card/**` | GET | `admin`, `test` |
| `/v1/product/**` | POST, DELETE | `admin` |
| `/v1/product/**` | GET | `admin`, `test` |
| `/v1/transaction/**` | POST | `admin` |
| `/v1/transaction/**` | GET | `admin`, `test` |
| `/v1/customer/**` | POST | `admin` |
| `/v1/customer/**` | GET | `admin`, `test` |

### Usuarios Iniciales

Los usuarios se cargan automáticamente desde `insert_users.sql` al iniciar la aplicación. Las contraseñas se almacenan hasheadas con BCrypt.

---
<br>

## <a id="api-documentation"></a>📚 API Documentation

### 📖 Swagger UI

Una vez que la aplicación esté ejecutándose, puedes acceder a la documentación interactiva:

- **Swagger UI:** [http://localhost:9091/v1/credibanco/swagger-ui/index.html](http://localhost:9091/v1/credibanco/swagger-ui/index.html)
- **OpenAPI JSON:** [http://localhost:9091/v3/api-docs](http://localhost:9091/v3/api-docs)

### 🗄️ Administración de Base de Datos

Para gestionar y administrar la base de datos PostgreSQL, se debe conectar al servidor **pgAdmin**:

- **pgAdmin:** [http://localhost:5050](http://localhost:5050)

---
<br>

## <a id="testing"></a>🧪 Testing

El proyecto incluye tests unitarios con **JUnit 5** y **Mockito**, usando **H2** como base de datos en memoria.

### Ejecutar tests

```bash
# Ejecutar todos los tests
mvn test

# Ejecutar tests con reporte de cobertura
mvn clean verify
```

### Cobertura de Tests

- ✅ Controllers (CardControllerImplTest, ProductControllerImplTest, TransactionControllerImplTest)
- ✅ Services (CardServiceImplTest, ProductServiceImplTest, TransactionServiceImplTest)
- ✅ Exception Handlers (ExceptionControllerImplTest)

---
<br>

## <a id="contacto"></a>📞 Contacto 


### Gustavo Castro

**Ingeniero de Sistemas**  
**Especialista en Ingeniería de Software**  
**Desarrollador Backend Senior, Spring Boot, Node.js, Arquitectura Cloud (AWS)**  
**GitHub:** [github.com/gustavo-0426](https://github.com/gustavo-0426)  
**LinkedIn:** [linkedin.com/in/gustavo-castro-prasca](https://linkedin.com/in/gustavo-castro-prasca)

---
