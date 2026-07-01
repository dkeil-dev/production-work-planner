package com.example.production_work_planner.dto;


import com.example.production_work_planner.entity.WorkTask;
import com.example.production_work_planner.enums.ProductionArea;
import com.example.production_work_planner.enums.TaskPriority;
import com.example.production_work_planner.enums.TaskStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record WorkTaskResponse(
        Long id,
        String title,
        String description,
        TaskStatus status,
        TaskPriority priority,
        ProductionArea productionArea,
        String assigneeName,
        LocalDate plannedStartDate,
        LocalDate plannedEndDate,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        boolean overdue
) {
    public static WorkTaskResponse from(WorkTask task) {
        return new WorkTaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                task.getProductionArea(),
                task.getAssigneeName(),
                task.getPlannedStartDate(),
                task.getPlannedEndDate(),
                task.getCreatedAt(),
                task.getUpdatedAt(),
                task.isOverdue()
        );
    }
}