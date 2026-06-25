package com.example.production_work_planner.entity;

import com.example.production_work_planner.enums.ProductionArea;
import com.example.production_work_planner.enums.TaskPriority;
import com.example.production_work_planner.enums.TaskStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


class WorkTaskTest {

    @Test
    void shouldCreateTaskWithNewStatus() {
        WorkTask task = new WorkTask(
                "Prepare report",
                "Weekly production report",
                TaskPriority.NORMAL,
                ProductionArea.QUALITY_CONTROL,
                "Ivanov",
                LocalDate.now(),
                LocalDate.now().plusDays(1)
        );
        assertEquals(TaskStatus.NEW, task.getStatus());
        assertNotNull(task.getCreatedAt());
    }

    @Test
    void shouldChangeStatus() {
        WorkTask task = createTask();
        task.changeStatus(TaskStatus.IN_PROGRESS);

        assertEquals(TaskStatus.IN_PROGRESS, task.getStatus());
        assertNotNull(task.getUpdatedAt());
    }

    @Test
    void shouldChangePriority() {
        WorkTask task = createTask();
        task.changePriority(TaskPriority.HIGH);

        assertEquals(TaskPriority.HIGH, task.getPriority());
        assertNotNull(task.getUpdatedAt());
    }

    @Test
    void shouldThrowExceptionWhenPriorityIsNullDuringChange() {
        WorkTask task = createTask();

        assertThrows(
                IllegalArgumentException.class,
                () -> task.changePriority(null)
        );
    }

    @Test
    void shouldThrowExceptionWhenTryingToChangePriorityOfDoneTask() {
        WorkTask task = createTask();

        task.changeStatus(TaskStatus.DONE);

        assertThrows(
                IllegalStateException.class,
                () -> task.changePriority(TaskPriority.HIGH)
        );
    }


    @Test
    void shouldThrowExceptionWhenTryingToChangeDoneStatus() {
        WorkTask task = createTask();
        task.changeStatus(TaskStatus.DONE);

        assertThrows(IllegalStateException.class,
                () -> task.changeStatus(TaskStatus.IN_PROGRESS));

    }

    @Test
    void shouldThrowExceptionWhenStatusIsNull() {
        WorkTask task = createTask();

        assertThrows(IllegalArgumentException.class,
                () -> task.changeStatus(null));
    }


    @Test
    void shouldThrowExceptionWhenTitleIsBlank() {
        assertThrows(IllegalArgumentException.class,
                () -> new WorkTask(
                        "",
                        "Description",
                        TaskPriority.NORMAL,
                        ProductionArea.QUALITY_CONTROL,
                        "Ivanov",
                        LocalDate.now(),
                        LocalDate.now().plusDays(1)
                ));
    }

    @Test
    void shouldThrowExceptionWhenPriorityIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new WorkTask(
                        "Title",
                        "Description",
                        null,
                        ProductionArea.QUALITY_CONTROL,
                        "Ivanov",
                        LocalDate.now(),
                        LocalDate.now().plusDays(1)
                ));
    }

    @Test
    void shouldThrowExceptionWhenProductionAreaIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new WorkTask(
                        "Title",
                        "Description",
                        TaskPriority.NORMAL,
                        null,
                        "Ivanov",
                        LocalDate.now(),
                        LocalDate.now().plusDays(1)
                ));
    }

    @Test
    void shouldThrowExceptionWhenPlannedEndDateIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new WorkTask(
                        "Title",
                        "Description",
                        TaskPriority.NORMAL,
                        ProductionArea.QUALITY_CONTROL,
                        "Ivanov",
                        LocalDate.now(),
                        null
                ));
    }

    @Test
    void shouldThrowExceptionWhenPlannedDateIsBeforeStartDate() {
        assertThrows(IllegalArgumentException.class,
                () -> new WorkTask(
                        "Title",
                        "Description",
                        TaskPriority.NORMAL,
                        ProductionArea.QUALITY_CONTROL,
                        "Ivanov",
                        LocalDate.now(),
                        LocalDate.now().minusDays(2)
                ));
    }


    @Test
    void shouldBeOverdue() {
        WorkTask task = new WorkTask(
                "Task",
                "Description",
                TaskPriority.NORMAL,
                ProductionArea.QUALITY_CONTROL,
                "Ivanov",
                LocalDate.now().minusDays(5),
                LocalDate.now().minusDays(1)
        );

        assertTrue(task.isOverdue());
    }

    @Test
    void shouldNotBeOverdueWhenTaskIsDone() {
        WorkTask task = new WorkTask(
                "Task",
                "Description",
                TaskPriority.NORMAL,
                ProductionArea.QUALITY_CONTROL,
                "Ivanov",
                LocalDate.now().minusDays(5),
                LocalDate.now().minusDays(1)
        );

        task.changeStatus(TaskStatus.DONE);

        assertFalse(task.isOverdue());
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
