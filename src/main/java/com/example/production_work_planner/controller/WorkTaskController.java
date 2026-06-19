package com.example.production_work_planner.controller;

import com.example.production_work_planner.dto.CreateWorkTaskRequest;
import com.example.production_work_planner.dto.UpdateTaskStatusRequest;
import com.example.production_work_planner.entity.WorkTask;
import com.example.production_work_planner.service.WorkTaskService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class WorkTaskController {

    private final WorkTaskService service;

    public WorkTaskController(WorkTaskService service) {
        this.service = service;
    }

    @PostMapping
    public WorkTask create(@Valid @RequestBody CreateWorkTaskRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<WorkTask> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public WorkTask getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PatchMapping("/{id}/status")
    public WorkTask changeStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTaskStatusRequest request
    ){
        return service.updateStatus(id, request.getStatus());

    }



}
