package tasks.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ArrayTaskListTest {

    private ArrayTaskList tasks;

    @Mock
    private Task task = mock(Task.class);

    @BeforeEach
    void setUp() {
        tasks = new ArrayTaskList();
    }

    @Test
    void addTest() {
        assertEquals(0, tasks.size());
        tasks.add(task);
        assertEquals(1, tasks.size());
    }

    @Test
    void removeTest() {
        tasks.add(task);
        assertEquals(1, tasks.size());
        tasks.remove(task);
        assertEquals(0, tasks.size());
    }
}
