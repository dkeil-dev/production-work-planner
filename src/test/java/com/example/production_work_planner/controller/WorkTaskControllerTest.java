package com.example.production_work_planner.controller;

import com.example.production_work_planner.entity.WorkTask;
import com.example.production_work_planner.enums.ProductionArea;
import com.example.production_work_planner.enums.TaskPriority;
import com.example.production_work_planner.enums.TaskStatus;
import com.example.production_work_planner.exception.WorkTaskNotFoundException;
import com.example.production_work_planner.service.WorkTaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;


import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WorkTaskController.class)
class WorkTaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WorkTaskService service;

    @Test
    void shouldReturnTaskById() throws Exception {
        WorkTask task = createTask();
        when(service.getById(1L)).thenReturn(task);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Prepare report"))
                .andExpect(jsonPath("$.status").value(TaskStatus.NEW.name()))
                .andExpect(jsonPath("$.priority").value(TaskPriority.NORMAL.name()))
                .andExpect(jsonPath("$.productionArea").value(ProductionArea.QUALITY_CONTROL.name()));

    }

    @Test
    void shouldReturnNotFoundWhenTaskDoesNotExist() throws Exception {
        when(service.getById(999L)).thenThrow(new WorkTaskNotFoundException(999L));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Work task not found with id: 999"));

        verify(service).getById(999L);
    }

    @Test
    void shouldReturnTasksByStatus() throws Exception {
        WorkTask task = createTask();

        when(service.findTasks(TaskStatus.NEW, null))
                .thenReturn(List.of(task));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks")
                        .param("status", "NEW"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Prepare report"))
                .andExpect(jsonPath("$[0].status").value(TaskStatus.NEW.name()));

        verify(service).findTasks(TaskStatus.NEW, null);

    }

    @Test
    void shouldReturnTasksByPriority() throws Exception {
        WorkTask task = createTask();

        when(service.findTasks(null, TaskPriority.NORMAL))
                .thenReturn(List.of(task));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks")
                        .param("priority", "NORMAL"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Prepare report"))
                .andExpect(jsonPath("$[0].priority").value(TaskPriority.NORMAL.name()));

        verify(service).findTasks(null, TaskPriority.NORMAL);
    }

    @Test
    void shouldReturnTasksByStatusAndPriority() throws Exception {
        WorkTask task = createTask();

        when(service.findTasks(TaskStatus.NEW, TaskPriority.NORMAL))
                .thenReturn(List.of(task));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks")
                        .param("status", "NEW")
                        .param("priority", "NORMAL"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Prepare report"))
                .andExpect(jsonPath("$[0].status").value(TaskStatus.NEW.name()))
                .andExpect(jsonPath("$[0].priority").value(TaskPriority.NORMAL.name()));

        verify(service).findTasks(TaskStatus.NEW, TaskPriority.NORMAL);
    }

    @Test
    void shouldReturnAllTasksWhenNoFiltersProvided() throws Exception {
        WorkTask task = createTask();

        when(service.findTasks(null, null))
                .thenReturn(List.of(task));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Prepare report"))
                .andExpect(jsonPath("$[0].status").value(TaskStatus.NEW.name()))
                .andExpect(jsonPath("$[0].priority").value(TaskPriority.NORMAL.name()));

        verify(service).findTasks(null, null);
    }


    private WorkTask createTask() {
        return new WorkTask(
                "Prepare report",
                "Weekly production report",
                TaskPriority.NORMAL,
                ProductionArea.QUALITY_CONTROL,
                "Ivanov",
                LocalDate.now(),
                LocalDate.now().plusDays(1)
        );
    }
}
