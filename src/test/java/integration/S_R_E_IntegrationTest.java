package integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.exceptions.ValidationException;
import tasks.model.ArrayTaskList;
import tasks.model.Task;
import tasks.services.TasksService;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class S_R_E_IntegrationTest { // TasksService + ArrayTaskList + Task
    private TasksService service;

    @BeforeEach
    void setUp() {
        ArrayTaskList repository = new ArrayTaskList();
        service = new TasksService(repository);
    }

    @Test
    void getObservableListTest() {
        assertEquals(0, service.getObservableList().size());
    }

    @Test
    void addTaskSuccessTest() {
        assertEquals(0, service.getObservableList().size());
        service.addTask(new Task("task", new Date()));
        assertEquals(1, service.getObservableList().size());
    }

    @Test
    void addTaskFailTest() {
        assertEquals(0, service.getObservableList().size());
        assertThrows(ValidationException.class, () -> service.addTask(new Task("task", new Date(-1))));
        assertEquals(0, service.getObservableList().size());
    }
}
