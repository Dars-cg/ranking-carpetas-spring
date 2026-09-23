# 📁 Ranking de Carpetas - API REST & Web App

Aplicación web y API REST desarrollada con **Spring Boot** que analiza archivos comprimidos (`.zip`) en memoria mediante flujos de datos (*streaming*), calcula el total de archivos internos sin descomprimirlos en disco físico y genera un ranking persistente en base de datos.

---

## 🚀 Tecnologías

* **Java 21**
* **Spring Boot** (Spring Web, Spring Data JPA)
* **Thymeleaf** (Motor de plantillas del lado del servidor)
* **H2 Database** (Base de datos embebida persistente en archivo)
* **Maven** (Gestión de dependencias y construcción)
* **CSS3** (Estilos modulares y diseño responsive)

---

## 🏗️ Arquitectura del Proyecto (MVC)

El proyecto implementa el patrón **Modelo-Vista-Controlador (MVC)**, desacoplando la lógica de negocio y la persistencia de las interfaces de consumo:

```text
src/main/java/com/proyecto/ranking_carpetas/
├── controller/
│   ├── CarpetaWebController.java     # Rutas web y renderizado Thymeleaf
│   └── CarpetaRestController.java    # Endpoints CRUD de la API REST (JSON)
├── model/
│   └── CarpetaRegistro.java           # Entidad JPA (Mapeo a tabla H2)
├── repository/
│   └── CarpetaRepository.java         # Consultas y persistencia Spring Data JPA
├── service/
│   └── ZipService.java                # Procesamiento y conteo del ZIP en memoria
└── RankingCarpetasApplication.java    # Clase principal de arranque
```

### 📂 Recursos estáticos y de la vista

```text
src/main/resources/
├── static/
│   └── css/
│       └── estilos.css                # Estilos de la aplicación web
├── templates/
│   └── index.html                     # Plantilla HTML renderizada con Thymeleaf
└── application.properties             # Configuración del servidor y base de datos
```

---

## 📡 Documentación de la API REST

| Método   | Endpoint                | Descripción                               | Formato de entrada              | Código de éxito  | Códigos de error |
| -------- | ----------------------- | ----------------------------------------- | ------------------------------- | ---------------- | ---------------- |
| `POST`   | `/api/carpetas`         | Sube un ZIP, analiza y guarda el registro | `multipart/form-data` (archivo) | `201 Created`    | `400`, `500`     |
| `GET`    | `/api/carpetas/ranking` | Obtiene el Top 10 con más archivos        | Ninguno                         | `200 OK`         | -                |
| `GET`    | `/api/carpetas`         | Obtiene el historial completo             | Ninguno                         | `200 OK`         | -                |
| `GET`    | `/api/carpetas/{id}`    | Consulta una carpeta específica           | Ninguno                         | `200 OK`         | `404 Not Found`  |
| `PUT`    | `/api/carpetas/{id}`    | Actualiza el nombre de un registro        | `application/json`              | `200 OK`         | `400`, `404`     |
| `DELETE` | `/api/carpetas/{id}`    | Elimina un registro del ranking           | Ninguno                         | `204 No Content` | `404 Not Found`  |

---

## 📋 Ejemplos de Peticiones

### 1. Crear Registro (Subir ZIP)

* **Método:** `POST`
* **URL:** `http://localhost:8080/api/carpetas`
* **Headers:** `Content-Type: multipart/form-data`
* **Body:** Clave `archivo` (tipo `File`, seleccionando un `.zip`).

**Respuesta (`201 Created`):**

```json
{
  "id": 1,
  "nombre": "entregable_lab1",
  "totalArchivos": 24,
  "fechaRegistro": "2026-09-22T20:30:15"
}
```

---

### 2. Consultar el Top 10

* **Método:** `GET`
* **URL:** `http://localhost:8080/api/carpetas/ranking`

**Respuesta (`200 OK`):**

```json
[
  {
    "id": 1,
    "nombre": "entregable_lab1",
    "totalArchivos": 24,
    "fechaRegistro": "2026-09-22T20:30:15"
  }
]
```

---

### 3. Actualizar Nombre (PUT)

* **Método:** `PUT`
* **URL:** `http://localhost:8080/api/carpetas/1`
* **Headers:** `Content-Type: application/json`
* **Body (JSON):**

```json
{
  "nombre": "entregable_lab1_v2"
}
```

**Respuesta (`200 OK`):**

```json
{
  "id": 1,
  "nombre": "entregable_lab1_v2",
  "totalArchivos": 24,
  "fechaRegistro": "2026-09-22T20:30:15"
}
```

---

### 4. Eliminar Registro

* **Método:** `DELETE`
* **URL:** `http://localhost:8080/api/carpetas/1`

**Respuesta:** `204 No Content` (cuerpo vacío).

---

## ⚙️ Instalación y Ejecución

### Prerrequisitos

* **Java Development Kit (JDK) 17 o superior** instalado.
* **Git** instalado.

### Pasos para iniciar el proyecto

#### 1. Clonar el repositorio

```bash
git clone https://github.com/tu-usuario/ranking-carpetas.git
cd ranking-carpetas
```

#### 2. Ejecutar la aplicación

**Windows:**

```cmd
mvnw.cmd spring-boot:run
```

**Linux / macOS:**

```bash
./mvnw spring-boot:run
```

#### 3. Acceder a la aplicación

El servidor iniciará en el puerto `8080`.

---

## 🌐 Puntos de Acceso

### Aplicación Web

http://localhost:8080

### Consola H2 Database

http://localhost:8080/h2-console

**Configuración de conexión:**

| Campo            | Valor                           |
| ---------------- | ------------------------------- |
| **Driver Class** | `org.h2.Driver`                 |
| **JDBC URL**     | `jdbc:h2:file:./data/rankingdb` |
| **User Name**    | `sa`                            |
| **Password**     | *(dejar en blanco)*             |

---

## 🧪 Pruebas con cURL

### Subir un archivo comprimido

**Windows:**

```bash
curl.exe -X POST -F "archivo=@C:\ruta\a\tu_archivo.zip" http://localhost:8080/api/carpetas
```

### Obtener el ranking

```bash
curl.exe http://localhost:8080/api/carpetas/ranking
```

### Actualizar el nombre de un registro

```bash
curl.exe -X PUT http://localhost:8080/api/carpetas/1 -H "Content-Type: application/json" -d "{\"nombre\":\"nuevo_nombre\"}"
```

### Eliminar un registro

```bash
curl.exe -X DELETE http://localhost:8080/api/carpetas/1
```

---

## 📌 Resumen

La aplicación permite:

* 📦 Subir archivos `.zip`.
* 🔍 Analizar su contenido mediante *streaming*.
* 💾 Contar los archivos internos sin extraerlos físicamente.
* 🗄️ Persistir los resultados utilizando H2 y Spring Data JPA.
* 🏆 Generar un ranking de los registros con mayor cantidad de archivos.
* 🌐 Consultar la información mediante una interfaz web.
* 🔌 Consumir la funcionalidad mediante una API REST.
* ✏️ Actualizar registros existentes.
* 🗑️ Eliminar registros del sistema.
