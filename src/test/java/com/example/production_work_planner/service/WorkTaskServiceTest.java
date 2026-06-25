package com.example.production_work_planner.service;

import com.example.production_work_planner.entity.WorkTask;
import com.example.production_work_planner.enums.ProductionArea;
import com.example.production_work_planner.enums.TaskPriority;
import com.example.production_work_planner.enums.TaskStatus;
import com.example.production_work_planner.exception.WorkTaskNotFoundException;
import com.example.production_work_planner.repository.WorkTaskRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WorkTaskServiceTest {


    @Test
    void shouldReturnTaskById() {
        WorkTaskRepository repository = mock(WorkTaskRepository.class);
        WorkTaskService service = new WorkTaskService(repository);

        WorkTask task = createTask();

        when(repository.findById(1L)).thenReturn(Optional.of(task));

        WorkTask result = service.getById(1L);

        assertSame(task, result);

    }

    @Test
    void shouldThrowExceptionWhenTaskNotFound() {
        WorkTaskRepository repository = mock(WorkTaskRepository.class);
        WorkTaskService service = new WorkTaskService(repository);

        when(repository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(WorkTaskNotFoundException.class,
                () -> service.getById(999L));
    }

    @Test
    void shouldUpdateStatus() {
        WorkTaskRepository repository = mock(WorkTaskRepository.class);
        WorkTaskService service = new WorkTaskService(repository);

        WorkTask task = createTask();

        when(repository.findById(1L)).thenReturn(Optional.of(task));
        when(repository.save(task)).thenReturn(task);

        WorkTask result = service.updateStatus(1L, TaskStatus.IN_PROGRESS);
        assertEquals(TaskStatus.IN_PROGRESS, result.getStatus());
        assertNotNull(result.getUpdatedAt());

        verify(repository).findById(1L);
        verify(repository).save(task);

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
