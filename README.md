# Taskify — Task Management REST API

A Spring Boot REST API application built for **SECJ4383 Software Construction — Assignment 2**.  
Taskify demonstrates clean MVC architecture with fully decoupled Controller, Service, and Repository layers, exposing task management functionalities as RESTful web services.

---

## Tech Stack

| Technology | Version |
|---|---|
| Java | 17 |
| Spring Boot | 4.0.6 |
| Spring Web | Included via starter |
| Spring Validation | `spring-boot-starter-validation` |
| Spring Actuator | `spring-boot-starter-actuator` |
| Build Tool | Maven |
| Storage | In-memory (no database) |

---

## Project Structure

```
src/main/java/com/example/taskify/
│
├── controller/
│   ├── TaskController.java              # REST endpoints
│   ├── request/
│   │   └── TaskRequestDTO.java          # Incoming request body
│   └── response/
│       └── TaskResponseDTO.java         # Outgoing response body
│
├── model/
│   ├── Task.java                        # Core domain model
│   └── TaskStatus.java                  # Enum: TODO, DONE
│
├── service/
│   └── TaskService.java                 # Business logic layer
│
├── repository/
│   └── TaskRepository.java              # In-memory data storage
│
└── TaskifyApplication.java              # Entry point
```

---

## Five Functionalities

| # | Functionality | Description |
|---|---|---|
| 1 | **Create Task** | Register a new task with title and description |
| 2 | **Get All Tasks** | Retrieve a list of all existing tasks |
| 3 | **Get Task by ID** | Retrieve a single task using its UUID |
| 4 | **Update Task Status** | Change a task status between `TODO` and `DONE` |
| 5 | **Delete Task** | Remove a task permanently by its UUID |

> Search by title and filter by status are additional functionalities also exposed as endpoints.

---

## REST API Endpoints

Base URL: `http://localhost:8080`

| Method | Endpoint | Description | Request |
|---|---|---|---|
| `POST` | `/api/tasks` | Create a new task | JSON body |
| `GET` | `/api/tasks` | Get all tasks | — |
| `GET` | `/api/tasks/{id}` | Get task by UUID | Path variable |
| `DELETE` | `/api/tasks/{id}` | Delete task by UUID | Path variable |
| `GET` | `/api/tasks/search?title=` | Search tasks by title keyword | Query param |
| `GET` | `/api/tasks/status?status=` | Filter tasks by status | Query param (`TODO` or `DONE`) |
| `PUT` | `/api/tasks/{id}/status?status=` | Update task status | Path variable + Query param |

### Request Body (POST — Create Task)

```json
{
  "title": "Complete Assignment 2",
  "description": "Build Spring Boot REST API for Software Construction subject"
}
```

### Response Body

```json
{
  "id": "a3f1c2d4-...",
  "title": "Complete Assignment 2",
  "description": "Build Spring Boot REST API for Software Construction subject",
  "status": "TODO"
}
```

### Task Status Enum

Only two valid values:
- `TODO` — default status when a task is created
- `DONE` — task has been completed

---

## Validation Rules

| Field | Rule | Error Message |
|---|---|---|
| `title` | `@NotBlank` | `Title is required` |
| `description` | `@NotBlank` | `Description is required` |

Sending a blank, whitespace-only, or missing field returns `400 Bad Request`.

---

## Setup & Running the Application

### Prerequisites

- Java 17 installed
- Maven installed (or use the included `mvnw` wrapper)

### Steps

**1. Clone the repository**
```bash
git clone https://github.com/Alielber1010/taskify.git
cd taskify
```

**2. Run the application**
```bash
./mvnw spring-boot:run
```
Or on Windows:
```bash
mvnw.cmd spring-boot:run
```

**3. Verify it's running**

Open your browser or Postman and hit:
```
GET http://localhost:8080/api/tasks
```
Expected response: `[]` (empty array on first run)

---

## Testing with Postman

All endpoints were tested using **Postman Desktop**.

Import the provided collection file `Taskify_Postman_Final.json` into Postman:

1. Open Postman → click **Import**
2. Drag and drop `Taskify_Postman_Final.json`
3. The collection loads with all requests pre-configured and grouped by folder

The collection includes a **auto-capture script** on every POST request — it automatically saves the returned UUID into a `{{taskId}}` variable, which is then used automatically in GET by ID, PUT, and DELETE requests. No manual copy-pasting needed.

### Recommended testing order

1. Run `Create Task` (POST) — creates a task, auto-saves `{{taskId}}`
2. Run `Get All Tasks` (GET) — confirms task exists
3. Run `Get Task by ID` (GET) — uses `{{taskId}}` automatically
4. Run `Filter by Status TODO` (GET) — shows newly created tasks
5. Run `Update Status to DONE` (PUT) — updates the task
6. Run `Filter by Status DONE` (GET) — confirms the update
7. Run `Search by Title` (GET) — search by keyword
8. Run `Delete Task` (DELETE) — removes the task
9. Run `Get All Tasks` again — confirms deletion

---

## Group Members

| Name | Matric No. |
|---|---|
| Ali | A22EC0009 |
| Adam | A22EC0002 |
| Shams  | A22EC4041 |
| Albatoul  | A22EC4026 |

---

## Course Information

- **Subject:** SECJ4383 Software Construction
- **Assignment:** Assignment 2 — REST API Spring Boot Application
- **Topic:** Topic 7 — RESTful Web Services
