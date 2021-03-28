package tasks.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tasks.exceptions.ValidationException;
import tasks.model.ArrayTaskList;
import tasks.model.Task;
import tasks.services.TaskIO;
import tasks.services.TasksService;
import tasks.view.Main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(MockitoExtension.class)
class NewEditControllerTest {

    NewEditController newEditController;

    @Mock
    private TasksService mockTasksService;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private String title_ok = "d";
    private String title_ok_2 = "dd";
    private String title_fail = "";
    private Date start, start1, start2;
    private Date end, end1, end2;
    {
        try {
            start = sdf.parse("2021-01-01");
            end = sdf.parse("2021-01-02");

            start1 = sdf.parse("2021-03-03");
            end1 = sdf.parse("2021-03-04");

            start2 = sdf.parse("2022-03-03");
            end2 = sdf.parse("2021-03-03");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private long interval_ok = 10000;
    private long interval_fail = 0;
    private Task task_BVA_ok_1 = new Task(title_ok, start, end, interval_ok); // "d" 1
    private Task task_BVA_ok_2 = new Task(title_ok_2, start, end, interval_ok); // "dd" 1


    private ArrayTaskList tasks;

    private ObservableList<Task> tasksList;

    private static ClassLoader classLoader = Main.class.getClassLoader();
    private ArrayTaskList savedTasksList = new ArrayTaskList();
    private File savedTasksFile;
    @BeforeEach
    void setUp() {
        tasks = new ArrayTaskList();
        mockTasksService = new TasksService(tasks);
        tasksList = FXCollections.observableArrayList(tasks.getAll());

        newEditController = new NewEditController();
        newEditController.setTasksList(tasksList);
        newEditController.setService(mockTasksService);
        savedTasksFile = new File(classLoader.getResource("data/tasks.txt").getFile());

    }

    @AfterEach
    void tearDown() {
        savedTasksList = new ArrayTaskList();
        FileWriter f2 = null;
        try {
            f2 = new FileWriter(savedTasksFile, false);
            f2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("BVA_OK1_add")
    @Tag("BBT")
    void saveChanges_BVA_valid_title1() {
        newEditController.saveChangesButCanBeTested(task_BVA_ok_1);
        Task savedTask = newEditController.getTasksList().get(0);
        assertEquals(task_BVA_ok_1, savedTask);
        try {
            TaskIO.readBinary(savedTasksList, savedTasksFile);
            assertEquals(1, savedTasksList.size());
            assertEquals(task_BVA_ok_1, savedTasksList.getTask(0));
        } catch (IOException e) {
            fail("File must be found!");
        }
    }

    @Test
    @DisplayName("BVA_OK2_add")
    @Tag("BBT")
    void saveChanges_BVA_valid_title2() {
        newEditController.saveChangesButCanBeTested(task_BVA_ok_2);
        Task savedTask = newEditController.getTasksList().get(0);
        assertEquals(task_BVA_ok_2, savedTask);
        try {
            TaskIO.readBinary(savedTasksList, savedTasksFile);
            assertEquals(1, savedTasksList.size());
            assertEquals(task_BVA_ok_2, savedTasksList.getTask(0));
        } catch (IOException e) {
            fail("File must be found!");
        }
    }

    @Test
    @DisplayName("BVA_FAIL1_add")
    @Tag("BBT")
    void saveChanges_BVA_invalid_1() {

        assertThrows(ValidationException.class, () -> {
            Task task_BVA_fail_1 = new Task(title_fail, start, end, interval_ok); // "" 1
            newEditController.saveChangesButCanBeTested(task_BVA_fail_1);
        });
        try {
            TaskIO.readBinary(savedTasksList, savedTasksFile);
            assertEquals(0, savedTasksList.size());
        } catch (IOException e) {
            fail("File must be found!");
        }
    }

    @Test
    @DisplayName("BVA_FAIL2_add")
    @Tag("BBT")
    void saveChanges_BVA_invalid_2() {
        assertThrows(ValidationException.class, () -> {
            Task task_BVA_fail_2 = new Task(title_ok, start, end, interval_fail); // "d" 0
            newEditController.saveChangesButCanBeTested(task_BVA_fail_2);
        });
        try {
            TaskIO.readBinary(savedTasksList, savedTasksFile);
            assertEquals(0, savedTasksList.size());
        } catch (IOException e) {
            fail("File must be found!");
        }
    }

    @Test
    @DisplayName("ECP_OK1_add")
    @Tag("BBT")
    void saveChanges_ECP_valid_1() {
        Task task_ECP_ok_1 = new Task("Task 1", start1, end1, interval_ok);

        newEditController.saveChangesButCanBeTested(task_ECP_ok_1);
        Task savedTask = newEditController.getTasksList().get(0);
        assertEquals(task_ECP_ok_1, savedTask);
        try {
            TaskIO.readBinary(savedTasksList, savedTasksFile);
            assertEquals(1, savedTasksList.size());
            assertEquals(task_ECP_ok_1, savedTasksList.getTask(0));
        } catch (IOException e) {
            fail("File must be found!");
        }
    }

    @Test
    @DisplayName("ECP_OK2_add")
    @Tag("BBT")
    void saveChanges_ECP_valid_2() {
        Task task_ECP_ok_2 = new Task("Task 2", start2, end2, interval_ok);
        newEditController.saveChangesButCanBeTested(task_ECP_ok_2);
        Task savedTask = newEditController.getTasksList().get(0);
        assertEquals(task_ECP_ok_2, savedTask);
        try {
            TaskIO.readBinary(savedTasksList, savedTasksFile);
            assertEquals(1, savedTasksList.size());
            assertEquals(task_ECP_ok_2, savedTasksList.getTask(0));
        } catch (IOException e) {
            fail("File must be found!");
        }
    }

    @Test
    @DisplayName("ECP_FAIL1_add")
    @Tag("BBT")
    void saveChanges_ECP_invalid_1() {
        assertThrows(ValidationException.class, () -> {
            Task task_ECP_fail_1 = new Task("", start1, end1, interval_ok);
            newEditController.saveChangesButCanBeTested(task_ECP_fail_1);
        });
        try {
            TaskIO.readBinary(savedTasksList, savedTasksFile);
            assertEquals(0, savedTasksList.size());
        } catch (IOException e) {
            fail("File must be found!");
        }
    }

    @Test
    @DisplayName("ECP_FAIL2_add")
    @Tag("BBT")
    void saveChanges_ECP_invalid_2() {
        assertThrows(ValidationException.class, () -> {
            Task task_ECP_fail_2 = new Task(null, null, null, interval_ok);
            newEditController.saveChangesButCanBeTested(task_ECP_fail_2);
        });
        try {
            TaskIO.readBinary(savedTasksList, savedTasksFile);
            assertEquals(0, savedTasksList.size());
        } catch (IOException e) {
            fail("File must be found!");
        }
    }
}