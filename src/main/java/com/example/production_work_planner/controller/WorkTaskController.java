package com.example.production_work_planner.controller;

import com.example.production_work_planner.dto.CreateWorkTaskRequest;
import com.example.production_work_planner.dto.UpdateTaskStatusRequest;
import com.example.production_work_planner.dto.WorkTaskResponse;
import com.example.production_work_planner.entity.WorkTask;
import com.example.production_work_planner.enums.TaskPriority;
import com.example.production_work_planner.enums.TaskStatus;
import com.example.production_work_planner.service.WorkTaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class WorkTaskController {

    private final WorkTaskService service;

    public WorkTaskController(WorkTaskService service) {
        this.service = service;
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public WorkTaskResponse create(@Valid @RequestBody CreateWorkTaskRequest request) {
        WorkTask task = service.create(request);
        return WorkTaskResponse.from(task);
    }

    @GetMapping
    public List<WorkTaskResponse> getAll(
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) TaskPriority priority
    ) {

        return service.findTasks(status, priority)
                .stream()
                .map(WorkTaskResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    public WorkTaskResponse getById(@PathVariable Long id) {
        WorkTask task = service.getById(id);
        return WorkTaskResponse.from(task);
    }

    @PatchMapping("/{id}/status")
    public WorkTaskResponse changeStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTaskStatusRequest request
    ) {
        WorkTask task = service.updateStatus(id, request.getStatus());
        return WorkTaskResponse.from(task);

    }


}
