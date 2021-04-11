package tasks.model;

import tasks.exceptions.ValidationException;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class TaskScheduler {
    public List<Task> tasks;

    public TaskScheduler(List<Task> tasksList){
        tasks = tasksList;
    }

    private static final int MINUTES_IN_HOUR = 60;
    private static final int HOURS_IN_A_DAY = 24;

    public Iterable<Task> incoming(LocalDate startDateValue, String startTimeValue, Date end){ // TODO: de adaugat validation if-s
        Instant instant = Instant.from(startDateValue.atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);

        if (startTimeValue.matches("[^0-9:]+")) {
            throw new ValidationException("invalid time format");
        }
        String[] units = startTimeValue.split(":");
        if (units.length != 2) {
            throw new ValidationException("invalid time format");
        }
        int hour = Integer.parseInt(units[0]);
        int minute = Integer.parseInt(units[1]);
        if (hour > HOURS_IN_A_DAY || minute > MINUTES_IN_HOUR) {
            throw new ValidationException("time unit exceeds bounds");
        }
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        Date start = calendar.getTime();

        ArrayList<Task> incomingTasks = new ArrayList<>();
        for (Task t : tasks) {
            Date nextTime = t.nextTimeAfter(start);
            boolean v1 = nextTime != null;
            if (v1) {
                boolean v2 = nextTime.before(end);
                boolean v3 = nextTime.equals(end);
                if (v2 || v3) {
                    incomingTasks.add(t);
                    System.out.println(t.getTitle());
                }
            }
        }
        return incomingTasks;
    }
}

/*
package tasks.model;

import tasks.exceptions.ValidationException;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class TaskScheduler {
    public List<Task> tasks;

    public TaskScheduler(List<Task> tasksList){
        tasks = tasksList;
    }

    private static final int MINUTES_IN_HOUR = 60;
    private static final int HOURS_IN_A_DAY = 24;

    public Iterable<Task> incoming(LocalDate startDateValue, String startTimeValue, Date end){ // TODO: de adaugat validation if-s
        Instant instant = Instant.from(startDateValue.atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);

        if (startTimeValue.matches("[^0-9:]+")) {
            throw new ValidationException("invalid time format");
        }
        String[] units = startTimeValue.split(":");
        if (units.length != 2) {
            throw new ValidationException("invalid time format");
        }
        int hour = Integer.parseInt(units[0]);
        int minute = Integer.parseInt(units[1]);
        if (hour > HOURS_IN_A_DAY || minute > MINUTES_IN_HOUR) {
            throw new ValidationException("time unit exceeds bounds");
        }
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        Date start = calendar.getTime();

        ArrayList<Task> incomingTasks = new ArrayList<>();
        for (Task t : tasks) {
            Date nextTime = t.nextTimeAfter(start);
            if (nextTime != null && (nextTime.before(end) || nextTime.equals(end))) {
                incomingTasks.add(t);
                System.out.println(t.getTitle());
            }
        }
        return incomingTasks;
    }
}
 */
