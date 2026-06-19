package com.example.production_work_planner.dto;

import com.example.production_work_planner.enums.ProductionArea;
import com.example.production_work_planner.enums.TaskPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class CreateWorkTaskRequest {
    @NotBlank
    private String title;

    private String description;

    @NotNull
    private TaskPriority priority;

    @NotNull
    private ProductionArea productionArea;

    private String assigneeName;

    private LocalDate plannedStartDate;

    @NotNull
    private LocalDate plannedEndDate;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public ProductionArea getProductionArea() {
        return productionArea;
    }

    public String getAssigneeName() {
        return assigneeName;
    }

    public LocalDate getPlannedEndDate() {
        return plannedEndDate;
    }

    public LocalDate getPlannedStartDate() {
        return plannedStartDate;
    }
}
