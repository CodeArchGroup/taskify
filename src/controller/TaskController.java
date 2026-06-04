package com.example.taskify.controller;

import com.example.taskify.controller.request.TaskRequestDTO;
import com.example.taskify.controller.response.TaskResponseDTO;
import com.example.taskify.model.TaskStatus;
import com.example.taskify.model.Task;
import com.example.taskify.service.TaskServiceInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskServiceInterface taskServiceInterface;

    public TaskController( TaskServiceInterface taskServiceInterface) {
        this.taskServiceInterface = taskServiceInterface;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<TaskResponseDTO> create(@RequestBody TaskRequestDTO dto) {
        Task task = toDomain(dto);
        TaskResponseDTO response = toDTO(taskServiceInterface.createTask(task));
        return ResponseEntity.status(201).body(response);
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getAll() {
        return ResponseEntity.ok(
                taskServiceInterface.getAllTasks().stream().map(this::toDTO).toList()
        );
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getById(@PathVariable String id) {
        return ResponseEntity.ok(toDTO(taskServiceInterface.getTaskById(id)));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        taskServiceInterface.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    // SEARCH
    @GetMapping("/search")
    public ResponseEntity<List<TaskResponseDTO>> search(@RequestParam String title) {
        return ResponseEntity.ok(
                taskServiceInterface.searchByTitle(title).stream().map(this::toDTO).toList()
        );
    }

    // FILTER
    @GetMapping("/status")
    public ResponseEntity<List<TaskResponseDTO>> filter(@RequestParam TaskStatus status) {
        return ResponseEntity.ok(
                taskServiceInterface.filterByStatus(status).stream().map(this::toDTO).toList()
        );
    }

    // UPDATE STATUS
    @PutMapping("/{id}/status")
    public ResponseEntity<TaskResponseDTO> updateStatus(
            @PathVariable String id,
            @RequestParam TaskStatus status) {

        return ResponseEntity.ok(
                toDTO(taskServiceInterface.updateStatus(id, status))
        );
    }

    // =========================
    // MAPPERS (inside controller)
    // =========================

    private Task toDomain(TaskRequestDTO dto) {
        return new Task(dto.getTitle(), dto.getDescription());
    }

    private TaskResponseDTO toDTO(Task task) {
        return new TaskResponseDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus()
        );
    }
}