package tasks.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.exceptions.ValidationException;
import tasks.view.MyTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;

class TaskSchedulerTest {

    private LocalDate localDate;
    private TaskScheduler taskScheduler;

    private String endTimeValue;

    private Date endInterval;
    private Date start;
    private Date end;
    private Date datedate;
    private LocalDate startDateButLocalDate;

    @BeforeEach
    void setUp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String date = "01/01/2000";
        localDate = LocalDate.parse(date, formatter);

        List<Task> tasks = new ArrayList<>();
        taskScheduler = new TaskScheduler(tasks);

        endTimeValue = "00:00";
        String startDateValue = "10/04/2021";

        String startDate = "01/04/2021";
        String endDate = "05/04/2021";

        startDateButLocalDate = LocalDate.parse(startDate, formatter);

        try {
            endInterval = new SimpleDateFormat("dd/MM/yyyy").parse(startDateValue);
            start = new SimpleDateFormat("dd/MM/yyyy").parse(startDate);
            end = new SimpleDateFormat("dd/MM/yyyy").parse(endDate);
            datedate = new SimpleDateFormat("dd/MM/yyyy").parse(date);
        } catch (ParseException e) {
            // it will work, no worries :D
        }
    }

    @Test
    void incomingThrowsTimeContainsAlpha() {
        String startTimeValue = "manual";
        assertThrows(ValidationException.class, () -> taskScheduler.incoming(localDate, startTimeValue, new Date()), "Should have thrown exception because of bad time!");
    }

    @Test
    void incomingThrowsTimeSplitFormat() {
        String startTimeValue = "0000";
        assertThrows(ValidationException.class, () -> taskScheduler.incoming(localDate, startTimeValue, new Date()), "Should have thrown exception because of bad time!");
    }

    @Test
    void incomingThrowsTimeIntervalBadHours() {
        String startTimeValue = "99:10";
        assertThrows(ValidationException.class, () -> taskScheduler.incoming(localDate, startTimeValue, new Date()), "Should have thrown exception because of bad time!");
    }

    @Test
    void incomingThrowsTimeIntervalBadMinutes() {
        String startTimeValue = "10:99";
        assertThrows(ValidationException.class, () -> taskScheduler.incoming(localDate, startTimeValue, new Date()), "Should have thrown exception because of bad time!");
    }

    @Test
    void incomingThrowsTimeIntervalBadAll() {
        String startTimeValue = "99:99";
        assertThrows(ValidationException.class, () -> taskScheduler.incoming(localDate, startTimeValue, new Date()), "Should have thrown exception because of bad time!");
    }

    @Test
    void incomingEmptyResultEmptyList() {
        Iterable<Task> filtered = taskScheduler.incoming(localDate, endTimeValue, endInterval);
        List<Task> list = StreamSupport.stream(filtered.spliterator(), false)
                .collect(Collectors.toList());
        assertEquals(0, list.size());
    }

    @Test
    void incomingEmptyResultNoActiveRepetitiveTasks() {

        Task task = new Task("no_active_rep", start, end, 10);
        task.setActive(false);
        taskScheduler.tasks.add(task);

        Iterable<Task> filtered = taskScheduler.incoming(localDate, endTimeValue, endInterval);
        List<Task> list = StreamSupport.stream(filtered.spliterator(), false)
                .collect(Collectors.toList());
        assertEquals(0, list.size());
    }

    @Test
    void incomingEmptyResultActiveNonRepetitiveTasks() { // nextTime == null

        Task task = new Task("active_non_rep", start);
        task.setActive(false);
        taskScheduler.tasks.add(task);

        Iterable<Task> filtered = taskScheduler.incoming(localDate, endTimeValue, endInterval);
        List<Task> list = StreamSupport.stream(filtered.spliterator(), false)
                .collect(Collectors.toList());
        assertEquals(0, list.size());
    }


    @Test
    void incomingEmptyResultNotV1V2V3() { // nextTime == null

        Task task = new Task("active_rep", end);
        task.setActive(false);
        taskScheduler.tasks.add(task);

        Iterable<Task> filtered = taskScheduler.incoming(localDate, endTimeValue, endInterval);
        List<Task> list = StreamSupport.stream(filtered.spliterator(), false)
                .collect(Collectors.toList());
        assertEquals(0, list.size());
    }
    @Test
    void incomingEmptyResultActiveV1NotV2V3() { // !nextTime.before(end) || nextTime.equals(end)

        Task task = new Task("active_rep", end);
        task.setActive(true);
        taskScheduler.tasks.add(task);

        Iterable<Task> filtered = taskScheduler.incoming(localDate, endTimeValue, endInterval);
        List<Task> list = StreamSupport.stream(filtered.spliterator(), false)
                .collect(Collectors.toList());
        assertEquals(1, list.size());
    }

    @Test
    void incomingEmptyResultV1V2NotV3() { // nextTime.before(end) || !nextTime.equals(end)

        Task task = new Task("active_rep", datedate, end, 10);
        task.setActive(true);
        taskScheduler.tasks.add(task);

//        MyTest.f(taskScheduler.tasks, start, end);
        Iterable<Task> filtered = taskScheduler.incoming(localDate, "00:00", end);
        List<Task> list = StreamSupport.stream(filtered.spliterator(), false)
                .collect(Collectors.toList());
        assertEquals(1, list.size());
    }

    @Test
    void incomingEmptyResultV1NotV2NotV3() { // !nextTime.before(end) && !nextTime.equals(end)

        Task task = new Task("active_rep", start, end, 1000000);
        task.setActive(true);
        taskScheduler.tasks.add(task);

        Iterable<Task> filtered = taskScheduler.incoming(startDateButLocalDate, "00:00", end);
        List<Task> list = StreamSupport.stream(filtered.spliterator(), false)
                .collect(Collectors.toList());
        assertEquals(0, list.size());
    }

//    @MyTest
//    void incomingValidResult() { // nextTime.before(end) && nextTime.equals(end) (XOR exclusive)
//        Task task = new Task("title", start, end, 10);
//        task.setActive(true);
//        taskScheduler.tasks.add(task);
//
//        Iterable<Task> filtered = taskScheduler.incoming(localDate, endTimeValue, endInterval);
//        List<Task> list = StreamSupport.stream(filtered.spliterator(), false)
//                .collect(Collectors.toList());
//        assertEquals(1, list.size());
//    }
//    @Test
//    void test1(){
//        Task task = new Task("active_rep", start, end, 1000000);
//        task.setActive(true);
//        taskScheduler.tasks.add(task);
//
//        MyTest.f(taskScheduler.tasks, start, end);
//    }
}