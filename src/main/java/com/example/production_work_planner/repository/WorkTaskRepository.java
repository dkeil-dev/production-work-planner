package com.example.production_work_planner.repository;

import com.example.production_work_planner.entity.WorkTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkTaskRepository extends JpaRepository<WorkTask,Long> {
}
