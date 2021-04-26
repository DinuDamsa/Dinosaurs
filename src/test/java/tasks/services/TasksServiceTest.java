package tasks.services;

import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import tasks.model.ArrayTaskList;
import tasks.model.Task;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TasksServiceTest {

    private TasksService service;

    @Mock
    private ArrayTaskList tasks = mock(ArrayTaskList.class);

    @Mock
    private Task task = mock(Task.class);

    @BeforeEach
    void setUp() {
        service = new TasksService(tasks);
    }

    @Test
    void addTaskTest() {
        service.addTask(task);
        verify(tasks).add(any());
    }

    @Test
    void getObservableListTest() {
        when(tasks.getAll()).thenReturn(Arrays.asList(task));
        ObservableList<Task> list = service.getObservableList();
        assertEquals(1, list.toArray().length);
    }
}
