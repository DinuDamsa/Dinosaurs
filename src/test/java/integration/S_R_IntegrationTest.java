package integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import tasks.model.ArrayTaskList;
import tasks.model.Task;
import tasks.services.TasksService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class S_R_IntegrationTest { // TasksService + ArrayTaskList
    private TasksService service;

    @Mock
    private Task task = mock(Task.class);

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
    void addTaskTest() {
        assertEquals(0, service.getObservableList().size());
        service.addTask(task);
        assertEquals(1, service.getObservableList().size());
    }
}
