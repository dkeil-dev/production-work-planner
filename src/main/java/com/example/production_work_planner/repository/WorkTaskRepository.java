package com.example.production_work_planner.repository;

import com.example.production_work_planner.entity.WorkTask;
import com.example.production_work_planner.enums.TaskPriority;
import com.example.production_work_planner.enums.TaskStatus;
import org.hibernate.jdbc.Work;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkTaskRepository extends JpaRepository<WorkTask, Long> {
    List<WorkTask> findByStatus(TaskStatus status);

    List<WorkTask> findByPriority(TaskPriority priority);

    List<WorkTask> findByStatusAndPriority(TaskStatus status, TaskPriority priority);
}
