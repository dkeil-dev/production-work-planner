package com.example.production_work_planner.service;


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

    public List<WorkTask> getAll() {
        return repository.findAll();
    }
}
