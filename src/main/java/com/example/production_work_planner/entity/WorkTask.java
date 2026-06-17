package com.example.production_work_planner.entity;


import com.example.production_work_planner.enums.ProductionArea;
import com.example.production_work_planner.enums.TaskPriority;
import com.example.production_work_planner.enums.TaskStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "work_tasks")
public class WorkTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskPriority priority;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductionArea productionArea;

    private String assigneeName;

    private LocalDate plannedStartDate;

    @Column(nullable = false)
    private LocalDate plannedEndDate;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    protected WorkTask() {
    }

    public WorkTask(String title,
                    String description,
                    TaskStatus status,
                    TaskPriority priority,
                    ProductionArea productionArea,
                    String assigneeName,
                    LocalDate plannedStartDate,
                    LocalDate plannedEndDate,
                    LocalDateTime createdAt
    ) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }

        if (priority == null) {
            throw new IllegalArgumentException("Priority cannot be null");
        }

        if (productionArea == null) {
            throw new IllegalArgumentException("Production area cannot be null");
        }

        if (plannedEndDate == null) {
            throw new IllegalArgumentException("Planned end date cannot be null");
        }

        if (plannedStartDate != null && plannedEndDate.isBefore(plannedStartDate)) {
            throw new IllegalArgumentException("Planned end date cannot be before planned start date");
        }
        this.title = title;
        this.description = description;
        this.status = TaskStatus.NEW;
        this.priority = priority;
        this.productionArea = productionArea;
        this.assigneeName = assigneeName;
        this.plannedStartDate = plannedStartDate;
        this.plannedEndDate = plannedEndDate;
        this.createdAt = LocalDateTime.now();
    }

    public void changeStatus(TaskStatus newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }

        if (this.status == TaskStatus.DONE) {
            throw new IllegalStateException("Done task cannot be changed");
        }

        this.status = newStatus;
        this.updatedAt = LocalDateTime.now();
    }

    public void changePriority(TaskPriority newPriority) {
        if (newPriority == null) {
            throw new IllegalArgumentException("Priority cannot be null");
        }

        if (this.status == TaskStatus.DONE) {
            throw new IllegalStateException("Done task cannot be changed");
        }

        this.priority = newPriority;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isOverdue() {
        return status != TaskStatus.DONE
                && plannedEndDate.isBefore(LocalDate.now());
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public TaskStatus getStatus() {
        return status;
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

    public LocalDate getPlannedStartDate() {
        return plannedStartDate;
    }

    public LocalDate getPlannedEndDate() {
        return plannedEndDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }


}
