package tasks.model;

import org.junit.jupiter.api.Test;
import tasks.exceptions.ValidationException;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

class TaskTest {

    @Test
    void createTaskErrorTest(){
        assertThrows(ValidationException.class, () -> new Task(null, new Date(-1)));
    }

    @Test
    void createTaskSuccessTest() {
        try {
            new Task("task", new Date(1));
            assert true;
        } catch (ValidationException ignored) {
            fail();
        }
    }
}
