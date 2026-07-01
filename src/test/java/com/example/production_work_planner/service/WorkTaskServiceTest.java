package com.example.production_work_planner.service;

import com.example.production_work_planner.dto.CreateWorkTaskRequest;
import com.example.production_work_planner.entity.WorkTask;
import com.example.production_work_planner.enums.ProductionArea;
import com.example.production_work_planner.enums.TaskPriority;
import com.example.production_work_planner.enums.TaskStatus;
import com.example.production_work_planner.exception.WorkTaskNotFoundException;
import com.example.production_work_planner.repository.WorkTaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WorkTaskServiceTest {

    private WorkTaskRepository repository;
    private WorkTaskService service;

    @BeforeEach
    void setUp() {
         repository = mock(WorkTaskRepository.class);
         service = new WorkTaskService(repository);

    }


    @Test
    void shouldReturnTaskById() {


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

    @Test
    void shouldCreateTask() {
        WorkTaskRepository repository = mock(WorkTaskRepository.class);
        WorkTaskService service = new WorkTaskService(repository);

        CreateWorkTaskRequest request = new CreateWorkTaskRequest();
        request.setTitle("Prepare report");
        request.setDescription("Weekly production report");
        request.setPriority(TaskPriority.NORMAL);
        request.setProductionArea(ProductionArea.QUALITY_CONTROL);
        request.setAssigneeName("Ivanov");
        request.setPlannedStartDate(LocalDate.now());
        request.setPlannedEndDate(LocalDate.now().plusDays(1));

        when(repository.save(any(WorkTask.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        WorkTask result = service.create(request);

        assertEquals("Prepare report", result.getTitle());
        assertEquals(TaskStatus.NEW, result.getStatus());
        assertEquals(TaskPriority.NORMAL, result.getPriority());
        assertEquals(ProductionArea.QUALITY_CONTROL, result.getProductionArea());
        assertNotNull(result.getCreatedAt());

        verify(repository).save(any(WorkTask.class));

    }

    @Test
    void shouldFindTasksByStatusAndPriority() {
        WorkTaskRepository repository = mock(WorkTaskRepository.class);
        WorkTaskService service = new WorkTaskService(repository);

        WorkTask task = createTask();

        when(repository.findByStatusAndPriority(TaskStatus.NEW, TaskPriority.NORMAL))
                .thenReturn(List.of(task));

        List<WorkTask> result = service.findTasks(TaskStatus.NEW, TaskPriority.NORMAL);

        assertEquals(1, result.size());
        assertSame(task, result.get(0));

        verify(repository).findByStatusAndPriority(TaskStatus.NEW, TaskPriority.NORMAL);
        verify(repository, never()).findAll();
    }

    @Test
    void shouldFindTasksByStatus() {
        WorkTaskRepository repository = mock(WorkTaskRepository.class);
        WorkTaskService service = new WorkTaskService(repository);

        WorkTask task = createTask();

        when(repository.findByStatus(TaskStatus.NEW))
                .thenReturn(List.of(task));

        List<WorkTask> result = service.findTasks(TaskStatus.NEW, null);

        assertEquals(1, result.size());
        assertSame(task, result.get(0));

        verify(repository).findByStatus(TaskStatus.NEW);
        verify(repository, never()).findAll();
    }

    @Test
    void shouldFindTasksByPriority() {
        WorkTaskRepository repository = mock(WorkTaskRepository.class);
        WorkTaskService service = new WorkTaskService(repository);

        WorkTask task = createTask();

        when(repository.findByPriority(TaskPriority.NORMAL))
                .thenReturn(List.of(task));

        List<WorkTask> result = service.findTasks(null, TaskPriority.NORMAL);

        assertEquals(1, result.size());
        assertSame(task, result.get(0));

        verify(repository).findByPriority(TaskPriority.NORMAL);
        verify(repository, never()).findAll();
    }

    @Test
    void shouldFindAllTasksWhenNoFiltersProvided() {
        WorkTaskRepository repository = mock(WorkTaskRepository.class);
        WorkTaskService service = new WorkTaskService(repository);

        WorkTask task = createTask();

        when(repository.findAll()).thenReturn(List.of(task));

        List<WorkTask> result = service.findTasks(null, null);

        assertEquals(1, result.size());
        assertSame(task, result.get(0));
        verify(repository).findAll();
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
