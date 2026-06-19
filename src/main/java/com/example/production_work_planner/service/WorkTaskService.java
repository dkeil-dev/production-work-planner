package com.example.production_work_planner.service;


import com.example.production_work_planner.dto.CreateWorkTaskRequest;
import com.example.production_work_planner.entity.WorkTask;
import com.example.production_work_planner.repository.WorkTaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkTaskService {
    private final WorkTaskRepository repository;

    public WorkTaskService(WorkTaskRepository repository) {
        this.repository = repository;
    }

    public WorkTask create(CreateWorkTaskRequest request) {
        WorkTask task = new WorkTask(
                request.getTitle(),
                request.getDescription(),
                request.getPriority(),
                request.getProductionArea(),
                request.getAssigneeName(),
                request.getPlannedStartDate(),
                request.getPlannedEndDate()
        );

        return repository.save(task);



    }

    public WorkTask getById(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));
    }

    public List<WorkTask> getAll() {
        return repository.findAll();
    }

}
